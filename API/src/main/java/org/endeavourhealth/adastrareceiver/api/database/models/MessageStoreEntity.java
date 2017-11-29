package org.endeavourhealth.adastrareceiver.api.database.models;

import org.endeavourhealth.adastrareceiver.api.database.PersistenceManager;
import org.endeavourhealth.adastrareceiver.api.enums.MessageStatus;

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

    @Basic
    @Column(name = "error_message")
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static List<MessageStoreEntity> getMessages(Integer pageNumber, Integer pageSize,
                                                       String orderColumn, boolean ascending,
                                                       List<Integer> statusList) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MessageStoreEntity> cq = cb.createQuery(MessageStoreEntity.class);
        Root<MessageStoreEntity> rootEntry = cq.from(MessageStoreEntity.class);
        CriteriaQuery<MessageStoreEntity> all = cq.select(rootEntry);

        if (statusList != null && statusList.size() > 0) {
            Predicate predicate = rootEntry.get("status").in(statusList);
            cq.where(predicate);
        }

        if (ascending)
            cq.orderBy(cb.asc(rootEntry.get(orderColumn)));
        else
            cq.orderBy(cb.desc(rootEntry.get(orderColumn)));

        TypedQuery<MessageStoreEntity> allQuery = entityManager.createQuery(all);
        allQuery.setFirstResult((pageNumber.intValue() - 1) * pageSize.intValue());
        allQuery.setMaxResults(pageSize.intValue());
        List<MessageStoreEntity> ret = allQuery.getResultList();

        entityManager.close();

        return ret;
    }

    public static String resendSingleMessage(Long messageId) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(
                "UPDATE MessageStoreEntity m " +
                        "set m.status = :processed, " +
                        " m.errorMessage = :emptyError " +
                        "where m.id = :message");
        query.setParameter("processed", MessageStatus.RECEIVED.getMessageStatus());
        query.setParameter("emptyError", "");
        query.setParameter("message", messageId);

        int updateCount = query.executeUpdate();

        entityManager.getTransaction().commit();

        entityManager.close();

        return updateCount + " message updated";
    }

    public static String resendErrorMessages() throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(
                "UPDATE MessageStoreEntity m " +
                        "set m.status = :received, " +
                        " m.errorMessage = :emptyError " +
                        "where m.status = :error");
        query.setParameter("received", MessageStatus.RECEIVED.getMessageStatus());
        query.setParameter("emptyError", "");
        query.setParameter("error", MessageStatus.ERROR.getMessageStatus());

        int updateCount = query.executeUpdate();

        entityManager.getTransaction().commit();

        entityManager.close();

        return updateCount + " messages updated";
    }

    public static String resendMessagesBefore(Long messageId) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(
                "UPDATE MessageStoreEntity m " +
                        "set m.status = :received, " +
                        " m.errorMessage = :emptyError " +
                        "where m.id < :messageId");
        query.setParameter("received", MessageStatus.RECEIVED.getMessageStatus());
        query.setParameter("emptyError", "");
        query.setParameter("messageId", messageId);

        int updateCount = query.executeUpdate();

        entityManager.getTransaction().commit();

        entityManager.close();

        return updateCount + " message updated";
    }

    public static String resendMessagesAfter(Long messageId) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(
                "UPDATE MessageStoreEntity m " +
                        "set m.status = :received, " +
                        " m.errorMessage = :emptyError " +
                        "where m.id > :messageId");
        query.setParameter("received", MessageStatus.RECEIVED.getMessageStatus());
        query.setParameter("emptyError", "");
        query.setParameter("messageId", messageId);

        int updateCount = query.executeUpdate();

        entityManager.getTransaction().commit();

        entityManager.close();

        return updateCount + " message updated";
    }

    public static String updateMessageStatus(Long messageId, byte status) throws Exception {
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

        return updateCount + " message updated";
    }

    public static String resendAllMessages() throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(
                "UPDATE MessageStoreEntity m " +
                        "set m.status = :received ");
        query.setParameter("received", MessageStatus.RECEIVED.getMessageStatus());

        int updateCount = query.executeUpdate();

        entityManager.getTransaction().commit();

        System.out.println(updateCount + " updated");

        entityManager.close();

        return updateCount + " message updated";
    }

    public static List<MessageStoreEntity> getEarliestUnsentMessages(Integer limit) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(
                "select m from MessageStoreEntity m " +
                        "where m.status = :unsentStatus " +
                        "order by m.receivedDateTime asc " +
                        "");
        query.setParameter("unsentStatus", MessageStatus.RECEIVED.getMessageStatus());
        query.setMaxResults(limit);

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

    public static Long getMessageCount(List<Integer> statusList) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<MessageStoreEntity> rootEntry = cq.from(MessageStoreEntity.class);


        if (statusList != null && statusList.size() > 0) {
            Predicate predicate = rootEntry.get("status").in(statusList);
            cq.where(predicate);
        }

        cq.select((cb.countDistinct(rootEntry)));

        Long ret = entityManager.createQuery(cq).getSingleResult();

        entityManager.close();

        return ret;
    }

    public static List getGraphValues(byte messageStatus) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        String column = "m.received_date_time";
        String status = "";
        if (messageStatus == MessageStatus.PROCESSED.getMessageStatus()) {
            column = "m.sent_date_time";
            status = String.format("and m.status = %d ", messageStatus);
        }

        if (messageStatus == MessageStatus.ERROR.getMessageStatus()) {
            status = String.format("and m.status = %d ", messageStatus);
        }

        String sql = String.format("select DATE_FORMAT(r.min_date, \"%%d/%%m/%%Y\"), count(m.id) " +
                " from adastra_receiver.graph_date_range r" +
                " left outer join adastra_receiver.message_store m  " +
                "   on IFNULL(%s, '9999-12-31') >= r.min_date " +
                "   and IFNULL(%s, '9999-12-31') <= r.max_date " +
                "   %s " +
                " group by r.min_date ",column, column, status);

        Query q = entityManager.createNativeQuery(sql);
        System.out.println(sql);

        List resultList =  q.getResultList();

        entityManager.close();

        return resultList;
    }

    /*private void initialiseReportResultTable() throws Exception {
        List<String> initialiseScripts = new ArrayList<>();
        String period = "DAYS";

        initialiseScripts.add("delete from adastra_receiver.graph_date_range;");

        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        Date beginning;
        Date end;

        Integer number = 10;
                //Integer number = Integer.parseInt(10);

        String insert = "insert into adastra_receiver.graph_date_range (min_date, max_date)\n" +
                "values ('%s', '%s')";

        int precision = Calendar.DAY_OF_YEAR;
        int substractionPrecision = Calendar.YEAR;

        if (period.equals("MONTHS")) {
            precision = Calendar.DAY_OF_MONTH;
            substractionPrecision = Calendar.MONTH;
        }

        if (period.equals("DAYS")) {
            precision = Calendar.DATE;
            substractionPrecision = Calendar.DATE;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Integer i = 0; i < number; i++) {

//            // get the last day of the month/year
//            if (options.getDateType().equals("absolute"))
//                c.set(precision, c.getActualMaximum(precision));
            end = c.getTime();


            // get the first day of the month/year
            if (options.getDateType().equals("absolute")) {
                c.set(precision, c.getActualMinimum(precision));
                beginning = c.getTime();
            } else {
                Calendar b = Calendar.getInstance();
                b.setTime(c.getTime());
                b.add(substractionPrecision, -1);
                //Make sure same date isnt counted twice
                b.add(Calendar.DAY_OF_MONTH, 1);
                beginning = b.getTime();
            }

            c.add(substractionPrecision, -1);
            initialiseScripts.add(String.format(insert, dateFormat.format(beginning).toString(),dateFormat.format(end).toString()));


        }

        for (String script : initialiseScripts) {
            runSQLScript(script);
        }

    }*/

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
