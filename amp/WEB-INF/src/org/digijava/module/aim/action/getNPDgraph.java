package org.digijava.module.aim.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.digijava.module.aim.dbentity.AmpIndicator;
import org.digijava.module.aim.dbentity.AmpIndicatorValue;
import org.digijava.module.aim.dbentity.AmpTheme;
import org.digijava.module.aim.dbentity.IndicatorTheme;
import org.digijava.module.aim.dbentity.NpdSettings;
import org.digijava.module.aim.exception.AimException;
import org.digijava.module.aim.form.NpdGraphForm;
import org.digijava.module.aim.util.ChartUtil;
import org.digijava.module.aim.util.IndicatorUtil;
import org.digijava.module.aim.util.NpdUtil;
import org.digijava.module.aim.util.ProgramUtil;
import org.digijava.module.aim.util.TeamUtil;
import org.digijava.module.categorymanager.util.CategoryManagerUtil;
import org.digijava.module.common.util.DateTimeUtil;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.CustomCategoryDataset;

/**
 * NPD Indicators graph generator action.
 * Generates different (currently only one) types of graphs for specified indicators
 * Response of the Action is image generated by JFreeChart.
 *
 * @author Irakli Kobiashvili - ikobiashvili@picktek.com
 */
public class getNPDgraph extends Action {


    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {


        NpdGraphForm npdForm = (NpdGraphForm) form;


        try {
            Long currentThemeId = npdForm.getCurrentProgramId();
            long[] selIndicators = npdForm.getSelectedIndicators();
            String[] selYears = npdForm.getSelectedYears();
            if (selYears!=null){
                Arrays.sort(selYears);
            }

            //session for storing latest map for graph
            HttpSession session = request.getSession();

            CategoryDataset dataset = null;
            if (currentThemeId != null && currentThemeId.longValue() > 0) {
                AmpTheme currentTheme = ProgramUtil.getThemeById(currentThemeId);


                dataset = createPercentsDataset(currentTheme, selIndicators, selYears,request);
            }
            JFreeChart chart = ChartUtil.createChart(dataset, ChartUtil.CHART_TYPE_BAR);


            ChartRenderingInfo info = new ChartRenderingInfo();


            response.setContentType("image/png");            
           
            Long teamId=TeamUtil.getCurrentTeam(request).getAmpTeamId();
            NpdSettings npdSettings=NpdUtil.getCurrentSettings(teamId);            
            Double angle=null;
            
            if(npdSettings.getAngle()!=null){
                CategoryPlot categoryplot = (CategoryPlot)chart.getPlot();
                CategoryAxis categoryaxis = categoryplot.getDomainAxis();
                angle=npdSettings.getAngle().intValue()*3.1415926535897931D/180D;
                categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(angle));
            }
            
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, npdSettings.getWidth().intValue(),
                    npdSettings.getHeight().intValue(), info);
         
            //NpdGraphTooltipGenerator ttGen = new NpdGraphTooltipGenerator();

            //generate map for this graph
            String map = ChartUtilities.getImageMap("npdChartMap", info);
            //String map = getImageMap("npdChartMap", info, new StandardToolTipTagFragmentGenerator(), new StandardURLTagFragmentGenerator());
            //////System.out.println(map);

            //save map with timestamp from request for later use
            //timestemp is generated with javascript before sending ajax request.
            ChartUtil.saveMap(map, npdForm.getTimestamp(), session);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    // TODO This method should be moved to NPD or chart util.
    public CategoryDataset createPercentsDataset(AmpTheme currentTheme,
                                                  long[] selectedIndicators, String[] selectedYears, HttpServletRequest request)
            throws AimException {

        CustomCategoryDataset dataset = new CustomCategoryDataset();

        if (selectedIndicators != null && currentTheme.getIndicators() != null) {
            Arrays.sort(selectedIndicators);

            dataset = new CustomCategoryDataset();

            //Set sortedIndicators = new TreeSet(currentTheme.getIndicators());
            List<IndicatorTheme> sortedIndicators = new ArrayList<IndicatorTheme>(currentTheme.getIndicators());
            Collections.sort(sortedIndicators, new IndicatorUtil.IndThemeIndciatorNameComparator());
            Iterator<IndicatorTheme> iter = sortedIndicators.iterator();
            while (iter.hasNext()) {
                IndicatorTheme item = iter.next();
                AmpIndicator indicator=item.getIndicator();
                int pos = Arrays.binarySearch(selectedIndicators, indicator.getIndicatorId().longValue());

                if (pos >= 0) {
                    //String key="aim:NPD:"+indicator.getName();
                    String displayLabel = indicator.getName();
                    
                    
                    try {
                        Collection<AmpIndicatorValue> indValues = item.getValues();  // ProgramUtil.getThemeIndicatorValuesDB(item.getAmpThemeIndId());
                       
                        Map<String, AmpIndicatorValue> actualValues = new HashMap<String, AmpIndicatorValue>(); // map to store latest actual values group by year
                        Map<Integer, ArrayList<AmpIndicatorValue>> targetVals = new HashMap<Integer, ArrayList<AmpIndicatorValue>>(); // map to save target values group by year
                        Map<Integer, ArrayList<AmpIndicatorValue>> baseVals = new HashMap<Integer, ArrayList<AmpIndicatorValue>>(); // map to save base values group by year
                       
                       
                        Date latestActualDate = null;

                       
                           for (AmpIndicatorValue valueItem : indValues) {
                                if (valueItem.getValueType() == 1) {
                                    // actual value's year must be in the selected years range
                                    if (isInSelectedYears(valueItem, selectedYears)) {
                                        // we should store latest actual value
                                        Date actualDate = valueItem.getValueDate();
                                        if (latestActualDate == null) {
                                            latestActualDate = actualDate;
                                        }
                                        String year = extractYearString(actualDate);
                                        if (!actualValues.containsKey(year) || latestActualDate.before(actualDate)) {
                                            actualValues.put(year, valueItem);
                                        }
                                    }
                                }
                                else{
                                     if (valueItem.getValueType() == 0) {
                                        // store target values
                                        Date targetDate = valueItem.getValueDate();
                                        Calendar cal = Calendar.getInstance();
                                        cal.setTime(targetDate);
                                        int tarYear = cal.get(Calendar.YEAR);
                                        if(!targetVals.containsKey(tarYear)){
                                            ArrayList<AmpIndicatorValue> targets=new ArrayList();
                                            targets.add(valueItem);
                                            targetVals.put(tarYear, targets);
                                        }
                                        else{
                                            ArrayList<AmpIndicatorValue> targets=targetVals.get(tarYear);
                                             targets.add(valueItem);
                                             targetVals.put(tarYear, targets);
                                        }
                                      
                                     }
                                     else{
                                        // store base values
                                        Date baseDate = valueItem.getValueDate();
                                        Calendar cal = Calendar.getInstance();
                                        cal.setTime(baseDate);
                                        int baseYear = cal.get(Calendar.YEAR);
                                        if(!baseVals.containsKey(baseYear)){
                                            ArrayList<AmpIndicatorValue> bases=new ArrayList();
                                            bases.add(valueItem);
                                            baseVals.put(baseYear, bases);
                                        }
                                        else{
                                            ArrayList<AmpIndicatorValue> bases=baseVals.get(baseYear);
                                             bases.add(valueItem);
                                             baseVals.put(baseYear, bases);
                                        }
                                         
                                     }
                                }
                               
                           }
                         
                           // show data restrict to selected date range, here we choose target and base values.
                           if (selectedYears!=null){
                               for (String selectedYear : selectedYears) {
                                   
                                   AmpIndicatorValue actValue = actualValues.get(selectedYear);
                                   AmpIndicatorValue targValue = null;
                                   AmpIndicatorValue basValue = null;
                                   List<Integer> years = new ArrayList(targetVals.keySet());
                                   // sort target years
                                   Collections.sort(years);
                                   
                                   List<Integer> baseYears = new ArrayList(baseVals.keySet());
                                   // sort base years
                                   Collections.sort(baseYears);
                                
                                   /* to select target value for selected year we must remember:
                                    * 1) if actual value in selected year exists: 
                                    * target value's date must be equal or greater than actual value's date in selected year.
                                    * 2) if there is no actual value in 
                                    * this year the year of target value must be equal or greater than selected year.
                                    */
                                   Double targetValue = null;
                                   Double baseValue = null;
                                   Double actualValue = null;
                                   Integer targetYear=null;
                                   Integer baseYear=null;
                                   
                                   for (Integer year : years) {
                                       if (Integer.parseInt(selectedYear) <= year) {
                                           ArrayList<AmpIndicatorValue> targValues = targetVals.get(year);
                                           for (AmpIndicatorValue value : targValues) {
                                               if (targValue == null || targValue.getValueDate().after(value.getValueDate())) {
                                                   if (actValue == null) {
                                                       targValue = value;
                                                       actualValue = new Double(0);
                                                       targetYear=year;
                                                   } else {
                                                       actualValue = actValue.getValue();
                                                       if (value.getValueDate().after(actValue.getValueDate()) || value.getValueDate().equals(actValue.getValueDate())) {
                                                           targValue = value;
                                                           //actualValue = actValue.getValue();
                                                           targetYear=year;
                                                       }
                                                   }
                                               }


                                           }
                                       }
                                   }
                                   
                                    /* to select base value for selected year we must remember:
                                    * 1) if actual value in selected year exists: 
                                    * base value's date must be equal or less than actual value's date in selected year.
                                    * 2) if there is no actual value in 
                                    * this year the year of base value must be equal or less than selected year.
                                    */
                                   for (Integer year : baseYears) {
                                       if (Integer.parseInt(selectedYear) >= year) {
                                          
                                           ArrayList<AmpIndicatorValue> basValues = baseVals.get(year);
                                           if (basValues != null) {
                                               for (AmpIndicatorValue value : basValues) {
                                                   if (basValue == null || basValue.getValueDate().before(value.getValueDate())) {
                                                       if (actValue == null) {
                                                           basValue = value;
                                                           baseYear=year;
                                                       } else {
                                                           if (value.getValueDate().before(actValue.getValueDate()) || value.getValueDate().equals(actValue.getValueDate())) {
                                                               basValue = value;
                                                               baseYear=year;
                                                           }
                                                       }
                                                   }


                                               }

                                           }
                                       }
                                   }
                                
                               
                                   if(basValue==null){
                                       baseValue=new Double(0);
                                   }
                                   else{
                                       baseValue=basValue.getValue();
                                   }
                                      if(targValue==null){
                                       targetValue=new Double(0);
                                   }
                                   else{
                                       targetValue=targValue.getValue();
                                   }
                                // create dataset for graph
                                   dataset.addCustomTooltipValue(new String[]{formatValue(baseValue,baseYear, selectedYear), formatValue(actualValue,Integer.parseInt(selectedYear), selectedYear), formatValue(actualValue,Integer.parseInt(selectedYear), selectedYear), formatValue(targetValue,targetYear, selectedYear)});
                                    Double realActual = computePercent(indicator, targetValue, actualValue, baseValue);
                                    dataset.addValue(realActual.doubleValue(), selectedYear, displayLabel);

                               }
                               
                           }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        throw new AimException("Error creating dataset for graph.", ex);
                    }
                }
            }

        }
        return dataset;

    }


    public String formatValue(Double val,Integer year,String selctedYear) {
        String retVal="0";
        if (val != null) {
            retVal=val.toString();
            if(year!=null){
                retVal+=" ("+year.toString()+") ";
            }
            else{
                retVal+=" ("+selctedYear+") ";
            }
        }
        else{
            retVal+=" ("+selctedYear+") ";
        }
        return retVal;
    }

    public String formatActualDate(AmpIndicatorValue val) {
        if (val != null) {
            return DateTimeUtil.formatDate(val.getValueDate());
        }
        return "";
    }

    /**
     * Calculates percent from indicator values.
     * This calculation depends on type of indicator which may be ascending or descending.
     * @param indic
     * @param _target
     * @param _actual
     * @param _base
     * @return
     */
    private static Double computePercent(AmpIndicator indic, Double _target, Double _actual, Double _base) {
        if ((_actual == null || _actual == 0)) {
            return new Double(0);
        } else {
            double actual = _actual.doubleValue();
            double base = (_base == null) ? 0 : _base.doubleValue();
            double target = (_target == null) ? 0 : _target.doubleValue();
            double result = 0;
            if (indic.getType() != null && "D".equals(indic.getType())) {
                //descending
                base -= target;
                actual -= target;
                target = 0;
                if (base != 0) {
                    result = actual / (base / 100);
                    result = 1 - result / 100;

                }
            } else {
                //ascending
                actual -= base;
                target -= base;
                base = 0;
                if (target != 0) {
                    result = actual / target;
                }
            }
            return new Double(result);
        }

    }
    private static int extractYearInt(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int iYear = cal.get(Calendar.YEAR);
        return iYear;
    }

    private static String extractYearString(Date date) {
        String sYear = null;
        if (date != null) {
            int iYear = extractYearInt(date);
            sYear = String.valueOf(iYear);
        }
        return sYear;
    }

    private static boolean isInSelectedYears(AmpIndicatorValue value, String[] selYars) {
        String sYear = extractYearString(value.getValueDate());
        if (sYear != null && selYars!=null) {
            //we can use this method because selYears are sorted by sort() method of Arrays class
            return Arrays.binarySearch(selYars, sYear) >= 0;
//            for (int i = 0; i < selYars.length; i++) {
//                if (selYars[i].equals(sYear)) {
//                    return true;
//                }
//            }
        }
        return false;
    }


}
