package org.digijava.module.aim.form;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class AuditLoggerManagerForm extends ActionForm implements Serializable{

    
    private static final long serialVersionUID = -2614366966871314200L;
    
    private Collection logs;
    private String sortBy;
    private int pagesToShow;
    private Integer currentPage;
    private int pagesSize;
    private Collection pages = null;
    private int offset;
    private String useraction;
    private String frecuency;
    private boolean withLogin;
    
    private String filterBy;
    private String method;

    private List<String> selectedUser;
    private String selectedTeam;
    private Date dateFrom;
    private Date dateTo;
    

    public String getUseraction() {
        return useraction;
    }

    public void setUseraction(String useraction) {
        this.useraction = useraction;
    }

    public String getFrecuency() {
        return frecuency;
    }

    public void setFrecuency(String frecuency) {
        this.frecuency = frecuency;
    }

    public int getOffset() {
        int value;
        if (getCurrentPage()> (this.getPagesToShow()/2)){
            value = (this.getCurrentPage() - (this.getPagesToShow()/2))-1;
        }
        else {
            value = 0;
        }
        setOffset(value);
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    public Collection getPages() {
        return pages;
    }
    public void setPages(Collection pages) {
        this.pages = pages;
          if(pages!=null)
          {    
              this.pagesSize=pages.size();
          }
    }
    
    public int getPagesToShow() {
        return pagesToShow;
    }
    public void setPagesToShow(int pagesToShow) {
        this.pagesToShow = pagesToShow;
    }
    public Integer getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
    public int getPagesSize() {
        return pagesSize;
    }
    public void setPagesSize(int pagesSize) {
        this.pagesSize = pagesSize;
    }
    public Collection getLogs() {
        return logs;
    }
    public void setLogs(Collection logs) {
        this.logs = logs;
    }
    public String getSortBy() {
        return sortBy;
    }
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public boolean isWithLogin() {
        return withLogin;
    }

    public void setWithLogin(boolean withLogin) {
        this.withLogin = withLogin;
    }
    public List getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(List<String> users) {
        this.selectedUser = users;
    }
    public String getSelectedTeam() {
        return selectedTeam;
    }

    public void setSelectedTeam(String selectedTeam) {
        this.selectedTeam = selectedTeam;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
    
    public String getFilterBy() {
        return filterBy;
    }

    public void setFilterBy(String filterBy) {
        this.filterBy = filterBy;
    }
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
