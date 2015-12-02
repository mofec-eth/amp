package org.digijava.module.aim.dbentity ;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import org.dgfoundation.amp.ar.ArConstants;

/**
 * https://jira.dgfoundation.org/browse/AMP-20534, https://jira.dgfoundation.org/browse/AMP-20923
 * @author Dolghier Constantin
 *
 */
public class AmpInflationRate implements Serializable, Comparable<AmpInflationRate>
{
	private static final long serialVersionUID = 1L;
	
	//DEFLATOR: review, do we continue to enforce this limits?
	public final static int MIN_DEFLATION_YEAR = ArConstants.MIN_SUPPORTED_YEAR;
	public final static int MAX_DEFLATION_YEAR = ArConstants.MAX_SUPPORTED_YEAR;

	private Long id;
	
	/**
	 * the currency related to which the inflation rate is specified
	 */
	@NotNull
	private AmpCurrency currency;

	/**
	 * the Gregorian period start date for which we specify the inflation rate compared with the previous period
	 */
	private Date periodStart;
	
	//DEFLATOR: remove in the end, keeping it temporarily here to move on with iterative changes
	private Integer year;
	
	/**
	 * inflation rate expressed as a percentage change of the prices from previous period. <br />
	 * Thus, inflationRate = -50% means prices have halved while inflationRate = +100% means prices have doubled
	 */
	private double inflationRate;
	
	public AmpInflationRate() {}
	
	public AmpInflationRate(AmpCurrency currency, Date periodStart, double inflationRate) {
		this.currency = currency;
		this.periodStart = periodStart;
		this.inflationRate = inflationRate;
	}
	
	@Override public boolean equals(Object obj) {
		if (obj instanceof AmpInflationRate) {
			return this.compareTo((AmpInflationRate) obj) == 0;
		}
		return false;
	}
	
	@Override public int compareTo(AmpInflationRate other) {
		if (!this.currency.equals(other.currency))
			return this.currency.compareTo(other.currency);
		
		if (this.periodStart.equals(other.periodStart))
			return this.periodStart.compareTo(other.periodStart);
		
		return Double.compare(this.inflationRate, other.inflationRate);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public AmpCurrency getCurrency() {
		return currency;
	}

	public void setCurrency(AmpCurrency currency) {
		this.currency = currency;
	}
	
	// DEFLATOR: remove it in the end
	public int getYear() {
		if (year == null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(periodStart);
			year = cal.get(Calendar.YEAR);
		}
		return year;
	}

	public Date getPeriodStart() {
		return periodStart;
	}

	public void setPeriodStart(Date periodStart) {
		this.periodStart = periodStart;
	}

	public double getInflationRate() {
		return inflationRate;
	}

	public void setInflationRate(double inflationRate) {
		this.inflationRate = inflationRate;
	}

	//DEFLATOR: remove in the end
	public boolean isConstantCurrency() {
		return false;
	}

	/**
	 * copies all the fields except id from an another instance
	 * @param air
	 */
	public void importDataFrom(AmpInflationRate air) {
		this.currency = air.currency;
		this.inflationRate = air.inflationRate;
		this.periodStart = air.periodStart;
	}

	@AssertTrue()
	private boolean isValid() {
		//boolean res = (this.inflationRate > -100) && (this.currency != null) && (!this.currency.isVirtual()) && (this.periodStart >= MIN_DEFLATION_YEAR) && (this.year <= MAX_DEFLATION_YEAR);
		boolean res = (this.inflationRate > -100) && (this.currency != null) && (!this.currency.isVirtual());
		return res;
	}
	
	@Override public String toString() {
		return String.format("currency: %s, year: %d, rate: %.2f, constant: %s", currency.getCurrencyCode(), this.periodStart, this.inflationRate);
	}
}	
