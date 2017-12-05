package org.endeavourhealth.adastrareceiver.api.database.models;

import org.endeavourhealth.adastrareceiver.api.database.PersistenceManager;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dashboard_items", schema = "discovery_dashboard")
public class DashboardItemsEntity {
    private int id;
    private String title;
    private String apiUrl;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "api_url")
    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DashboardItemsEntity that = (DashboardItemsEntity) o;
        return id == that.id &&
                Objects.equals(title, that.title) &&
                Objects.equals(apiUrl, that.apiUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, apiUrl);
    }

    public static List<DashboardItemsEntity> getDashboardItems() throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DashboardItemsEntity> cq = cb.createQuery(DashboardItemsEntity.class);
        Root<DashboardItemsEntity> rootEntry = cq.from(DashboardItemsEntity.class);
        CriteriaQuery<DashboardItemsEntity> all = cq.select(rootEntry);

        cq.orderBy(cb.asc(rootEntry.get("id")));

        TypedQuery<DashboardItemsEntity> allQuery = entityManager.createQuery(all);
        List<DashboardItemsEntity> ret = allQuery.getResultList();

        entityManager.close();

        return ret;
    }
}
