package org.endeavourhealth.adastrareceiver.soapws.database.models;

import org.endeavourhealth.adastrareceiver.soapws.database.PersistenceManager;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "message_store", schema = "adastra_receiver")
public class MessageStoreEntity {
    private long id;
    private long source;
    private Timestamp receivedDateTime;
    private String sentDateTime;
    private String messagePayload;
    private byte status;
    private String errorMessage;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "source")
    public long getSource() {
        return source;
    }

    public void setSource(long source) {
        this.source = source;
    }

    @Basic
    @Column(name = "received_date_time")
    public Timestamp getReceivedDateTime() {
        return receivedDateTime;
    }

    public void setReceivedDateTime(Timestamp receivedDateTime) {
        this.receivedDateTime = receivedDateTime;
    }

    @Basic
    @Column(name = "sent_date_time")
    public String getSentDateTime() {
        return sentDateTime;
    }

    public void setSentDateTime(String sentDateTime) {
        this.sentDateTime = sentDateTime;
    }

    @Basic
    @Column(name = "message_payload")
    public String getMessagePayload() {
        return messagePayload;
    }

    public void setMessagePayload(String messagePayload) {
        this.messagePayload = messagePayload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageStoreEntity that = (MessageStoreEntity) o;

        if (id != that.id) return false;
        if (source != that.source) return false;
        if (receivedDateTime != null ? !receivedDateTime.equals(that.receivedDateTime) : that.receivedDateTime != null)
            return false;
        if (sentDateTime != null ? !sentDateTime.equals(that.sentDateTime) : that.sentDateTime != null) return false;
        if (messagePayload != null ? !messagePayload.equals(that.messagePayload) : that.messagePayload != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (source ^ (source >>> 32));
        result = 31 * result + (receivedDateTime != null ? receivedDateTime.hashCode() : 0);
        result = 31 * result + (sentDateTime != null ? sentDateTime.hashCode() : 0);
        result = 31 * result + (messagePayload != null ? messagePayload.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "status")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "error_message")
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static void storeMessage(MessageStoreEntity message) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(message);
        entityManager.getTransaction().commit();

        entityManager.close();
    }
}
