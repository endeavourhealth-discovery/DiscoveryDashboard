package org.endeavourhealth.adastrareceiver.api.json;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonConceptRelationship {
    private Integer id = null;
    private Integer sourceConcept = null;
    private Integer targetConcept = null;
    private String targetLabel = null;
    private Integer relationship_order = null;
    private Long relationship_type = null;
    private Long count = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSourceConcept() {
        return sourceConcept;
    }

    public void setSourceConcept(Integer sourceConcept) {
        this.sourceConcept = sourceConcept;
    }

    public Integer getTargetConcept() {
        return targetConcept;
    }

    public void setTargetConcept(Integer targetConcept) {
        this.targetConcept = targetConcept;
    }

    public String getTargetLabel() {
        return targetLabel;
    }

    public void setTargetLabel(String targetLabel) {
        this.targetLabel = targetLabel;
    }

    public Integer getRelationship_order() {
        return relationship_order;
    }

    public void setRelationship_order(Integer relationship_order) {
        this.relationship_order = relationship_order;
    }

    public Long getRelationship_type() {
        return relationship_type;
    }

    public void setRelationship_type(Long relationship_type) {
        this.relationship_type = relationship_type;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
