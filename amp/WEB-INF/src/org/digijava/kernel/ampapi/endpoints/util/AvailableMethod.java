package org.digijava.kernel.ampapi.endpoints.util;

import io.swagger.annotations.ApiModelProperty;

public class AvailableMethod {
    public AvailableMethod() {
        this.ui = false;
    }
    
    @ApiModelProperty(value = "Name to display", example = "Translations")
    private String name;

    @ApiModelProperty(value = "Should be visible in UI?", example = "false")
    private Boolean ui;

    @ApiModelProperty(value = "Path to operation", example = "/rest/translations/label-translations")
    private String endpoint;

    @ApiModelProperty(value = "Http method", example = "POST")
    private String method;

    @ApiModelProperty(value = "Method id", example = "Translations")
    private String id;

    @ApiModelProperty(value = "Used to group filters under different tabs", example = "Unassigned")
    private String tab;

    @ApiModelProperty("Columns linked to this filter")
    private String []columns;

    private FilterType [] filterType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Boolean getUi() {
        return ui;
    }

    public void setUi(Boolean ui) {
        this.ui = ui;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FilterType[] getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType[] filterType) {
        this.filterType = filterType;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }
    

}
