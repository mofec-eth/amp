/**
 * 
 */
package org.dgfoundation.amp.visibility.data;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dgfoundation.amp.ar.ArConstants;
import org.dgfoundation.amp.ar.MeasureConstants;
import org.digijava.kernel.persistence.PersistenceManager;
import org.digijava.module.aim.dbentity.AmpFeaturesVisibility;
import org.digijava.module.aim.dbentity.AmpModulesVisibility;
import org.digijava.module.aim.dbentity.AmpTemplatesVisibility;
import org.digijava.module.aim.helper.GlobalSettingsConstants;
import org.digijava.module.aim.util.FeaturesUtil;
import org.digijava.module.categorymanager.dbentity.AmpCategoryValue;
import org.digijava.module.categorymanager.util.CategoryConstants;
import org.digijava.module.categorymanager.util.CategoryManagerUtil;

import clover.com.google.common.collect.Sets;

/**
 * Detects which measures are visible in Activity Form and can be further used, 
 * e.g. in report generation & filtering 
 * @author Alexandru Cartaleanu
 */
public class MeasuresVisibility extends DataVisibility implements FMSettings {
	protected static final Logger logger = Logger.getLogger(MeasuresVisibility.class);
	
	protected final static String CAPITAL_SPENDING_PERCENTAGE_ID_NAME = "Capital";
	protected final static String ANNUAL_PROPOSED_PROJECT_COST_ID_NAME = "AnnualPropProjCost";
	protected final static String DIRECTED_DISBURSEMENTS_ID_NAME = "DirectedDisbursements";
	protected final static String PROPOSED_PROJECT_COST_ID_NAME = "ProposedProjectCost";
	protected final static Map<String, String> fieldsToMeasuresMap = new HashMap<String, String>();
	protected final static String ADJUSTMENT_PREFIX = "adjustment_type: ";
	
	public MeasuresVisibility() {
	}
	
	@SuppressWarnings("serial")
	/**
	 * Map<FM Module full path, name of said path as an Element>
	 */
	protected static final Map<String, String> modulesToMeasuresMap = new HashMap<String, String>() {{
		for(String transactionName:ArConstants.TRANSACTION_TYPE_NAME_TO_ID.keySet())
			put("/Activity Form/Funding/Funding Group/Funding Item/" + transactionName, transactionName);
			put("/Activity Form/Funding/Funding Group/Funding Item/Disbursements/Disbursements Table/Capital Spending Percentage", CAPITAL_SPENDING_PERCENTAGE_ID_NAME);
			put("/Activity Form/Funding/Proposed Project Cost/Annual Proposed Project Cost/Add Projection", ANNUAL_PROPOSED_PROJECT_COST_ID_NAME);
			put("/Activity Form/Funding/Proposed Project Cost/Amount", PROPOSED_PROJECT_COST_ID_NAME);
			put("/Activity Form/Funding/Funding Group/Funding Item/Disbursements/Disbursements Table/Recipient Organization", DIRECTED_DISBURSEMENTS_ID_NAME);
	}};
	
	@SuppressWarnings("serial")
	/**
	 * Map<Measure_Name, List<Elements it depends on>>
	 */
	public static final Map<String, Collection<String>> measuresCustomDependencies = new HashMap<String, Collection<String>>() {{
		//Collection<AmpCategoryValue> adjustmentTypes = new ArrayList<AmpCategoryValue>(); 
		for (AmpCategoryValue adj: CategoryManagerUtil.getAmpCategoryValueCollectionByKeyExcludeDeleted(CategoryConstants.ADJUSTMENT_TYPE_KEY)) {
			for (final String trType: ArConstants.TRANSACTION_TYPE_NAME_TO_ID.keySet()) {
				put(adj.getValue() + " " + trType, Arrays.asList(trType, ADJUSTMENT_PREFIX + adj.getValue()));
			}
		}
		put(MeasureConstants.ACTUAL_DISBURSEMENTS_CAPITAL, Arrays.asList(ArConstants.DISBURSEMENT, CAPITAL_SPENDING_PERCENTAGE_ID_NAME, ADJUSTMENT_PREFIX + "Actual"));
		put(MeasureConstants.ACTUAL_DISBURSEMENTS_RECURRENT, Arrays.asList(ArConstants.DISBURSEMENT, CAPITAL_SPENDING_PERCENTAGE_ID_NAME, ADJUSTMENT_PREFIX + "Actual"));
		put(MeasureConstants.PLANNED_DISBURSEMENTS_CAPITAL, Arrays.asList(ArConstants.DISBURSEMENT, CAPITAL_SPENDING_PERCENTAGE_ID_NAME, ADJUSTMENT_PREFIX + "Planned"));
		put(MeasureConstants.PLANNED_DISBURSEMENTS_EXPENDITURE, Arrays.asList(ArConstants.DISBURSEMENT, CAPITAL_SPENDING_PERCENTAGE_ID_NAME, ADJUSTMENT_PREFIX + "Planned"));
		put(MeasureConstants.ANNUAL_PROPOSED_PROJECT_COST, Arrays.asList(ANNUAL_PROPOSED_PROJECT_COST_ID_NAME));
		put(MeasureConstants.PLEDGES_COMMITMENT_GAP, Arrays.asList(ArConstants.COMMITMENT, MeasureConstants.PLEDGES_ACTUAL_PLEDGE, ADJUSTMENT_PREFIX + "Actual"));
		put(MeasureConstants.CONSUMPTION_RATE, Arrays.asList(ArConstants.DISBURSEMENT, ADJUSTMENT_PREFIX + "Actual", ADJUSTMENT_PREFIX + "Planned"));
		put(MeasureConstants.CUMULATED_DISBURSEMENTS, Arrays.asList(ArConstants.DISBURSEMENT, ADJUSTMENT_PREFIX + "Actual"));
		put(MeasureConstants.DISBURSMENT_RATIO, Arrays.asList(ArConstants.DISBURSEMENT, ADJUSTMENT_PREFIX + "Actual"));		
		put(MeasureConstants.EXECUTION_RATE, Arrays.asList(ArConstants.DISBURSEMENT, ADJUSTMENT_PREFIX + "Actual", ADJUSTMENT_PREFIX + "Planned"));
		put(MeasureConstants.PLEDGES_PERCENTAGE_OF_DISBURSEMENT, Arrays.asList(ArConstants.DISBURSEMENT, ADJUSTMENT_PREFIX + "Actual", MeasureConstants.PLEDGES_ACTUAL_PLEDGE));
		put(MeasureConstants.PERCENTAGE_OF_TOTAL_COMMITMENTS, Arrays.asList(ArConstants.COMMITMENT, ADJUSTMENT_PREFIX + "Actual"));
		put(MeasureConstants.PREVIOUS_MONTH_DISBURSEMENTS, Arrays.asList(ArConstants.DISBURSEMENT, ADJUSTMENT_PREFIX + "Actual"));
		put(MeasureConstants.PRIOR_ACTUAL_DISBURSEMENTS, Arrays.asList(ArConstants.DISBURSEMENT, ADJUSTMENT_PREFIX + "Actual"));
		put(MeasureConstants.REAL_DISBURSEMENTS, Arrays.asList(ArConstants.DISBURSEMENT, ADJUSTMENT_PREFIX + "Actual", DIRECTED_DISBURSEMENTS_ID_NAME));
		// skipping REAL_COMMITMENTS and REAL_MTEF, as could not find support for those in the AF
		put(MeasureConstants.SELECTED_YEAR_PLANNED_DISBURSEMENTS, Arrays.asList(ArConstants.DISBURSEMENT, ADJUSTMENT_PREFIX + "Planned"));
		put(MeasureConstants.TOTAL_COMMITMENTS, Arrays.asList(ArConstants.COMMITMENT, ADJUSTMENT_PREFIX + "Actual"));
		put(MeasureConstants.UNCOMMITTED_BALANCE, Arrays.asList(ArConstants.COMMITMENT, ADJUSTMENT_PREFIX + "Actual", PROPOSED_PROJECT_COST_ID_NAME));
		put(MeasureConstants.UNDISBURSED_BALANCE, Arrays.asList(ArConstants.COMMITMENT, ADJUSTMENT_PREFIX + "Actual", ArConstants.DISBURSEMENT));
	}};

	@SuppressWarnings("serial")
	public static final Set<String> allMeasures = Collections.unmodifiableSet(new HashSet<String>(PersistenceManager.getSession().createSQLQuery("select distinct(measurename) from amp_measures").list()));	
	
	/**
	 * this should be {@link #detectVisibleData()} in case AF is taken into account
	 * @return
	 */
	public Set<String> detectVisibleData_AF(long visibilityTemplateId) {
		sanityCheck();
		Set<String> visiblePrecursors = new HashSet<String>(); // Measure Names
		Set<String> visibleData = new HashSet<String>(); // Measure Names
		Set<String> invisibleData = new HashSet<String>(getAllData()); // Measure Names

		Set<String> invisiblePrecursors = new HashSet<String>(); // Measure Names
		List<AmpModulesVisibility> modules = FeaturesUtil.getAmpModulesVisibility(modulesToMeasuresMap.keySet(), visibilityTemplateId);
		splitObjectsByVisibility(modules, modulesToMeasuresMap, visiblePrecursors, invisiblePrecursors);
		
		visiblePrecursors.addAll(getVisibleAdjustments());
		
		Map<String, Collection<String>> allDependenciesMap = getDependancyMapTypeAll();
		for (Entry<String, Collection<String>> entry : allDependenciesMap.entrySet()) {
			if (visiblePrecursors.containsAll(entry.getValue())) {
				visibleData.add(entry.getKey());
				invisibleData.remove(entry.getKey());
			}
		}
		return Collections.unmodifiableSet(visibleData);
	}
		
	/**
	 * this should be {@link #detectVisibleData()} in case FM is taken into account
	 * @return
	 */
	protected Set<String> detectVisibleData_FM() {
		Set<String> visibleData = new HashSet<String>(); // Measure Names
		Set<String> invisibleData = new HashSet<String>(getAllData()); // Measure Names
		AmpTemplatesVisibility currentTemplate = FeaturesUtil.getCurrentTemplate();
		
		List<AmpFeaturesVisibility> features = FeaturesUtil.getAmpFeaturesVisibility(allMeasures, currentTemplate.getId());
		splitObjectsByVisibility(features, featuresToMeasuresMap, visibleData, invisibleData);
		return Collections.unmodifiableSet(visibleData);
	}

	@Override
	/**
	 * uses a Global Settings to decide whether to use FM or AF to return a Set of measures which should be visible
	 * @param visibilityEnum
	 * @return
	 */
	public Set<String> detectVisibleData() {
		String measVS = FeaturesUtil.getGlobalSettingValue(GlobalSettingsConstants.REPORT_WIZARD_VISIBILITY_SOURCE);
		return detectVisibleData(Integer.parseInt(measVS));
	}

	/**
	 * uses either FM or AF to return a Set of measures which should be visible
	 * @param src - one of {@link VisibilitySourceEnum} codes
	 * @return
	 */
	public Set<String> detectVisibleData(int src) {
		long visibilityTemplateId = FeaturesUtil.getCurrentTemplateId();

		switch(src) {
			case VisibilitySourceEnum.ACTIVITY_FORM_VISIBILITY:
				return detectVisibleData_AF(visibilityTemplateId);
			
			case VisibilitySourceEnum.FEATURE_MANAGER_VISIBILITY:
				return detectVisibleData_FM();
				
			case VisibilitySourceEnum.FEATURE_MANAGER_AND_ACTIVITY_FORM_VISIBILITY:
				return Sets.intersection(detectVisibleData_AF(visibilityTemplateId), detectVisibleData_FM()).immutableCopy();
				
			case VisibilitySourceEnum.FEATURE_MANAGER_OR_ACTIVITY_FORM_VISIBILITY:
				return Sets.union(detectVisibleData_AF(visibilityTemplateId), detectVisibleData_FM()).immutableCopy();
			
			default:
				throw new RuntimeException("unknown value in ReportWizardVisibilitySource: " + src);
		}
	}
	
	/**
	 * @return the current set of visible measures
	 */
	synchronized public static Set<String> getVisibleMeasures() {
		return FMSettingsMediator.getEnabledSettings(FMSettingsMediator.FMGROUP_MEASURES);
	}
	
	@Override
	public Set<String> getEnabledSettings() {
		return getCurrentVisibleData();
	}
		
	@SuppressWarnings("serial")
	// identity mapping
	protected static final Map<String, String> featuresToMeasuresMap = new HashMap<String, String>() {{
		for(String measureName:allMeasures)
			put(measureName, measureName);
	}};
		
	/**
	 * computes a list of Elements which contain the adjustments which exist in the system
	 * @return
	 */
	protected List<String> getVisibleAdjustments() {
		List<String> currentlyVisible = new ArrayList<>();
		for (AmpCategoryValue adj: CategoryManagerUtil.getAmpCategoryValueCollectionByKeyExcludeDeleted(CategoryConstants.ADJUSTMENT_TYPE_KEY)) {
				currentlyVisible.add(ADJUSTMENT_PREFIX + adj.getValue());
		}
		return currentlyVisible;
	}

	@Override
	protected Set<String> getAllData() {
		return allMeasures;
	}
	
	@Override
	protected Map<String, String> getDataMap(DataMapType dataMapType) {
		switch(dataMapType) {
			case MODULES: return modulesToMeasuresMap; 
			case FEATURES: return featuresToMeasuresMap;
			case FIELDS: return fieldsToMeasuresMap;
			case DEPENDENCY: return new HashMap<String, String>();
			default: throw new RuntimeException("unknown dataMapType: " + dataMapType);
		}
	}

	@Override
	protected Map<String, Collection<String>> getDependancyMapTypeAny() {
		return new HashMap<String,Collection<String>>();
	}

	@Override
	protected Map<String, Collection<String>> getDependancyMapTypeAll() {
		return measuresCustomDependencies;
	}

	@Override
	protected List<String> getVisibleByDefault() {
		return new ArrayList<>();
	}
	
}
