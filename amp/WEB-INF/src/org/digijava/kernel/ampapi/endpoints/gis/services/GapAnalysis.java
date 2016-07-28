/**
 * 
 */
package org.digijava.kernel.ampapi.endpoints.gis.services;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.digijava.kernel.ampapi.endpoints.indicator.IndicatorUtils;
import org.digijava.kernel.ampapi.endpoints.util.GisConstants;
import org.digijava.kernel.ampapi.endpoints.util.JsonBean;
import org.digijava.module.aim.dbentity.AmpIndicatorLayer;
import org.digijava.module.aim.dbentity.AmpLocationIndicatorValue;
import org.digijava.module.categorymanager.dbentity.AmpCategoryValue;
import org.digijava.module.categorymanager.util.CategoryConstants;
import org.digijava.module.categorymanager.util.CategoryConstants.HardCodedCategoryValue;

/**
 * GIS Gap Analysis service
 * 
 * @author Nadejda Mandrescu
 */
public class GapAnalysis {
    protected static Logger LOGGER = Logger.getLogger(GapAnalysis.class);
    private static final MathContext MCTX = new MathContext(6, RoundingMode.HALF_EVEN);
    
    private Map<String, AmpIndicatorLayer> implLocPopulationLayer = new HashMap<>();
    
    private AmpIndicatorLayer indicator;
    private JsonBean input;
    private boolean canDoGapAnalysis = false;
    
    private Map<String, BigDecimal> admFundings;
    private Map<String, BigDecimal> populationCount;
    private boolean isPopulation;
    
    public GapAnalysis() {
    }
    
    public GapAnalysis(AmpIndicatorLayer indicator, JsonBean input) {
        this.indicator = indicator;
        this.input = input;
        initForGapAnalysis();
    }
    
    private void initForGapAnalysis() {
        this.canDoGapAnalysis = canDoGapAnalysis(indicator);
        
        if (canDoGapAnalysis) {
            prepareData();
        }
    }
    
    private void prepareData() {
        JsonBean admTotals = null;
        String implementationLocation = indicator.getAdmLevel() == null ? null : indicator.getAdmLevel().getValue();
        String admLevel = GisConstants.IMPL_CATEGORY_VALUE_TO_ADM.get(implementationLocation);
        if (admLevel != null) {
            // prepare population data if needed
            if (CategoryConstants.INDICATOR_LAYER_TYPE_POPULATION_RATIO.getValueKey().equals(
                    indicator.getIndicatorType().getValue())) {
                AmpIndicatorLayer populationLayer = getPopulationLayer(indicator.getAdmLevel());
                if (populationLayer == null) {
                    canDoGapAnalysis = false;
                    LOGGER.error("No population layer found for '" + implementationLocation + "'");
                } else {
                    buildPopulationData(populationLayer); 
                }
            }
            // prepare funding data
            if (canDoGapAnalysis) {
                admTotals = new LocationService().getTotals(admLevel, input);
                admFundings = convertToMap(admTotals);
            }
        } else {
            canDoGapAnalysis = false;
            LOGGER.error("Could not identify admLevel for implLevel = " + implementationLocation);
        }
    }
    
    private void buildPopulationData(AmpIndicatorLayer populationLayer) {
        isPopulation = true;
        populationCount = new HashMap<>();
        for (AmpLocationIndicatorValue locValue : populationLayer.getIndicatorValues()) {
            populationCount.put(locValue.getLocation().getGeoCode(), new BigDecimal(locValue.getValue()));
        }
    }
    
    public BigDecimal getGapAnalysisAmount(BigDecimal amount, String geoCode) {
        BigDecimal fundingAmount = admFundings.get(geoCode);
        BigDecimal factor = isPopulation ? populationCount.get(geoCode) : BigDecimal.ONE;
        // if no factor found for population, then we report 'No Data'
        amount = factor == null ? null : factor.multiply(amount);
        // if no funding data or no amount, then 'No Data' to show
        if (amount == null || fundingAmount == null || BigDecimal.ZERO.equals(amount.abs(MCTX)) ) {
            amount = null;
            // for testing only, until GIS is updated to handle "null"s
            // amount = BigDecimal.ZERO;
        } else {
            amount =  fundingAmount.divide(amount, MCTX);
        }
        return amount;
    }
    
    public boolean isReadyForGapAnalysis() {
        return canDoGapAnalysis;
    }
    
    /**
     * Converts total fundings to internal map for flexibility
     * @param data
     * @param locationValues
     * @return
     */
    private Map<String, BigDecimal> convertToMap(JsonBean data) {
        List<JsonBean> values = data == null ? null : (List<JsonBean>) data.get("values");
        Map<String, BigDecimal> result = new HashMap<>();
        if (values != null && !values.isEmpty()) {
            for (JsonBean admAmount : values) {
                String admId = admAmount.getString("admID");
                BigDecimal amount = (BigDecimal) admAmount.get("amount");
                if (admId != null) {
                    result.put(admId, amount == null ? BigDecimal.ZERO : amount);
                }
            }
        }
        return result;
    }
    
    /**
     * Get unique Population Layer designated for 'admLevel' and remember for reuse
     * @param admLevel the generic ADM level ("adm0", ...)
     * @return the population layer or null if no unique layer found
     */
    public AmpIndicatorLayer getPopulationLayer(String admLevel) {
        HardCodedCategoryValue hardcodedCatValue = GisConstants.ADM_TO_IMPL_CATEGORY_VALUE.get(admLevel);
        return getPopulationLayer(hardcodedCatValue == null ? null : hardcodedCatValue.getAmpCategoryValueFromDB());
    }
    
    /**
     * Get unique Population Layer designated for the given implementation location and remember for reuse
     * @param implLoc the implementation location (Region, etc)
     * @return  the population layer or null if no unique layer found
     */
    public AmpIndicatorLayer getPopulationLayer(AmpCategoryValue implLoc) {
        String implLocation = implLoc == null ? null : implLoc.getValue();
        AmpIndicatorLayer ail = implLocPopulationLayer.putIfAbsent(implLocation, IndicatorUtils.getPopulationLayer(implLoc));
        if (ail == null) {
            ail = implLocPopulationLayer.get(implLocation);
        }
        return ail;
    }
    
    /**
     * Checks if gap analysis can be done over the specified indicator layer
     * @param ail the indicator layer to test
     * @return true if gap analysis can be done over this indicator layer
     */
    public boolean canDoGapAnalysis(AmpIndicatorLayer ail) {
        if (ail != null && ail.getIndicatorType() != null) {
            String acvValue = ail.getIndicatorType().getValue();
            if (acvValue.equals(CategoryConstants.INDICATOR_LAYER_TYPE_PER_CAPITA.getValueKey())
                    || acvValue.equals(CategoryConstants.INDICATOR_LAYER_TYPE_COUNT.getValueKey())
                    || (acvValue.equals(CategoryConstants.INDICATOR_LAYER_TYPE_POPULATION_RATIO.getValueKey())
                            && getPopulationLayer(ail.getAdmLevel()) != null)) {
                return true;
            }
        }
        return false;
    }
    
}
