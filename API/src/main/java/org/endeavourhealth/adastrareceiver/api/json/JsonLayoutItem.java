package org.endeavourhealth.adastrareceiver.api.json;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonLayoutItem {
    private Integer id = null;
    private String username = null;
    private String title = null;
    private Byte position = null;
    private Byte size = null;
    private Integer dashboardItem = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Byte getPosition() {
        return position;
    }

    public void setPosition(Byte position) {
        this.position = position;
    }

    public Byte getSize() {
        return size;
    }

    public void setSize(Byte size) {
        this.size = size;
    }

    public Integer getDashboardItem() {
        return dashboardItem;
    }

    public void setDashboardItem(Integer dashboardItem) {
        this.dashboardItem = dashboardItem;
    }
}
