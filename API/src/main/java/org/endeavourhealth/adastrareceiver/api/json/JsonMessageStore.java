package org.endeavourhealth.adastrareceiver.api.json;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonMessageStore {
    private Integer id = null;
    private Integer source = null;
    private LocalDateTime receivedDateTime = null;
    private byte status = 0;
    private String sentDateTime = null;
    private String messagePayload = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public LocalDateTime getReceivedDateTime() {
        return receivedDateTime;
    }

    public void setReceivedDateTime(LocalDateTime receivedDateTime) {
        this.receivedDateTime = receivedDateTime;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getSentDateTime() {
        return sentDateTime;
    }

    public void setSentDateTime(String sentDateTime) {
        this.sentDateTime = sentDateTime;
    }

    public String getMessagePayload() {
        return messagePayload;
    }

    public void setMessagePayload(String messagePayload) {
        this.messagePayload = messagePayload;
    }
}
