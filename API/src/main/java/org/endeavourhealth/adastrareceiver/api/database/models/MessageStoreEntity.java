package org.endeavourhealth.adastrareceiver.api.database.models;

import org.endeavourhealth.adastrareceiver.api.database.PersistenceManager;
import org.endeavourhealth.adastrareceiver.api.enums.MessageStatus;
import org.endeavourhealth.adastrareceiver.api.json.JsonConcept;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "message_store", schema = "adastra_receiver")
public class MessageStoreEntity {
    private long id;
    private long source;
    private Timestamp receivedDateTime;
    private byte status;
    private String sentDateTime;
    private String messagePayload;

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
    @Column(name = "status")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
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
        if (status != that.status) return false;
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
        result = 31 * result + (int) status;
        result = 31 * result + (sentDateTime != null ? sentDateTime.hashCode() : 0);
        result = 31 * result + (messagePayload != null ? messagePayload.hashCode() : 0);
        return result;
    }

    public static void storeMessage(MessageStoreEntity message) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(message);
        entityManager.getTransaction().commit();

        entityManager.close();
    }

    public static List<MessageStoreEntity> getMessagesLimitByNumber(Integer limit) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MessageStoreEntity> cq = cb.createQuery(MessageStoreEntity.class);
        Root<MessageStoreEntity> rootEntry = cq.from(MessageStoreEntity.class);
        CriteriaQuery<MessageStoreEntity> all = cq.select(rootEntry);
        cq.orderBy(cb.desc(rootEntry.get("id")));
        TypedQuery<MessageStoreEntity> allQuery = entityManager.createQuery(all);
        allQuery.setFirstResult(0);
        allQuery.setMaxResults(limit);
        List<MessageStoreEntity> ret = allQuery.getResultList();

        entityManager.close();

        return ret;
    }

    public static String resendMessages(Integer messageId) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(
                "UPDATE MessageStoreEntity m " +
                        "set m.status = :processed " +
                        "where m.id <= :message");
        query.setParameter("processed", MessageStatus.PROCESSED.getMessageStatus());
        query.setParameter("message", messageId);

        int updateCount = query.executeUpdate();

        entityManager.getTransaction().commit();

        System.out.println(updateCount + " updated");

        entityManager.close();

        return updateCount + " updated";
    }

    public static String updateMessageStatus(long messageId, byte status) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(
                "UPDATE MessageStoreEntity m " +
                        "set m.status = :newStatus " +
                        "where m.id = :message");
        query.setParameter("newStatus", status);
        query.setParameter("message", messageId);

        int updateCount = query.executeUpdate();

        entityManager.getTransaction().commit();

        System.out.println(updateCount + " updated");

        entityManager.close();

        return updateCount + " updated";
    }

    public static List<MessageStoreEntity> getEarliestUnsendMessages() throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(
                "select m from MessageStoreEntity m " +
                        "where m.status = :unsentStatus " +
                        "order by m.receivedDateTime asc " +
                        "");
        query.setParameter("unsentStatus", (byte)0);
        query.setMaxResults(10);

        List<MessageStoreEntity> mse = query.getResultList();

        entityManager.getTransaction().commit();

        entityManager.close();

        return mse;
    }

    public static Long getTotalNumberOfMessages(byte status) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<MessageStoreEntity> rootEntry = cq.from(MessageStoreEntity.class);

        Predicate predicate = cb.equal(rootEntry.get("status"), status);

        cq.select((cb.countDistinct(rootEntry)));

        if (status != -1) { //All messages
            cq.where(predicate);
        }

        Long ret = entityManager.createQuery(cq).getSingleResult();

        entityManager.close();

        return ret;
    }

    public static int runSQLScript(String script) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

       entityManager.getTransaction().begin();
       try {
           Query q = entityManager.createNativeQuery(script);

           return q.executeUpdate();
       }
       finally {
           entityManager.getTransaction().commit();
           entityManager.close();
       }
    }

}
