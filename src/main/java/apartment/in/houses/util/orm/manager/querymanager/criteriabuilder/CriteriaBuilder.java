package apartment.in.houses.util.orm.manager.querymanager.criteriabuilder;

import apartment.in.houses.util.orm.manager.querymanager.criteriaquery.CriteriaQuery;
import apartment.in.houses.util.orm.manager.querymanager.predicateandexpression.OrderImpl;
import apartment.in.houses.util.orm.manager.querymanager.predicateandexpression.interf.Order;
import apartment.in.houses.util.orm.manager.querymanager.predicateandexpression.interf.Predicate;
import apartment.in.houses.util.orm.manager.querymanager.root.Path;

public interface CriteriaBuilder {
    <Y extends Comparable<? super Y>> Predicate greaterThan(Path<Y> path, Y value);
    <Y extends Comparable<? super Y>> Predicate lessThan(Path<Y> path, Y value);
    Predicate equal(Path<?> path, Object value);
    Predicate notEqual(Path<?> path, Object value);
    Predicate like(Path<?> path, String pattern);
    Predicate isNull(Path<?> path);
    Predicate isNotNull(Path<?> path);
    Predicate greaterThanOrEqualTo(Path<?> path, Object value);
    Predicate lessThanOrEqualTo(Path<?> path, Object value);
    Predicate and(Predicate... predicates);
    Predicate or(Predicate... predicates);
    <T> CriteriaQuery<T> createQuery(Class<T> entityClass);
    <Y extends Comparable<? super Y>> Order asc(Path<Y> path);
    <Y extends Comparable<? super Y>> Order desc(Path<Y> path);
}
