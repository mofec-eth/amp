/**
 * 
 */
package org.dgfoundation.amp.newreports;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Stores a report configuration by implementing {@link ReportSpecification} and defines all data required to generate a report. 
 * @author Nadejda Mandrescu
 *
 */
public class ReportSpecificationImpl implements ReportSpecification {
	private String reportName = null;
	private Set<ReportColumn> columns = null;
	private Set<ReportColumn> hierarchies = null;
	private List<ReportMeasure> measures = null;
	private ReportFilters filters = null;
	private ReportSettings settings = null;
	private List<SortingInfo> sorters = null;
	private GroupingCriteria groupingCriteria = GroupingCriteria.GROUPING_TOTALS_ONLY;
	private boolean summaryReport = false;
	private boolean calculateRowTotals = false;
	private boolean calculateColumnTotals = false;
	private int rowsHierarchiesTotals = 0;
	private int colsHierarchyTotals = 0;
	private boolean displayEmptyFundingColumns = false;
	private boolean displayEmptyFundingRows = false;
	
	public ReportSpecificationImpl(String reportName) {
		this.reportName = reportName;
	}

	@Override
	public String getReportName() {
		return reportName;
	}

	@Override
	public Set<ReportColumn> getColumns() {
		return columns;
	}
	
	/**
	 * Configures the columns set.
	 * Refer to {@link #getColumns()} for more details
	 */
	public void setColumns(Set<ReportColumn> columns) {
		this.columns = columns;
	}
	
	/**
	 * Adds a column to the columns set.
	 * Refer to {@link #getColumns()} for more details
	 * @param column - {@link ReportColumn}  
	 */
	public void addColumn(ReportColumn column) {
		if (columns == null) {
			columns = new LinkedHashSet<ReportColumn>();
		}
		this.columns.add(column);
	}

	@Override
	public List<ReportMeasure> getMeasures() {
		return measures;
	}
	
	/**
	 * Configures measures list. 
	 * Refer to {@link #getMeasures()} for more details
	 * @param measures
	 */
	public void setMeasures(List<ReportMeasure> measures) {
		this.measures = measures;
	}
	
	/**
	 * Adds a measure to the measure list.
	 * Refer to {@link #getMeasures()} for more details
	 * @param measure - {@link ReportMeasure}
	 */
	public void addMeasure(ReportMeasure measure) {
		if (this.measures == null) {
			this.measures = new ArrayList<ReportMeasure>();
		}
		this.measures.add(measure);
	}

	@Override
	public Set<ReportColumn> getHierarchies() {
		return hierarchies;
	}
	
	public void setHierarchies(Set<ReportColumn> hierarchies) {
		this.hierarchies = hierarchies;
	}

	@Override
	public ReportFilters getFilters() {
		return filters;
	}
	
	public void setFilters(ReportFilters filters) {
		this.filters = filters;
	}

	/**
	 * @return the settings
	 */
	public ReportSettings getSettings() {
		return settings;
	}

	/**
	 * @param settings the settings to set
	 */
	public void setSettings(ReportSettings settings) {
		this.settings = settings;
	}

	@Override
	public List<SortingInfo> getSorters() {
		return sorters;
	}
	
	/**
	 * Configures sorting information. 
	 * @param sorters - a list of {@link SortingInfo} data
	 */
	public void setSorters(List<SortingInfo> sorters) {
		this.sorters = sorters;
	}
	
	/**
	 * Adds sorting criteria
	 * @param sorter - {@link SortingInfo}
	 */
	public void addSorter(SortingInfo sorter) {
		if (sorters == null) {
			this.sorters = new ArrayList<SortingInfo>();
		}
		this.sorters.add(sorter);
	}

	@Override
	public GroupingCriteria getGroupingCriteria() {
		return groupingCriteria;
	}
	
	/**
	 * Configures grouping criteria
	 * @param groupingCriteria
	 */
	public void setGroupingCriteria(GroupingCriteria groupingCriteria) {
		this.groupingCriteria = groupingCriteria;
	}

	@Override
	public boolean isSummaryReport() {
		return summaryReport;
	}
	
	/**
	 * @param summaryReport - true if this must be a summary report, i.e. show only sub-totals and totals
	 */
	public void setSummaryReport(boolean summaryReport) {
		this.summaryReport = summaryReport;
	}

	/**
	 * Configures whether totals per each row must be calculated
	 * @param calculateRowTotals 
	 */
	public void setCalculateRowTotals(boolean calculateRowTotals) {
		this.calculateRowTotals = calculateRowTotals;
	}

	@Override
	public boolean isCalculateRowTotals() {
		return calculateRowTotals;
	}

	/**
	 * Configures whether totals per each measure column must be calculated
	 * @param calculateColumnTotals 
	 */
	public void setCalculateColumnTotals(boolean calculateColumnTotals) {
		this.calculateColumnTotals = calculateColumnTotals;
	}
	
	@Override
	public boolean isCalculateColumnTotals() {
		return calculateColumnTotals;
	}
	
	/**
	 * Configures whether columns with no funding data should be displayed or not
	 * @param displayEmptyFundingColumns
	 */
	public void setDisplayEmptyFundingColumns(boolean displayEmptyFundingColumns) {
		this.displayEmptyFundingColumns = displayEmptyFundingColumns;
	}

	@Override
	public boolean isDisplayEmptyFundingColumns() {
		return this.displayEmptyFundingColumns;
	}

	/**
	 * 
	 * @param displayEmptyFundingRows
	 */
	public void setDisplayEmptyFundingRows(boolean displayEmptyFundingRows) {
		this.displayEmptyFundingRows = displayEmptyFundingRows;
	}
	
	@Override
	public boolean isDisplayEmptyFundingRows() {
		return this.displayEmptyFundingRows;
	}

	/**
	 * @return the number of rows hierarchies to calculate the subtotals
	 */
	public int getRowsHierarchiesTotals() {
		return rowsHierarchiesTotals;
	}

	/**
	 * @param rowsHierarchiesTotals the rowHierarchiesTotals to set
	 */
	public void setRowsHierarchiesTotals(int rowsHierarchiesTotals) {
		this.rowsHierarchiesTotals = rowsHierarchiesTotals;
	}

	/**
	 * @return the number of column hierarchies to calculate the subtotals
	 */
	public int getColsHierarchyTotals() {
		return colsHierarchyTotals;
	}

	/**
	 * @param colsHierarchyTotals the number of column hierarchies to calculate the subtotals
	 */
	public void setColsHierarchyTotals(int colsHierarchyTotals) {
		this.colsHierarchyTotals = colsHierarchyTotals;
	}

}