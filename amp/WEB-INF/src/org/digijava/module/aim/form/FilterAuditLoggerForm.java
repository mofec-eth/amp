package org.digijava.module.aim.form;

import org.apache.struts.action.ActionForm;
import org.digijava.module.aim.helper.DateConversion;
import org.joda.time.DateTime;

import java.util.Date;


public class FilterAuditLoggerForm extends ActionForm {

    public static final Integer LAST_HOUR_OF_DAY = 30;
    public static final Integer LAST_MINUTE_OF_HOUR = 59;
    public static final Integer LAST_SECOND_OF_MINUTE = 59;
    private Long selectedUser;
    private String selectedTeam;
    private String selectedDateFrom;
    private String selectedDateTo;
    private Long effectiveSelectedUser;
    private String effectiveSelectedTeam;
    private Date effectiveDateFrom;
    private Date effectiveDateTo;

    public FilterAuditLoggerForm() {
        super();

    }

    public String getSelectedDateFrom() {
        return selectedDateFrom;
    }

    public void setSelectedDateFrom(String selectedDateFrom) {
        this.selectedDateFrom = selectedDateFrom;
    }

    public String getSelectedDateTo() {
        return selectedDateTo;
    }

    public void setSelectedDateTo(String selectedDateTo) {
        this.selectedDateTo = selectedDateTo;
    }

    public Long getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Long selectedUser) {
        this.selectedUser = selectedUser;
    }

    public String getSelectedTeam() {
        return selectedTeam;
    }

    public void setSelectedTeam(String selectedTeam) {
        this.selectedTeam = selectedTeam;
    }

    public Long getEffectiveSelectedUser() {
        return effectiveSelectedUser;
    }

    public void setEffectiveSelectedUser(Long effectiveSelectedUser) {
        this.effectiveSelectedUser = effectiveSelectedUser;
    }

    public String getEffectiveSelectedTeam() {
        return effectiveSelectedTeam;
    }

    public void setEffectiveSelectedTeam(String effectiveSelectedTeam) {
        this.effectiveSelectedTeam = effectiveSelectedTeam;
    }

    public Date getEffectiveDateFrom() {
        return effectiveDateFrom;
    }

    public void setEffectiveDateFrom(Date effectiveDateFrom) {
        this.effectiveDateFrom = effectiveDateFrom;
    }

    public Date getEffectiveDateTo() {
        return effectiveDateTo;
    }

    public void setEffectiveDateTo(Date effectiveDateTo) {
        this.effectiveDateTo = effectiveDateTo;
    }

    public void populateEffectiveFilters() {
        effectiveSelectedUser = this.getSelectedUser() != null && !this.getSelectedUser().equals(-1L)
                ? this.getSelectedUser() : null;
        effectiveSelectedTeam = this.getSelectedTeam() != null && !this.getSelectedTeam().equals("-1")
                && effectiveSelectedUser == null ? this.getSelectedTeam() : null;
        if (effectiveSelectedTeam == null) {
            this.setSelectedTeam(null);
        }
        effectiveDateFrom = DateConversion.getDate(this.getSelectedDateFrom());
        if (this.getSelectedDateTo() != null) {

            effectiveDateTo = new DateTime(DateConversion.getDate(this.getSelectedDateTo())).
                    plusHours(LAST_HOUR_OF_DAY).plusMinutes(LAST_MINUTE_OF_HOUR).
                    plusSeconds(LAST_SECOND_OF_MINUTE).toDate();
        }
    }
}
