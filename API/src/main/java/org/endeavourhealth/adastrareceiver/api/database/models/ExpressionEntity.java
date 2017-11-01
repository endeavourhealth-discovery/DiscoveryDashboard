package org.endeavourhealth.adastrareceiver.api.database.models;

import javax.persistence.*;

/**
 * Created by studu on 09/06/2017.
 */
@Entity
@Table(name = "expression", schema = "information_model", catalog = "")
public class ExpressionEntity {
    private long id;
    private String expression;
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
    @Column(name = "expression", nullable = false, length = 1024)
    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
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

        ExpressionEntity that = (ExpressionEntity) o;

        if (id != that.id) return false;
        if (count != that.count) return false;
        if (expression != null ? !expression.equals(that.expression) : that.expression != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (expression != null ? expression.hashCode() : 0);
        result = 31 * result + (int) (count ^ (count >>> 32));
        return result;
    }
}
