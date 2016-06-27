package org.digijava.kernel.ampapi.endpoints.indicator;

import org.apache.log4j.Logger;
import org.digijava.kernel.ampapi.endpoints.common.EndpointUtils;
import org.digijava.kernel.ampapi.endpoints.util.JsonBean;
import org.digijava.kernel.request.TLSUtils;
import org.digijava.module.aim.dbentity.AmpIndicatorColor;
import org.digijava.module.aim.dbentity.AmpIndicatorLayer;
import org.digijava.module.aim.dbentity.AmpTeamMember;
import org.digijava.module.aim.helper.Constants;
import org.digijava.module.aim.helper.TeamMember;
import org.digijava.module.aim.util.ColorRampUtil;
import org.digijava.module.aim.util.DynLocationManagerUtil;
import org.digijava.module.aim.util.TeamUtil;
import org.digijava.module.categorymanager.util.CategoryManagerUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class IndicatorUtils {
    protected static final Logger logger = Logger.getLogger(IndicatorUtils.class);

    public static JsonBean buildIndicatorLayerJson(AmpIndicatorLayer indicator) {
        JsonBean indicatorJson = new JsonBean();

        indicatorJson.set(IndicatorEPConstants.ID, indicator.getId());
        indicatorJson.set(IndicatorEPConstants.NAME, indicator.getName());
        indicatorJson.set(IndicatorEPConstants.DESCRIPTION, indicator.getDescription());
        indicatorJson.set(IndicatorEPConstants.NUMBER_OF_CLASSES, indicator.getNumberOfClasses());
        indicatorJson.set(IndicatorEPConstants.UNIT, indicator.getUnit());
        indicatorJson.set(IndicatorEPConstants.ADM_LEVEL_ID, indicator.getAdmLevel().getId());
        indicatorJson.set(IndicatorEPConstants.ACCESS_TYPE_ID, indicator.getAccessType().getId());
        if (indicator.getCreatedOn()!=null) {
            indicatorJson.set(IndicatorEPConstants.CREATED_ON, IndicatorEPConstants.DATE_FORMATTER.format(indicator.getCreatedOn()));
        }
        if (indicator.getUpdatedOn() != null) {
            indicatorJson.set(IndicatorEPConstants.UPDATED_ON, IndicatorEPConstants.DATE_FORMATTER.format(indicator.getUpdatedOn()));
        }
        if (indicator.getCreatedBy() != null) {
            indicatorJson.set(IndicatorEPConstants.CREATE_BY, indicator.getCreatedBy().getUser().getEmail());
        }

        if (indicator.getColorRamp() != null) {
            String[] colors = new String[indicator.getColorRamp().size()];
            int i=0;
            for (AmpIndicatorColor indicatorColor: indicator.getColorRamp()){
                if (indicatorColor.getPayload() == IndicatorEPConstants.PAYLOAD_INDEX) {
                    long colorId = ColorRampUtil.getColorId(indicatorColor.getColor());
                    indicatorJson.set(IndicatorEPConstants.COLOR_RAMP_ID, colorId);
                }
                colors[i++] = indicatorColor.getColor();
            }
            indicatorJson.set(IndicatorEPConstants.COLOR_RAMP, colors);
        }

        indicatorJson.set(IndicatorEPConstants.NUMBER_OF_IMPORTED_RECORDS, (indicator.getIndicatorValues()!=null ? indicator.getIndicatorValues().size() : 0));

        return indicatorJson;
    }

    public static AmpIndicatorLayer setIndicatorLayer(JsonBean indicator) {
        AmpIndicatorLayer indicatorLayer = null; new AmpIndicatorLayer();

        if (indicator.get("id")!=null) {
            indicatorLayer = DynLocationManagerUtil.getIndicatorLayerById(Long.valueOf(indicator.getString("id")));
        } else{
            indicatorLayer = new AmpIndicatorLayer();
            indicatorLayer.setId(null);
        }

        indicatorLayer.setName(indicator.getString(IndicatorEPConstants.NAME));
        indicatorLayer.setDescription(indicator.getString(IndicatorEPConstants.DESCRIPTION));
        indicatorLayer.setNumberOfClasses(Long.valueOf(indicator.getString(IndicatorEPConstants.NUMBER_OF_CLASSES)));
        indicatorLayer.setUnit(indicator.getString(IndicatorEPConstants.UNIT));
        if (indicatorLayer.getId() == null) {
            indicatorLayer.setCreatedOn(new Date());
            indicatorLayer.setCreatedBy(getTeamMember());
        }
        indicatorLayer.setUpdatedOn(new Date());
        indicatorLayer.setAccessType(DynLocationManagerUtil.getAmpIndicatorAccessTypeFromDb(Long.valueOf(indicator.getString(IndicatorEPConstants.ACCESS_TYPE_ID))));
        indicatorLayer.setAdmLevel(CategoryManagerUtil.getAmpCategoryValueFromDb(Long.valueOf(indicator.getString(IndicatorEPConstants.ADM_LEVEL_ID))));

        if (indicator.get(IndicatorEPConstants.COLOR_RAMP_ID)!=null) {
            Set<AmpIndicatorColor> colorRamp = new HashSet<AmpIndicatorColor>();
            String[] colorRampColors = ColorRampUtil.getColorRamp(EndpointUtils.getSingleValue(indicator, IndicatorEPConstants.COLOR_RAMP_ID,null),
                    indicatorLayer.getNumberOfClasses());
            for (int i = 0; i < colorRampColors.length; i++) {
                AmpIndicatorColor color = new AmpIndicatorColor();
                long payload = i + 1;
                color.setPayload(payload);
                color.setColor(colorRampColors[i]);
                colorRamp.add(color);
            }
            indicatorLayer.setColorRamp(colorRamp);
        }

        return indicatorLayer;
    }

    public static AmpTeamMember getTeamMember() {
        TeamMember tm = (TeamMember) TLSUtils.getRequest().getSession().getAttribute(Constants.CURRENT_MEMBER);
        if (tm != null) {
            AmpTeamMember ampTeamMember = TeamUtil.getAmpTeamMember(tm.getMemberId());
            if (ampTeamMember != null) {
                return ampTeamMember;
            }
        }
        return null;
    }

    public static boolean isAdmin() {
        return "yes".equals(TLSUtils.getRequest().getSession().getAttribute("ampAdmin"));
    }

    public static boolean hasRights(long indicatorId) {
        if (isAdmin()) {
            return true;
        } else {
            TeamMember tm = (TeamMember) TLSUtils.getRequest().getSession().getAttribute(Constants.CURRENT_MEMBER);
            if (tm != null) {
                AmpTeamMember ampTeamMember = TeamUtil.getAmpTeamMember(tm.getMemberId());
                if (ampTeamMember != null) {
                    AmpIndicatorLayer indicatorLayer =DynLocationManagerUtil.getIndicatorLayerById(indicatorId);
                    if (indicatorLayer.getCreatedBy() != null && indicatorLayer.getCreatedBy().getAmpTeamMemId()==ampTeamMember.getAmpTeamMemId()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static JsonBean getList(Collection<AmpIndicatorLayer> indicatorLayers, Integer offset, Integer count) {

        offset = Math.min(indicatorLayers.size(), offset == null ? 0 : offset);
        count = (count == null ? IndicatorEPConstants.DEFAULT_COUNT : count);
        int totalPageCount = Math.min(indicatorLayers.size() - offset, count);

        JsonBean result = new JsonBean();
        JsonBean page = new JsonBean();

        Collection<JsonBean> indicatorLayerList = new ArrayList<JsonBean>();
        Collection<AmpIndicatorLayer> col = new ArrayList<>(indicatorLayers).subList(offset, offset + totalPageCount);

        for (AmpIndicatorLayer indicator: col){
            JsonBean indicatorJson = IndicatorUtils.buildIndicatorLayerJson(indicator);
            indicatorLayerList.add(indicatorJson);
        }

        page.set(IndicatorEPConstants.RECORDS_PER_PAGE,count);
        page.set(IndicatorEPConstants.CURRENT_PAGE_NUMBER, (offset / count) + 1);
        page.set(IndicatorEPConstants.TOTAL_PAGE_COUNT,totalPageCount);
        page.set(IndicatorEPConstants.TOTAL_RECORDS,indicatorLayers.size());

        result.set(IndicatorEPConstants.PAGE,page);
        result.set(IndicatorEPConstants.DATA,indicatorLayerList);

        return result;
    }

}