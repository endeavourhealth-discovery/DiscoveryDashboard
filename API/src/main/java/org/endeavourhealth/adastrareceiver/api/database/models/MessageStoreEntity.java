package org.endeavourhealth.adastrareceiver.api.database.models;

import org.endeavourhealth.adastrareceiver.api.database.PersistenceManager;
import org.endeavourhealth.adastrareceiver.api.enums.MessageStatus;
import org.endeavourhealth.adastrareceiver.api.json.JsonGraphOptions;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public static List getGraphValues(byte messageStatus, String period) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        String sql = "";

        switch (period) {
            case "YEAR":
                sql = getYearSQLScript(messageStatus);
                break;
            case "MONTH":
                sql = getMonthSQLScript(messageStatus);
                break;
            case "DAY":
                sql = getDaySQLScript(messageStatus);
                break;
            case "HOUR":
                sql = getHourSQLScript(messageStatus);
                break;
        }

        Query q = entityManager.createNativeQuery(sql);
        System.out.println(sql);

        List resultList =  q.getResultList();

        entityManager.close();

        return resultList;
    }

    private static String getDaySQLScript(byte messageStatus) throws Exception {
        String column = "m.received_date_time";
        String status = "";

        if (messageStatus == MessageStatus.PROCESSED.getMessageStatus()) {
            column = "m.sent_date_time";
            status = String.format("and m.status = %d ", messageStatus);
        }

        if (messageStatus == MessageStatus.ERROR.getMessageStatus()) {
            status = String.format("and m.status = %d ", messageStatus);
        }

        return String.format("select DATE_FORMAT(r.reference_date, \"%%d/%%m/%%Y\"), count(m.id)  \n" +
                "from adastra_receiver.graph_date_range r \n" +
                "left outer join adastra_receiver.message_store m    \n" +
                "\ton IFNULL(DATE(%s), '9999-12-31') = DATE(r.reference_date)   \n" +
                "   %s " +
                " group by DATE(r.reference_date);", column, status );
    }

    private static String getMonthSQLScript(byte messageStatus) throws Exception {
        String column = "m.received_date_time";
        String status = "";

        if (messageStatus == MessageStatus.PROCESSED.getMessageStatus()) {
            column = "m.sent_date_time";
            status = String.format("and m.status = %d ", messageStatus);
        }

        if (messageStatus == MessageStatus.ERROR.getMessageStatus()) {
            status = String.format("and m.status = %d ", messageStatus);
        }

        return String.format("select DATE_FORMAT(r.reference_date, \"01/%%m/%%Y\"), count(m.id)  \n" +
                "from adastra_receiver.graph_date_range r \n" +
                "left outer join adastra_receiver.message_store m    \n" +
                "\ton IFNULL(MONTH(%s), '01') = MONTH(r.reference_date)   \n" +
                " and IFNULL(YEAR(%s), '9999') = YEAR(r.reference_date)   \n" +
                "   %s " +
                " group by YEAR(r.reference_date), MONTH(r.reference_date);", column, column, status );
    }

    private static String getHourSQLScript(byte messageStatus) throws Exception {
        String column = "m.received_date_time";
        String status = "";

        if (messageStatus == MessageStatus.PROCESSED.getMessageStatus()) {
            column = "m.sent_date_time";
            status = String.format("and m.status = %d ", messageStatus);
        }

        if (messageStatus == MessageStatus.ERROR.getMessageStatus()) {
            status = String.format("and m.status = %d ", messageStatus);
        }

        return String.format("select DATE_FORMAT(r.reference_date, \"%%d/%%m/%%Y %%H\"), count(m.id)  \n" +
                "from adastra_receiver .graph_date_range r \n" +
                "left outer join adastra_receiver.message_store m    \n" +
                "\ton IFNULL(HOUR(%s), '01') = HOUR(r.reference_date)   \n" +
                " and IFNULL(DATE(%s), '9999-12-31') = DATE(r.reference_date)   \n" +
                "   %s " +
                " group by DATE(r.reference_date), HOUR(r.reference_date);", column, column, status );
    }

    private static String getYearSQLScript(byte messageStatus) throws Exception {
        String column = "m.received_date_time";
        String status = "";

        if (messageStatus == MessageStatus.PROCESSED.getMessageStatus()) {
            column = "m.sent_date_time";
            status = String.format("and m.status = %d ", messageStatus);
        }

        if (messageStatus == MessageStatus.ERROR.getMessageStatus()) {
            status = String.format("and m.status = %d ", messageStatus);
        }

        return String.format("select DATE_FORMAT(r.reference_date, \"01/01/%%Y\"), count(m.id)  \n" +
                "from adastra_receiver.graph_date_range r \n" +
                "left outer join adastra_receiver.message_store m    \n" +
                " on IFNULL(YEAR(%s), '01') = YEAR(r.reference_date)   \n" +
                "   %s " +
                " group by YEAR(r.reference_date);", column, status );
    }

    public static void initialiseReportResultTable(JsonGraphOptions options) throws Exception {
        String startDate = new SimpleDateFormat("yyyy-MM-dd").format(options.getStartTime());
        String endDate = new SimpleDateFormat("yyyy-MM-dd").format(options.getEndTime());
        String delete = "delete from adastra_receiver.graph_date_range;";

        runSQLScript(delete);

        String insert = String.format("insert into adastra_receiver.graph_date_range (reference_date)\n" +
                "select a.Date\n" +
                "from (\n" +
                "    select '%s' - INTERVAL (a.a + (10 * b.a) + (100 * c.a)) %s as Date\n" +
                "    from (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as a\n" +
                "    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as b\n" +
                "    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as c\n" +
                ") a\n" +
                "where a.Date between '%s' and '%s' ;", endDate, options.getPeriod(), startDate, endDate);


        runSQLScript(insert);

    }

    private static Date getEndOfDay(Date day,Calendar cal) {
        if (day == null) day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE,      cal.getMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND,      cal.getMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    private static Date getBeginningOfDay(Date day,Calendar cal) {
        if (day == null) day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE,      cal.getMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND,      cal.getMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
        return cal.getTime();
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
