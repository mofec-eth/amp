package org.dgfoundation.amp.newreports;

import java.util.LinkedHashMap;

import org.dgfoundation.amp.newreports.ReportElement.ElementType;

/**
 * class which identifies a path to sort by, like for Example [Primary Sector] or [Measures][2013][Q3][Actual Commitments]
 * @author Dolghier Constantin, Nadejda Mandrescu
 */
public class SortingInfo {
	/** sorting ascending or descending */
	public final boolean ascending;
	/** tuple of columns and/or measure to sort */
	public final LinkedHashMap<ReportElement, FilterRule> sortByTuple;
	/** whether this is sorting by column totals */
	public final boolean isTotals;
	
	/**
	 * Configures sorting by a single text column or measure column that is without grouping
	 * @param entity - column / measure
	 * @param ascending - true if sorting ascending
	 */
	public SortingInfo(NamedTypedEntity entity, boolean ascending) {
		this(entity, ascending, false);
	}
	/**
	 * Configures sorting by a single text column or measure column that is without grouping
	 * @param entity - column / measure
	 * @param ascending - true if sorting ascending
	 * @param isTotals
	 */
	public SortingInfo(NamedTypedEntity entity, boolean ascending, boolean isTotals) {
		this.sortByTuple = new LinkedHashMap<ReportElement, FilterRule>();
		this.sortByTuple.put(new ReportElement(entity), null);
		this.ascending = ascending;
		this.isTotals = isTotals;
	}
	
	/** This is a custom sorting by date that assumes that there is a grouping order by year/quarter/month/measure.
	 * Use generic {@link #SortingInfo(LinkedHashMap, boolean)} constructor for more flexibility.
	 */
	public SortingInfo(String year, ReportMeasure measure, boolean ascending) {
		this(year, null, null, measure, ascending);
	}
	
	/** This is a custom sorting by date that assumes that there is a grouping order by year/quarter/month/measure.
	 * Use generic {@link #SortingInfo(LinkedHashMap, boolean)} constructor for more flexibility.
	 */
	public SortingInfo(String year, String quarter, ReportMeasure measure, boolean ascending) {
		this(year, quarter, null, measure, ascending);
	}
	/**
	 * This is a custom sorting by date that assumes that there is a grouping order by year/quarter/month/measure.
	 * Use generic {@link #SortingInfo(LinkedHashMap, boolean)} constructor for more flexibility.  
	 */
	public SortingInfo(String year, String quarter, String month, ReportMeasure measure, boolean ascending) {
		this(null, year, quarter, month, measure, ascending);
	}
	
	/**
	 * Custom sorting by hierarchy totals for the specified grouping of dates and measure
	 * @param hierarchy - the hierarchy column
	 * @param year - the year of the grouping to sort (mandatory)
	 * @param quarter - null or the quarter name (Q1..Q4)
	 * @param month - null or the month name (January..December)
	 * @param measure - the measure
	 * @param ascending - true if sorting ascending
	 */
	public SortingInfo(ReportColumn hierarchy, String year, String quarter, String month, ReportMeasure measure, boolean ascending) {
		this.isTotals = hierarchy != null;
		this.ascending = ascending;
		this.sortByTuple = new LinkedHashMap<ReportElement, FilterRule>();
		if (hierarchy != null)
			this.sortByTuple.put(new ReportElement(hierarchy), null);
		this.sortByTuple.put(new ReportElement(ElementType.YEAR), new FilterRule(year, true, false));
		if (quarter !=null) 
			this.sortByTuple.put(new ReportElement(ElementType.QUARTER), new FilterRule(quarter, true, false));
		if (month !=null) 
			this.sortByTuple.put(new ReportElement(ElementType.MONTH), new FilterRule(month, true, false));
		this.sortByTuple.put(new ReportElement(measure), null);
	}
	
	/**
	 * Configures sorting by a tuple of columns (and measure)
	 * @param sortByTuple - list of 
	 * @param ascending
	 */
	public SortingInfo(LinkedHashMap<ReportElement, FilterRule> sortByTuple, boolean ascending) {
		this(sortByTuple, ascending, false);
	}
	
	private SortingInfo(LinkedHashMap<ReportElement, FilterRule> sortByTuple, boolean ascending, boolean isTotals) {
		this.sortByTuple = sortByTuple;
		this.ascending = ascending;
		this.isTotals = isTotals;
	}
	
	@Override
	public String toString() {
		return "SortingInfo: " + (ascending ? "ASC" : "DESC") + " sortByTuple=" + sortByTuple + ", isTotals=" + isTotals; 
	}
}