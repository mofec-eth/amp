/*
 *  AmpApplicationSettings.java
 *  @Author Priyajith C
 *  Created: 18-Aug-2004
 */

package org.digijava.module.aim.dbentity;

import java.io.*;

public class AmpApplicationSettings implements Serializable {

	private Long ampAppSettingsId;

	private AmpTeam team;

	private AmpTeamMember member;

	private Integer defaultRecordsPerPage;
	
	private Integer reportStartYear;
	
	private Integer reportEndYear;
	
	private AmpCurrency currency;

	private AmpFiscalCalendar fiscalCalendar;

	private String language;

	private String defaultPerspective; // defunct // still used though...if this is null in db report generator will not work

	private Boolean useDefault; /*
								 * use customized settings or use default team
								 * settings
								 */

	private AmpPerspective perspectiveId;

	private AmpReports defaultTeamReport;
        private Integer defaultReportsPerPage;

  public AmpReports getDefaultTeamReport() {
		return defaultTeamReport;
	}

	public void setDefaultTeamReport(AmpReports defaultTeamReport) {
		this.defaultTeamReport = defaultTeamReport;
	}

	public Long getAmpAppSettingsId() {
		return this.ampAppSettingsId;
	}

	public void setAmpAppSettingsId(Long ampAppSettingsId) {
		this.ampAppSettingsId = ampAppSettingsId;
	}

	public AmpTeam getTeam() {
		return this.team;
	}

	public void setTeam(AmpTeam team) {
		this.team = team;
	}

	public AmpTeamMember getMember() {
		return this.member;
	}

	public void setMember(AmpTeamMember member) {
		this.member = member;
	}

	public Integer getDefaultRecordsPerPage() {
		return this.defaultRecordsPerPage;
	}

	public void setDefaultRecordsPerPage(Integer defaultRecordsPerPage) {
		this.defaultRecordsPerPage = defaultRecordsPerPage;
	}

	public AmpCurrency getCurrency() {
		return this.currency;
	}

	public void setCurrency(AmpCurrency currency) {
		this.currency = currency;
	}

	public AmpFiscalCalendar getFiscalCalendar() {
		return this.fiscalCalendar;
	}

	public void setFiscalCalendar(AmpFiscalCalendar fiscalCalendar) {
		this.fiscalCalendar = fiscalCalendar;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDefaultPerspective() {
		return this.defaultPerspective;
	}

	public void setDefaultPerspective(String defaultPerspective) {
		this.defaultPerspective = defaultPerspective;
	}

	public Boolean getUseDefault() {
		return this.useDefault;
	}

	public void setUseDefault(Boolean useDefault) {
		this.useDefault = useDefault;
	}

	/**
	 * @return Returns the perspectiveId.
	 */
	public AmpPerspective getPerspectiveId() {
		return perspectiveId;
	}

        public Integer getDefaultReportsPerPage() {
          return defaultReportsPerPage;
        }

  /**
	 * @param perspectiveId
	 *            The perspectiveId to set.
	 */
	public void setPerspectiveId(AmpPerspective perspectiveId) {
		this.perspectiveId = perspectiveId;
	}

        public void setDefaultReportsPerPage(Integer  defaultReportsPerPage) {
          this.defaultReportsPerPage = defaultReportsPerPage;
        }

	public Integer getReportStartYear() {
	    return reportStartYear;
	}

	public void setReportStartYear(Integer reportStartYear) {
	    this.reportStartYear = reportStartYear;
	}

	public Integer getReportEndYear() {
	    return reportEndYear;
	}

	public void setReportEndYear(Integer reportEndYear) {
	    this.reportEndYear = reportEndYear;
	}
}
