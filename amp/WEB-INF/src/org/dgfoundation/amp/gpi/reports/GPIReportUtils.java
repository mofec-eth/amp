package org.dgfoundation.amp.gpi.reports;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.dgfoundation.amp.ar.AmpARFilter;
import org.dgfoundation.amp.ar.ArConstants;
import org.dgfoundation.amp.ar.ColumnConstants;
import org.dgfoundation.amp.ar.MeasureConstants;
import org.dgfoundation.amp.newreports.AmpReportFilters;
import org.dgfoundation.amp.newreports.CalendarConverter;
import org.dgfoundation.amp.newreports.FilterRule;
import org.dgfoundation.amp.newreports.GeneratedReport;
import org.dgfoundation.amp.newreports.GroupingCriteria;
import org.dgfoundation.amp.newreports.ReportAreaImpl;
import org.dgfoundation.amp.newreports.ReportColumn;
import org.dgfoundation.amp.newreports.ReportElement;
import org.dgfoundation.amp.newreports.ReportElement.ElementType;
import org.dgfoundation.amp.newreports.ReportMeasure;
import org.dgfoundation.amp.newreports.ReportSettingsImpl;
import org.dgfoundation.amp.newreports.ReportSpecification;
import org.dgfoundation.amp.newreports.ReportSpecificationImpl;
import org.dgfoundation.amp.reports.mondrian.MondrianReportUtils;
import org.digijava.kernel.ampapi.endpoints.common.EPConstants;
import org.digijava.kernel.ampapi.endpoints.common.EndpointUtils;
import org.digijava.kernel.ampapi.endpoints.gpi.GPIDataService;
import org.digijava.kernel.ampapi.endpoints.settings.SettingsUtils;
import org.digijava.kernel.ampapi.endpoints.util.DateFilterUtils;
import org.digijava.kernel.ampapi.endpoints.util.FilterUtils;
import org.digijava.kernel.ampapi.endpoints.util.JsonBean;
import org.digijava.kernel.ampapi.exception.AmpApiException;
import org.digijava.module.aim.dbentity.AmpFiscalCalendar;
import org.digijava.module.aim.util.FiscalCalendarUtil;
import org.digijava.module.common.util.DateTimeUtil;
import org.joda.time.DateTime;

public class GPIReportUtils {

	/**
	 * 
	 * @param indicatorCode
	 * @param formParams
	 * @return generatedRerport {@link GeneratedReport}
	 */
	public static GeneratedReport getGeneratedReportForIndicator(String indicatorCode, JsonBean formParams) {

		switch (indicatorCode) {
			case GPIReportConstants.REPORT_1:
				return getGeneratedReportForIndicator1(formParams);
			case GPIReportConstants.REPORT_5a:
				return getGeneratedReportForIndicator5a(formParams);
			case GPIReportConstants.REPORT_5b:
				return getGeneratedReportForIndicator5b(formParams);
			case GPIReportConstants.REPORT_6:
				return getGeneratedReportForIndicator6(formParams);
			case GPIReportConstants.REPORT_9b:
				return getGeneratedReportForIndicator9b(formParams);
			default:
				throw new RuntimeException("Wrong indicator code:" + indicatorCode);
		}
	}
	
	/**
	 * Create the template for the GPI report 1
	 * 
	 * @param formParams
	 * @return generatedReport 
	 */
	public static GeneratedReport getGeneratedReportForIndicator1(JsonBean formParams) {
		if (EndpointUtils.getSingleValue(formParams, "output", 1) == 2) {
			return getGeneratedReportForIndicator1Output2(formParams);
		}
		
		return getGeneratedReportForIndicator1Output1(formParams);
	}
	
	/**
	 * Create the template for the GPI report 1 Output 1
	 * 
	 * @param formParams
	 * @return generatedReport 
	 */
	public static GeneratedReport getGeneratedReportForIndicator1Output1(JsonBean formParams) {

		ReportSpecificationImpl spec = new ReportSpecificationImpl(GPIReportConstants.REPORT_1, ArConstants.DONOR_TYPE);

		spec.addColumn(new ReportColumn(ColumnConstants.PROJECT_TITLE));
		spec.addColumn(new ReportColumn(ColumnConstants.DONOR_AGENCY));
		spec.addColumn(new ReportColumn(ColumnConstants.ACTUAL_APPROVAL_DATE));
		spec.addColumn(new ReportColumn(ColumnConstants.IMPLEMENTING_AGENCY));
		spec.addColumn(new ReportColumn(ColumnConstants.IMPLEMENTING_AGENCY_GROUPS));
		spec.addColumn(new ReportColumn(ColumnConstants.PRIMARY_SECTOR));
		spec.addColumn(new ReportColumn(ColumnConstants.FINANCING_INSTRUMENT));
		spec.addColumn(new ReportColumn(ColumnConstants.GPI_1_Q6));
		spec.addColumn(new ReportColumn(ColumnConstants.GPI_1_Q6_DESCRIPTION));
		spec.addColumn(new ReportColumn(ColumnConstants.GPI_1_Q7));
		spec.addColumn(new ReportColumn(ColumnConstants.GPI_1_Q8));
		spec.addColumn(new ReportColumn(ColumnConstants.GPI_1_Q9));
		spec.addColumn(new ReportColumn(ColumnConstants.GPI_1_Q10));
		spec.addColumn(new ReportColumn(ColumnConstants.GPI_1_Q10_DESCRIPTION));
		
		spec.addMeasure(new ReportMeasure(MeasureConstants.ACTUAL_COMMITMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.PLANNED_COMMITMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.PIPELINE_COMMITMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.ACTUAL_DISBURSEMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.PLANNED_DISBURSEMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.ACTUAL_EXPENDITURES));
		spec.addMeasure(new ReportMeasure(MeasureConstants.PLANNED_EXPENDITURES));
		spec.addMeasure(new ReportMeasure(MeasureConstants.ACTUAL_DISBURSEMENT_ORDERS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.PLANNED_DISBURSEMENT_ORDERS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.ACTUAL_ESTIMATED_DISBURSEMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.PLANNED_ESTIMATED_DISBURSEMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.ANNUAL_PROPOSED_PROJECT_COST));
		
		spec.getHierarchies().add(new ReportColumn(ColumnConstants.PROJECT_TITLE));
		spec.getHierarchies().add(new ReportColumn(ColumnConstants.DONOR_AGENCY));
		
		applyAppovalStatusFilter(formParams, spec);
		removeDonorAgencyFilterRule(spec);
		applySettings(formParams, spec);
		updateApprovalDateFilter(spec);
		clearYearRangeSettings(spec);

		GeneratedReport generatedReport = EndpointUtils.runReport(spec, ReportAreaImpl.class, null);

		return generatedReport;
	}

	/**
	 * Create the template for the GPI report 1 Output 2
	 * 
	 * @param formParams
	 * @return generatedReport 
	 */
	public static GeneratedReport getGeneratedReportForIndicator1Output2(JsonBean formParams) {

		ReportSpecificationImpl spec = new ReportSpecificationImpl(GPIReportConstants.REPORT_1, ArConstants.DONOR_TYPE);

		spec.addColumn(new ReportColumn(ColumnConstants.PROJECT_TITLE));
		spec.addColumn(new ReportColumn(ColumnConstants.DONOR_AGENCY));
		spec.addColumn(new ReportColumn(ColumnConstants.ACTUAL_APPROVAL_DATE));
		spec.addColumn(new ReportColumn(ColumnConstants.GPI_1_Q6));
		spec.addColumn(new ReportColumn(ColumnConstants.GPI_1_Q7));
		spec.addColumn(new ReportColumn(ColumnConstants.GPI_1_Q8));
		spec.addColumn(new ReportColumn(ColumnConstants.GPI_1_Q9));
		spec.addColumn(new ReportColumn(ColumnConstants.GPI_1_Q10));
		
		spec.addMeasure(new ReportMeasure(MeasureConstants.ACTUAL_COMMITMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.PLANNED_COMMITMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.PIPELINE_COMMITMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.ACTUAL_DISBURSEMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.PLANNED_DISBURSEMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.ACTUAL_EXPENDITURES));
		spec.addMeasure(new ReportMeasure(MeasureConstants.PLANNED_EXPENDITURES));
		spec.addMeasure(new ReportMeasure(MeasureConstants.ACTUAL_DISBURSEMENT_ORDERS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.PLANNED_DISBURSEMENT_ORDERS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.ACTUAL_ESTIMATED_DISBURSEMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.PLANNED_ESTIMATED_DISBURSEMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.ANNUAL_PROPOSED_PROJECT_COST));
		
		spec.getHierarchies().add(new ReportColumn(ColumnConstants.PROJECT_TITLE));
		spec.getHierarchies().add(new ReportColumn(ColumnConstants.DONOR_AGENCY));
		
		applyAppovalStatusFilter(formParams, spec);
		applySettings(formParams, spec);
		updateApprovalDateFilter(spec);
		clearYearRangeSettings(spec);

		GeneratedReport generatedReport = EndpointUtils.runReport(spec, ReportAreaImpl.class, null);

		return generatedReport;
	}

	/**
	 * Create the template for the GPI report 5a
	 * 
	 * @param formParams
	 * @return generatedReport 
	 */
	public static GeneratedReport getGeneratedReportForIndicator5a(JsonBean formParams) {

		ReportSpecificationImpl spec = new ReportSpecificationImpl(GPIReportConstants.REPORT_5a,
				ArConstants.DONOR_TYPE);

		if (isDonorAgency(formParams)) {
			spec.addColumn(new ReportColumn(ColumnConstants.DONOR_AGENCY));
			spec.getHierarchies().add(new ReportColumn(ColumnConstants.DONOR_AGENCY));
		} else {
			spec.addColumn(new ReportColumn(ColumnConstants.DONOR_GROUP));
			spec.getHierarchies().add(new ReportColumn(ColumnConstants.DONOR_GROUP));
		}
		
		spec.addColumn(new ReportColumn(ColumnConstants.ON_OFF_TREASURY_BUDGET));
		spec.addColumn(new ReportColumn(ColumnConstants.HAS_EXECUTING_AGENCY));
		
		spec.getHierarchies().add(new ReportColumn(ColumnConstants.ON_OFF_TREASURY_BUDGET));
		spec.getHierarchies().add(new ReportColumn(ColumnConstants.HAS_EXECUTING_AGENCY));

		spec.addMeasure(new ReportMeasure(MeasureConstants.ACTUAL_DISBURSEMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.PLANNED_DISBURSEMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.DISBURSED_AS_SCHEDULED));
		spec.addMeasure(new ReportMeasure(MeasureConstants.OVER_DISBURSED));
		spec.setGroupingCriteria(GroupingCriteria.GROUPING_YEARLY);
		spec.setSummaryReport(true);

		applyAppovalStatusFilter(formParams, spec);
		applySettings(formParams, spec);
		clearYearRangeSettings(spec);

		GeneratedReport generatedReport = EndpointUtils.runReport(spec, ReportAreaImpl.class, null);

		return generatedReport;
	}

	/**
	 * Create the template for the GPI report 5b
	 * 
	 * @param formParams
	 * @return generatedReport 
	 */
	public static GeneratedReport getGeneratedReportForIndicator5b(JsonBean formParams) {

		ReportSpecificationImpl spec = new ReportSpecificationImpl(GPIReportConstants.REPORT_5b,
				ArConstants.DONOR_TYPE);

		if (isDonorAgency(formParams)) {
			spec.addColumn(new ReportColumn(ColumnConstants.DONOR_AGENCY));
			spec.getHierarchies().add(new ReportColumn(ColumnConstants.DONOR_AGENCY));
		} else {
			spec.addColumn(new ReportColumn(ColumnConstants.DONOR_GROUP));
			spec.getHierarchies().add(new ReportColumn(ColumnConstants.DONOR_GROUP));
		}

		spec.setSummaryReport(true);

		applyAppovalStatusFilter(formParams, spec);
		applySettings(formParams, spec);
		clearYearRangeSettings(spec);

		for (String mtefColumn : getMTEFColumnsForIndicator5b(spec)) {
			spec.addColumn(new ReportColumn(mtefColumn));
		}

		GeneratedReport generatedReport = EndpointUtils.runReport(spec, ReportAreaImpl.class, null);

		return generatedReport;
	}
	
	/**
	 * Create the template for the GPI report 5b
	 * 
	 * @param formParams
	 * @return generatedReport 
	 */
	public static GeneratedReport getGeneratedReportForIndicator5bActDisb(JsonBean formParams) {

		ReportSpecificationImpl spec = new ReportSpecificationImpl(GPIReportConstants.REPORT_5b + " measures",
				ArConstants.DONOR_TYPE);

		if (isDonorAgency(formParams)) {
			spec.addColumn(new ReportColumn(ColumnConstants.DONOR_AGENCY));
			spec.getHierarchies().add(new ReportColumn(ColumnConstants.DONOR_AGENCY));
		} else {
			spec.addColumn(new ReportColumn(ColumnConstants.DONOR_GROUP));
			spec.getHierarchies().add(new ReportColumn(ColumnConstants.DONOR_GROUP));
		}
		
		spec.addMeasure(new ReportMeasure(MeasureConstants.ACTUAL_COMMITMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.PLANNED_COMMITMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.PIPELINE_COMMITMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.ACTUAL_DISBURSEMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.PLANNED_DISBURSEMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.ACTUAL_EXPENDITURES));
		spec.addMeasure(new ReportMeasure(MeasureConstants.PLANNED_EXPENDITURES));
		spec.addMeasure(new ReportMeasure(MeasureConstants.ACTUAL_DISBURSEMENT_ORDERS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.PLANNED_DISBURSEMENT_ORDERS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.ACTUAL_ESTIMATED_DISBURSEMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.PLANNED_ESTIMATED_DISBURSEMENTS));
		spec.addMeasure(new ReportMeasure(MeasureConstants.ANNUAL_PROPOSED_PROJECT_COST));

		spec.setSummaryReport(true);

		applyAppovalStatusFilter(formParams, spec);
		applySettings(formParams, spec);
		clearYearRangeSettings(spec);

		GeneratedReport generatedReport = EndpointUtils.runReport(spec, ReportAreaImpl.class, null);

		return generatedReport;
	}

	/**
	 * Create the template for the GPI report 6
	 * 
	 * @param formParams
	 * @return generatedReport 
	 */
	public static GeneratedReport getGeneratedReportForIndicator6(JsonBean formParams) {

		ReportSpecificationImpl spec = new ReportSpecificationImpl(GPIReportConstants.REPORT_6, ArConstants.DONOR_TYPE);

		if (isDonorAgency(formParams)) {
			spec.addColumn(new ReportColumn(ColumnConstants.DONOR_AGENCY));
			spec.getHierarchies().add(new ReportColumn(ColumnConstants.DONOR_AGENCY));
		} else {
			spec.addColumn(new ReportColumn(ColumnConstants.DONOR_GROUP));
			spec.getHierarchies().add(new ReportColumn(ColumnConstants.DONOR_GROUP));
		}
		
		spec.setGroupingCriteria(GroupingCriteria.GROUPING_YEARLY);
		spec.addMeasure(new ReportMeasure(MeasureConstants.PLANNED_DISBURSEMENTS));
		spec.setSummaryReport(true);

		applyAppovalStatusFilter(formParams, spec);
		applySettings(formParams, spec);
		clearYearRangeSettings(spec);
		
		GeneratedReport generatedReport = EndpointUtils.runReport(spec, ReportAreaImpl.class, null);

		return generatedReport;
	}

	/**
	 * Create the template for the GPI report 9b.
	 * 
	 * @param formParams
	 * @return generatedReport 
	 */
	public static GeneratedReport getGeneratedReportForIndicator9b(JsonBean formParams) {

		ReportSpecificationImpl spec = new ReportSpecificationImpl(GPIReportConstants.REPORT_9b, ArConstants.GPI_TYPE);

		if (isDonorAgency(formParams)) {
			spec.addColumn(new ReportColumn(ColumnConstants.DONOR_AGENCY));
			spec.getHierarchies().add(new ReportColumn(ColumnConstants.DONOR_AGENCY));
		} else {
			spec.addColumn(new ReportColumn(ColumnConstants.DONOR_GROUP));
			spec.getHierarchies().add(new ReportColumn(ColumnConstants.DONOR_GROUP));
		}
		
		spec.setGroupingCriteria(GroupingCriteria.GROUPING_YEARLY);
		spec.addMeasure(new ReportMeasure(MeasureConstants.NATIONAL_BUDGET_EXECUTION_PROCEDURES));
		spec.addMeasure(new ReportMeasure(MeasureConstants.NATIONAL_FINANCIAL_REPORTING_PROCEDURES));
		spec.addMeasure(new ReportMeasure(MeasureConstants.NATIONAL_AUDITING_PROCEDURES));
		spec.addMeasure(new ReportMeasure(MeasureConstants.NATIONAL_PROCUREMENT_EXECUTION_PROCEDURES));
		spec.setSummaryReport(true);

		applyAppovalStatusFilter(formParams, spec);
		applySettings(formParams, spec);
		clearYearRangeSettings(spec);

		GeneratedReport generatedReport = EndpointUtils.runReport(spec, ReportAreaImpl.class, null);

		return generatedReport;
	}

	public static String getHierarchyColumn(JsonBean formParams) {
		if (formParams.get(GPIReportConstants.HIERARCHY_PARAMETER) != null) {
			return (String) formParams.get(GPIReportConstants.HIERARCHY_PARAMETER);
		}

		return GPIReportConstants.HIERARCHY_DONOR_AGENCY;
	}
	
	public static boolean isDonorAgency(JsonBean formParams) {
		String donorHierarchy = getHierarchyColumn(formParams);
		
		return !GPIReportConstants.HIERARCHY_DONOR_GROUP.equals(donorHierarchy);
	}
	
	/**
	 * @param formParams
	 * @param spec
	 */
	public static void applyAppovalStatusFilter(JsonBean formParams, ReportSpecificationImpl spec) {
		if (formParams != null) {
			Map<String, Object> filters = (Map<String, Object>) formParams.get(EPConstants.FILTERS);
			
			AmpReportFilters filterRules = FilterUtils.getFilterRules(filters, null);

			if (filterRules == null) {
				filterRules = new AmpReportFilters();
			}

			ReportElement elem = new ReportElement(new ReportColumn(ColumnConstants.APPROVAL_STATUS));

			// Validated Activities - 4
			FilterRule filterRule = new FilterRule(Arrays.asList("4"), true);
			filterRules.addFilterRule(elem, filterRule);

			spec.setFilters(filterRules);
		}
	}
	
	/**
	 * AMP-26444 When switching to ETH-CALENDAR, the selected year for approval
	 * date should be updated. Since filters are not working with other calendar
	 * than gregorian, we have to update explicitly the dates. E.g.: If in GPI
	 * was selected 2009 in ETH-Calendar, we have to update the approval date
	 * filter in Gregorian CAL 2009: "01/01/2009 to 31/12/2009" in ETH Calendar
	 * equals to "11/09/2016 to 10/09/2017" in GREG
	 * 
	 * @param formParams
	 * @param spec
	 */
	public static void updateApprovalDateFilter(ReportSpecificationImpl spec) {
		AmpReportFilters filters = (AmpReportFilters) spec.getFilters();

		CalendarConverter calendarConverter = (spec.getSettings() != null && spec.getSettings().getCalendar() != null)
				? spec.getSettings().getCalendar() : AmpARFilter.getDefaultCalendar();

		if (filters.getDateFilterRules() != null && FiscalCalendarUtil.isEthiopianCalendar(calendarConverter)) {
			AmpFiscalCalendar ethCalendar = (AmpFiscalCalendar) calendarConverter;
			Optional<ReportColumn> optApprovalColumn = filters.getDateFilterRules().keySet().stream()
					.filter(rc -> rc.getColumnName().equals(ColumnConstants.ACTUAL_APPROVAL_DATE)).findAny();
			ReportColumn approvalColumn = optApprovalColumn.isPresent() ? optApprovalColumn.get()
					: new ReportColumn(ColumnConstants.ACTUAL_APPROVAL_DATE);
			FilterRule gregFilterRule = filters.getDateFilterRules().get(approvalColumn);

			if (gregFilterRule != null) {
				Date gregStart = gregFilterRule.min == null ? null
						: DateTimeUtil.fromJulianNumberToDate(gregFilterRule.min);
				Date gregEnd = gregFilterRule.max == null ? null
						: DateTimeUtil.fromJulianNumberToDate(gregFilterRule.max);

				Date start = FiscalCalendarUtil.toGregorianDate(gregStart, ethCalendar);
				Date end = FiscalCalendarUtil.toGregorianDate(gregEnd, ethCalendar);

				try {
					FilterRule ethFilterRule = DateFilterUtils.getDatesRangeFilterRule(ElementType.DATE,
							DateTimeUtil.toJulianDayNumber(start), DateTimeUtil.toJulianDayNumber(end),
							DateTimeUtil.formatDateOrNull(start), DateTimeUtil.formatDateOrNull(end), false);
					filters.getDateFilterRules().put(approvalColumn, ethFilterRule);
				} catch (AmpApiException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * Apply settings on report specifications
	 * 
	 * @param formParams
	 * @param spec
	 */
	public static void applySettings(JsonBean formParams, ReportSpecificationImpl spec) {
		spec.setSettings(MondrianReportUtils.getCurrentUserDefaultSettings());
		spec.getSettings().getCurrencyFormat().setMinimumFractionDigits(0);
		spec.getSettings().getCurrencyFormat().setMinimumIntegerDigits(1);
		
		SettingsUtils.applySettings(spec, formParams, true);
	}

	/**
	 * @param spec
	 */
	public static void clearYearRangeSettings(ReportSpecificationImpl spec) {
		ReportSettingsImpl reportSettings = (ReportSettingsImpl) spec.getSettings();
		reportSettings.setYearRangeFilter(null);
	}

	public static List<String> getMTEFColumnsForIndicator5b(ReportSpecification spec) {
		
		FilterRule dateFilterRule = getDateFilterRule(spec);
		if (dateFilterRule == null || StringUtils.isBlank(dateFilterRule.min)) {
			throw new RuntimeException("No year selected. Please specify the date filter");
		}
		
		Date fromJulianNumberToDate = DateTimeUtil.fromJulianNumberToDate(dateFilterRule.min);
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromJulianNumberToDate);
		int year = cal.get(Calendar.YEAR);

		List<String> mtefColumns = new ArrayList<>();
		for (int i = 1; i <= 3; i++) {
			mtefColumns.add("MTEF " + (year + i));
		}

		return mtefColumns;
	}
	
	public static int getPivoteYear(ReportSpecification spec) {
		
		FilterRule dateFilterRule = getDateFilterRule(spec);
		if (dateFilterRule == null || StringUtils.isBlank(dateFilterRule.min)) {
			throw new RuntimeException("No year selected. Please specify the date filter");
		}
		
		Date fromJulianNumberToDate = DateTimeUtil.fromJulianNumberToDate(dateFilterRule.min);
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromJulianNumberToDate);
		int year = cal.get(Calendar.YEAR);

		return year;
	}

	public static FilterRule getDateFilterRule(ReportSpecification spec) {
		Optional<Entry<ReportElement, FilterRule>> dateRuleEntry = spec.getFilters()
				.getFilterRules().entrySet().stream()
				.filter(entry -> entry.getKey().type.equals(ElementType.DATE))
				.filter(entry -> entry.getKey().entity == null)
				.findAny();
		
		FilterRule dateRule = dateRuleEntry.isPresent() ? dateRuleEntry.get().getValue() : null;
		
		return dateRule;
	}
	
	/**
	 * @param spec
	 */
	private static void removeDonorAgencyFilterRule(ReportSpecificationImpl spec) {
		ReportElement donorAgencyRuleElement = getFilterRuleElement(spec.getFilters().getAllFilterRules(), 
				ColumnConstants.DONOR_AGENCY);
		
		if (donorAgencyRuleElement != null) {
			spec.getFilters().getAllFilterRules().remove(donorAgencyRuleElement);
		}
	}
	
	public static FilterRule getFilterRule(JsonBean formParams, String columnName) {
		FilterRule filterRule = null;
		if (formParams != null) {
			Map<String, Object> filters = (Map<String, Object>) formParams.get(EPConstants.FILTERS);

			AmpReportFilters filterRules = FilterUtils.getFilterRules(filters, null);
			if (filterRules != null) {
				ReportElement ruleElement = getFilterRuleElement(filterRules.getAllFilterRules(), columnName);
				filterRule = filterRules.getAllFilterRules().get(ruleElement);
			}
		}

		return filterRule;
	}
	
	public static ReportElement getFilterRuleElement(Map<ReportElement, FilterRule> filterRules, String column) {

		Optional<Entry<ReportElement, FilterRule>> dateRuleEntry = filterRules.entrySet().stream()
				.filter(entry -> entry.getKey() != null && entry.getKey().entity != null)
				.filter(entry -> column.equals(entry.getKey().entity.getEntityName()))
				.findAny();

		ReportElement reportElement = dateRuleEntry.isPresent() ? dateRuleEntry.get().getKey() : null;

		return reportElement;
	}
	
	/**
	 * Get the converted year of the julidanDateNumber using the calendar from the report spec
	 * 
	 * @param spec
	 * @param julianDateNumber
	 * @return
	 */
	public static int getYearOfCustomCalendar(ReportSpecification spec, long julianDateNumber) {
		Date date = DateTimeUtil.fromJulianNumberToDate(Long.toString(julianDateNumber));
		CalendarConverter calendarConverter = spec.getSettings().getCalendar();
		if (calendarConverter != null && calendarConverter instanceof AmpFiscalCalendar) {
			AmpFiscalCalendar calendar = (AmpFiscalCalendar) calendarConverter;
			DateTime convDate = FiscalCalendarUtil.convertFromGregorianDate(date, calendar);
			
			return convDate.getYear();
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return calendar.get(Calendar.YEAR);
		
	}
	
	/**
	 * Get GPI Remarks for indicator 5a exports (pdf and xlsx)
	 * 
	 * @param report
	 * @return remarks as string (joined by '\n')
	 */	
	public static String getRemarksForIndicator5a(Map<GPIReportOutputColumn, String> rowData) {
		String year = "";
		boolean isDonorAgency = true;
		List<Long> donorIds = new ArrayList<Long>();
		
		for (Map.Entry<GPIReportOutputColumn, String> e : rowData.entrySet()) {
			GPIReportOutputColumn column = e.getKey();
			if (column.originalColumnName.equals(ColumnConstants.DONOR_GROUP)) {
				isDonorAgency = false;
			}
			if (column.originalColumnName.equals(GPIReportConstants.COLUMN_YEAR)) {
				year = rowData.get(column);
			}
			if (column.originalColumnName.equals(ColumnConstants.DONOR_ID)) {
				donorIds.add(Long.valueOf(rowData.get(column)));
			}
		};
		
		String donorType = isDonorAgency ? GPIReportConstants.HIERARCHY_DONOR_AGENCY
				: GPIReportConstants.HIERARCHY_DONOR_GROUP;

		int y = Integer.parseInt(year);
		Long from = Long.valueOf(DateTimeUtil.toJulianDayNumber(LocalDate.ofYearDay(y, 1)));
		Long to = Long.valueOf(DateTimeUtil.toJulianDayNumber(LocalDate.ofYearDay(y + 1, 1)));

		List<GPIRemark> remarks = GPIDataService.getGPIRemarks(GPIReportConstants.REPORT_5a, 
							donorIds, donorType, from, to);
		
		List<String> remarksAsStringList = remarks.stream()
				.map(remark -> String.format("%s : %s", remark.getDate(), remark.getRemark()))
				.collect(Collectors.toList());
		
		
		return String.join("\n", remarksAsStringList);
	}
	
	/**
	 * Get GPI Remarks for indicator 1 exports (pdf and xlsx)
	 * 
	 * @param report
	 * @return remarks
	 */
	public static List<GPIRemark> getRemarksForIndicator1(GPIReport report) {
		String donorType = GPIReportConstants.HIERARCHY_DONOR_AGENCY;

		FilterRule donorAgencyRule = GPIReportUtils.getFilterRule(report.getOriginalFormParams(), 
				ColumnConstants.DONOR_AGENCY);
		List<Long> ids = donorAgencyRule == null ? new ArrayList<>()
				: donorAgencyRule.values.stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
		
		FilterRule aprDateRule = GPIReportUtils.getFilterRule(report.getOriginalFormParams(), 
				ColumnConstants.ACTUAL_APPROVAL_DATE);
		
		Long min = aprDateRule == null ? 0L : aprDateRule.min != null ? Long.parseLong(aprDateRule.min) : 0L;
		Long max = aprDateRule == null ? 0L : aprDateRule.max != null ? Long.parseLong(aprDateRule.max) : 0L;
		
		List<GPIRemark> remarks = GPIDataService.getGPIRemarks(GPIReportConstants.REPORT_1, ids, donorType, min, max);
		
		return remarks;
	}
}
