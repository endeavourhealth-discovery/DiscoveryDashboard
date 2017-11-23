package org.endeavourhealth.adastrareceiver.api.json;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonProcessorStatistics {
    private boolean isRunning;
    private String lastRun = null;
    private Long nextRun = null;

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public String getLastRun() {
        return lastRun;
    }

    public void setLastRun(String lastRun) {
        this.lastRun = lastRun;
    }

    public Long getNextRun() {
        return nextRun;
    }

    public void setNextRun(Long nextRun) {
        this.nextRun = nextRun;
    }
}
