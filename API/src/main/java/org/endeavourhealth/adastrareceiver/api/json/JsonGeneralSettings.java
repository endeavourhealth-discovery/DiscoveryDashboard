package org.endeavourhealth.adastrareceiver.api.json;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonGeneralSettings {
    private Integer refreshRate = null;
    private Long processorDelay = null;
    private String messagingApiUrl = null;
    private boolean useKeycloak;
    private Integer processBatchSize = null;

    public Integer getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(Integer refreshRate) {
        this.refreshRate = refreshRate;
    }

    public Long getProcessorDelay() {
        return processorDelay;
    }

    public void setProcessorDelay(Long processorDelay) {
        this.processorDelay = processorDelay;
    }

    public String getMessagingApiUrl() {
        return messagingApiUrl;
    }

    public void setMessagingApiUrl(String messagingApiUrl) {
        this.messagingApiUrl = messagingApiUrl;
    }

    public boolean isUseKeycloak() {
        return useKeycloak;
    }

    public void setUseKeycloak(boolean useKeycloak) {
        this.useKeycloak = useKeycloak;
    }

    public Integer getProcessBatchSize() {
        return processBatchSize;
    }

    public void setProcessBatchSize(Integer processBatchSize) {
        this.processBatchSize = processBatchSize;
    }
}
