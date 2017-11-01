package org.endeavourhealth.adastrareceiver.api.database.models;

import org.endeavourhealth.adastrareceiver.api.database.PersistenceManager;
import org.endeavourhealth.adastrareceiver.api.json.JsonConcept;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Entity
@Table(name = "concept", schema = "information_model")
public class ConceptEntity {
    private long id;
    private String name;
    private Byte status;
    private String shortName;
    private String structureType;
    private Long structureId;
    private long count;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 250)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "short_name", nullable = true, length = 125)
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Basic
    @Column(name = "structure_type", nullable = true, length = 3)
    public String getStructureType() {
        return structureType;
    }

    public void setStructureType(String structureType) {
        this.structureType = structureType;
    }

    @Basic
    @Column(name = "structure_id", nullable = true)
    public Long getStructureId() {
        return structureId;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }

    @Basic
    @Column(name = "count", nullable = false)
    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConceptEntity that = (ConceptEntity) o;

        if (id != that.id) return false;
        if (count != that.count) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (structureType != null ? !structureType.equals(that.structureType) : that.structureType != null)
            return false;
        if (structureId != null ? !structureId.equals(that.structureId) : that.structureId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (structureType != null ? structureType.hashCode() : 0);
        result = 31 * result + (structureId != null ? structureId.hashCode() : 0);
        result = 31 * result + (int) (count ^ (count >>> 32));
        return result;
    }

    public static List<ConceptEntity> getAllConcepts() throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ConceptEntity> cq = cb.createQuery(ConceptEntity.class);
        Root<ConceptEntity> rootEntry = cq.from(ConceptEntity.class);
        CriteriaQuery<ConceptEntity> all = cq.select(rootEntry);
        TypedQuery<ConceptEntity> allQuery = entityManager.createQuery(all);
        List<ConceptEntity> ret = allQuery.getResultList();

        entityManager.close();

        return ret;
    }

    public static ConceptEntity getConceptById(Integer id) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        ConceptEntity ret = entityManager.find(ConceptEntity.class, id);
        entityManager.close();

        return ret;
    }

    public static List<ConceptEntity> getConceptsByName(String conceptName) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ConceptEntity> cq = cb.createQuery(ConceptEntity.class);
        Root<ConceptEntity> rootEntry = cq.from(ConceptEntity.class);

        Predicate predicate = cb.like(cb.upper(rootEntry.get("name")), "%" + conceptName.toUpperCase() + "%");

        cq.where(predicate);
        TypedQuery<ConceptEntity> query = entityManager.createQuery(cq);
        List<ConceptEntity> ret = query.getResultList();

        entityManager.close();

        return ret;
    }

    public static void deleteConcept(Integer conceptId) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        Long conceptLong = Integer.toUnsignedLong(conceptId);
        ConceptEntity conceptEntity = entityManager.find(ConceptEntity.class, conceptLong);
        entityManager.getTransaction().begin();
        entityManager.remove(conceptEntity);
        entityManager.getTransaction().commit();

        entityManager.close();
    }

    public static List<ConceptEntity> getConceptsFromList(List<Integer> conceptsIds) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ConceptEntity> cq = cb.createQuery(ConceptEntity.class);
        Root<ConceptEntity> rootEntry = cq.from(ConceptEntity.class);

        Predicate predicate = rootEntry.get("id").in(conceptsIds);

        cq.where(predicate);
        TypedQuery<ConceptEntity> query = entityManager.createQuery(cq);

        List<ConceptEntity> ret = query.getResultList();

        entityManager.close();

        return ret;
    }

    public static void saveConcept(JsonConcept concept) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        ConceptEntity conceptEntity = new ConceptEntity();
        entityManager.getTransaction().begin();
        conceptEntity.setId(concept.getId());
        conceptEntity.setName(concept.getName());
        conceptEntity.setStatus(concept.getStatus());
        conceptEntity.setShortName(concept.getShort_name());
        conceptEntity.setStructureType(concept.getStructure_type());
        conceptEntity.setStructureId(concept.getStructure_id());
        conceptEntity.setCount(concept.getCount());
        entityManager.persist(conceptEntity);
        entityManager.getTransaction().commit();

        entityManager.close();
    }

    public static void updateConcept(JsonConcept concept) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        ConceptEntity cohortEntity = entityManager.find(ConceptEntity.class, concept.getId());
        entityManager.getTransaction().begin();
        cohortEntity.setId(concept.getId());
        cohortEntity.setName(concept.getName());
        cohortEntity.setStatus(concept.getStatus());
        cohortEntity.setShortName(concept.getShort_name());
        cohortEntity.setStructureType(concept.getStructure_type());
        cohortEntity.setStructureId(concept.getStructure_id());
        cohortEntity.setCount(1);
        entityManager.persist(cohortEntity);
        entityManager.getTransaction().commit();

        entityManager.close();
    }

    public static List<ConceptEntity> getCommonConcepts(Integer limit) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ConceptEntity> cq = cb.createQuery(ConceptEntity.class);
        Root<ConceptEntity> rootEntry = cq.from(ConceptEntity.class);

        cq.orderBy(cb.desc(rootEntry.get("count")));
        TypedQuery<ConceptEntity> query = entityManager.createQuery(cq);

        query.setMaxResults(limit);
        List<ConceptEntity> ret = query.getResultList();

        entityManager.close();

        return ret;
    }
}
