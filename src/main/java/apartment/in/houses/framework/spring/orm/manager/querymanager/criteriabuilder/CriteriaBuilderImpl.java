package apartment.in.houses.framework.spring.orm.manager.querymanager.criteriabuilder;

import apartment.in.houses.framework.spring.orm.manager.querymanager.criteriaquery.CriteriaQuery;
import apartment.in.houses.framework.spring.orm.manager.querymanager.criteriaquery.CriteriaQueryImpl;
import apartment.in.houses.framework.spring.orm.manager.querymanager.predicateandorder.OrderImpl;
import apartment.in.houses.framework.spring.orm.manager.querymanager.predicateandorder.interf.Order;
import apartment.in.houses.framework.spring.orm.manager.querymanager.predicateandorder.interf.Predicate;
import apartment.in.houses.framework.spring.orm.manager.querymanager.root.Path;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CriteriaBuilderImpl implements CriteriaBuilder {
    @Override
    public <Y extends Comparable<? super Y>> Predicate greaterThan(Path<Y> path, Y value) {
        return () -> path.toSql() + " > " + value.toString();
    }

    @Override
    public <Y extends Comparable<? super Y>> Predicate lessThan(Path<Y> path, Y value) {
        return () -> path.toSql() + " < " + value.toString();
    }

    @Override
    public Predicate equal(Path<?> path, Object value) {
        return () -> path.toSql() + " = " + value.toString() + "'";
    }

    @Override
    public Predicate notEqual(Path<?> path, Object value) {
        return () -> path.toSql() + " != " + value.toString() + "'";
    }

    @Override
    public Predicate like(Path<?> path, String pattern) {
        return () -> path.toSql() + " LIKE " + pattern + "'";
    }

    @Override
    public Predicate isNull(Path<?> path) {
        return () -> path.toSql() + " IS NULL";
    }

    @Override
    public Predicate isNotNull(Path<?> path) {
        return () -> path.toSql() + " IS NOT NULL";
    }

    @Override
    public Predicate greaterThanOrEqualTo(Path<?> path, Object value) {
        return () -> path.toSql() + " >= " + value.toString();
    }

    @Override
    public Predicate lessThanOrEqualTo(Path<?> path, Object value) {
        return () -> path.toSql() + " <= " + value.toString();
    }

    @Override
    public Predicate and(Predicate... predicates) {
        return () -> Arrays.stream(predicates)
                .map(Predicate::toSql)
                .collect(Collectors.joining(" AND "));
    }

    @Override
    public Predicate or(Predicate... predicates) {
        return () -> Arrays.stream(predicates)
                .map(Predicate::toSql)
                .collect(Collectors.joining(" OR "));
    }

    @Override
    public <T> CriteriaQuery<T> createQuery(Class<T> entityClass) {
        return new CriteriaQueryImpl<>(entityClass);
    }

    @Override
    public <Y extends Comparable<? super Y>> Order asc(Path<Y> path) {
        return new Order() {
            @Override
            public String toSql() {
                return path.toSql();
            }
        };
    }

    @Override
    public <Y extends Comparable<? super Y>> Order desc(Path<Y> path) {
        return new OrderImpl(path, false);
    }
}
