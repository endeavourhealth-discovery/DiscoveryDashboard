package org.endeavourhealth.adastrareceiver.api.database.models;

import javax.persistence.*;

@Entity
@Table(name = "message_status", schema = "adastra_receiver", catalog = "")
public class MessageStatusEntity {
    private byte id;
    private String statusDescription;

    @Id
    @Column(name = "id")
    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    @Basic
    @Column(name = "status_description")
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageStatusEntity that = (MessageStatusEntity) o;

        if (id != that.id) return false;
        if (statusDescription != null ? !statusDescription.equals(that.statusDescription) : that.statusDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (statusDescription != null ? statusDescription.hashCode() : 0);
        return result;
    }
}
