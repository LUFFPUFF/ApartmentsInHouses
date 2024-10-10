package apartment.in.houses.util.orm.manager.querymanager.criteriaquery;

import apartment.in.houses.util.orm.manager.entitymanager.util.TableUtil;
import apartment.in.houses.util.orm.manager.querymanager.predicateandorder.interf.Order;
import apartment.in.houses.util.orm.manager.querymanager.predicateandorder.interf.Predicate;
import apartment.in.houses.util.orm.manager.querymanager.root.Root;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CriteriaQueryImpl<T> implements CriteriaQuery<T> {
    private final Class<T> entityClass;
    private final List<Predicate> predicates = new ArrayList<>();
    private final List<Order> orderBy = new ArrayList<>();

    public CriteriaQueryImpl(Class<T> entityClass) {
        this.entityClass = entityClass;

    }

    @Override
    public Root<T> from(Class<T> entityClass) {
        if (!this.entityClass.equals(entityClass)) {
            throw new IllegalArgumentException("Invalid entity class: " + entityClass.getName());
        }
        return new Root<>(entityClass);
    }

    @Override
    public CriteriaQuery<T> select(Root<T> root) {
        return this;
    }

    @Override
    public CriteriaQuery<T> where(Predicate... predicates) {
        this.predicates.addAll(Arrays.asList(predicates));
        return this;
    }

    @Override
    public CriteriaQuery<T> orderBy(Order... orders) {
        orderBy.addAll(Arrays.asList(orders));
        return this;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public String build() {
        StringBuilder sql = new StringBuilder("SELECT * FROM " +
                TableUtil.getTableName(entityClass));

        if (!predicates.isEmpty()) {
            sql.append(" WHERE ");
            for (int i = 0; i < predicates.size(); i++) {
                sql.append(predicates.get(i).toSql());
                if (i < predicates.size() - 1) {
                    sql.append(" AND ");
                }
            }
        }

        if (!orderBy.isEmpty()) {
            sql.append(" ORDER BY ");
            for (int i = 0; i < orderBy.size(); i++) {
                sql.append(orderBy.get(i).toSql());
                if (i < orderBy.size() - 1) {
                    sql.append(", ");
                }
            }
        }

        return sql.toString();
    }
}
