package org.endeavourhealth.dashboardinformation.json;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonDashboardInformation {
    private String appHealth = null;
    private List<JsonApplicationInformation> applicationInformation = null;
    private List<JsonGraphResults> graphResults = null;

    public String getAppHealth() {
        return appHealth;
    }

    public void setAppHealth(String appHealth) {
        this.appHealth = appHealth;
    }

    public List<JsonApplicationInformation> getApplicationInformation() {
        return applicationInformation;
    }

    public void setApplicationInformation(List<JsonApplicationInformation> applicationInformation) {
        this.applicationInformation = applicationInformation;
    }

    public List<JsonGraphResults> getGraphResults() {
        return graphResults;
    }

    public void setGraphResults(List<JsonGraphResults> graphResults) {
        this.graphResults = graphResults;
    }
}
