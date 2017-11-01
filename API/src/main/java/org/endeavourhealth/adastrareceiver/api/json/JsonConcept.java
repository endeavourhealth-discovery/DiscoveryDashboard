package org.endeavourhealth.adastrareceiver.api.json;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonConcept {
    private Integer id = null;
    private String name = null;
    private byte status = 0;
    private String short_name = null;
    private String structure_type = null;
    private Long structure_id = null;
    private Integer count = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getStructure_type() {
        return structure_type;
    }

    public void setStructure_type(String structure_type) {
        this.structure_type = structure_type;
    }

    public Long getStructure_id() {
        return structure_id;
    }

    public void setStructure_id(Long structure_id) {
        this.structure_id = structure_id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
