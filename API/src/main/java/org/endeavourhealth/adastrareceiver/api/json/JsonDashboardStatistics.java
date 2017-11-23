package org.endeavourhealth.adastrareceiver.api.json;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonDashboardStatistics {
    private Long totalMessageCount = null;
    private Long receivedMessageCount = null;
    private Long sentMessageCount = null;
    private Long errorMessageCount = null;

    public Long getTotalMessageCount() {
        return totalMessageCount;
    }

    public void setTotalMessageCount(Long totalMessageCount) {
        this.totalMessageCount = totalMessageCount;
    }

    public Long getReceivedMessageCount() {
        return receivedMessageCount;
    }

    public void setReceivedMessageCount(Long receivedMessageCount) {
        this.receivedMessageCount = receivedMessageCount;
    }

    public Long getSentMessageCount() {
        return sentMessageCount;
    }

    public void setSentMessageCount(Long sentMessageCount) {
        this.sentMessageCount = sentMessageCount;
    }

    public Long getErrorMessageCount() {
        return errorMessageCount;
    }

    public void setErrorMessageCount(Long errorMessageCount) {
        this.errorMessageCount = errorMessageCount;
    }
}
