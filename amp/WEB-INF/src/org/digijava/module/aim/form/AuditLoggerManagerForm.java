package org.digijava.module.aim.form;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionForm;

import org.digijava.kernel.user.User;

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
    private Long selectedUser;
    private Collection<User> userList;    
    private List<Long> userId;
    private List<String> teamList;
    private String filteredTeam;
    private String dateFrom;
    private String dateTo;


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
    
    public List<String> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<String> teamList) {
        this.teamList = teamList;
    }

    public List<Long> getUserId() {
        return userId;
    }

    public void setUserId(List<Long> userId) {
        this.userId = userId;
    }

    public Long getSelectedUser() {
        return selectedUser;
    }
    public void setSelectedUser(Long users) {
        this.selectedUser = users;
    }

   public Collection<User> getUserList() {
        return userList;
   }

    public void setUserList(Collection<User> userList) {
        this.userList = userList;
    }
    public String getFilteredTeam() {
        return filteredTeam;
    }

    public void setFilteredTeam(String filteredTeam) {
        this.filteredTeam = filteredTeam;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

}
