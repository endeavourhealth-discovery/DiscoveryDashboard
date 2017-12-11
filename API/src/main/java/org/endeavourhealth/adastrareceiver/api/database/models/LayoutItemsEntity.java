package org.endeavourhealth.adastrareceiver.api.database.models;

import org.endeavourhealth.adastrareceiver.api.database.PersistenceManager;
import org.endeavourhealth.adastrareceiver.api.json.JsonLayoutItem;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "layout_items", schema = "discovery_dashboard")
public class LayoutItemsEntity {
    private int id;
    private String username;
    private String title;
    private int dashboardItem;
    private byte size;
    private byte position;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "dashboard_item")
    public int getDashboardItem() {
        return dashboardItem;
    }

    public void setDashboardItem(int dashboardItem) {
        this.dashboardItem = dashboardItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LayoutItemsEntity that = (LayoutItemsEntity) o;
        return id == that.id &&
                dashboardItem == that.dashboardItem &&
                Objects.equals(username, that.username) &&
                Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, username, title, dashboardItem);
    }

    @Basic
    @Column(name = "size")
    public byte getSize() {
        return size;
    }

    public void setSize(byte size) {
        this.size = size;
    }

    @Basic
    @Column(name = "position")
    public byte getPosition() {
        return position;
    }

    public void setPosition(byte position) {
        this.position = position;
    }


    public static List<LayoutItemsEntity> getLayoutItems(String username) throws Exception {
        EntityManager entityManager = PersistenceManager.getConfigEntityManager();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LayoutItemsEntity> cq = cb.createQuery(LayoutItemsEntity.class);
        Root<LayoutItemsEntity> rootEntry = cq.from(LayoutItemsEntity.class);
        CriteriaQuery<LayoutItemsEntity> all = cq.select(rootEntry);

        Predicate predicate = cb.equal(rootEntry.get("username"), username);

        cq.orderBy(cb.asc(rootEntry.get("position")));
        cq.where(predicate);

        TypedQuery<LayoutItemsEntity> allQuery = entityManager.createQuery(all);
        List<LayoutItemsEntity> ret = allQuery.getResultList();

        entityManager.close();

        return ret;
    }

    public static Integer saveLayoutItem(JsonLayoutItem item) throws Exception {
        Integer itemId = null;
        EntityManager entityManager = PersistenceManager.getConfigEntityManager();

        LayoutItemsEntity itemsEntity = new LayoutItemsEntity();
        entityManager.getTransaction().begin();

        itemsEntity.setTitle(item.getTitle());
        itemsEntity.setUsername(item.getUsername());
        itemsEntity.setTitle(item.getTitle());
        itemsEntity.setPosition(item.getPosition());
        itemsEntity.setSize(item.getSize());
        itemsEntity.setUsername(item.getUsername());
        itemsEntity.setDashboardItem(item.getDashboardItem());

        if (item.getId() != null) {
            itemsEntity.setId(item.getId());
            itemId = item.getId();
        }


        entityManager.merge(itemsEntity);


        if (itemId == null) {
            Query q = entityManager.createNativeQuery("SELECT LAST_INSERT_ID();");

            itemId = Integer.parseInt(q.getSingleResult().toString());

        }
        entityManager.getTransaction().commit();

        entityManager.close();

        return itemId;
    }
}
