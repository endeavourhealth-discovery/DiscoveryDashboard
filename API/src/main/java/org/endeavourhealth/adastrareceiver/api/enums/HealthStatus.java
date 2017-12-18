package org.endeavourhealth.adastrareceiver.api.enums;

public enum HealthStatus {
    SUCCESS("success"),
    WARNING("warning"),
    DANGER("danger"),
    INFO("info"),
    PRIMARY("primary");


    private String healthStatus;

    HealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getHealthStatus() {
        return healthStatus;
    }
}
