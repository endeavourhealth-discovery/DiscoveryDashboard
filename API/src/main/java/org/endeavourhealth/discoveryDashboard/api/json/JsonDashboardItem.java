package org.endeavourhealth.discoveryDashboard.api.json;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonDashboardItem {
    private Integer id = null;
    private String title = null;
    private Byte dashboardType = null;
    private String apiUrl = null;
    private String dashboardUrl = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Byte getDashboardType() {
        return dashboardType;
    }

    public void setDashboardType(Byte dashboardType) {
        this.dashboardType = dashboardType;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getDashboardUrl() {
        return dashboardUrl;
    }

    public void setDashboardUrl(String dashboardUrl) {
        this.dashboardUrl = dashboardUrl;
    }
}
