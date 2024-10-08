package apartment.in.houses.util.orm.manager.querymanager.criteriaquery;

import apartment.in.houses.util.orm.manager.querymanager.predicateandexpression.OrderImpl;
import apartment.in.houses.util.orm.manager.querymanager.predicateandexpression.interf.Order;
import apartment.in.houses.util.orm.manager.querymanager.predicateandexpression.interf.Predicate;
import apartment.in.houses.util.orm.manager.querymanager.root.Root;

public interface CriteriaQuery<T> {
    Root<T> from(Class<T> entityClass);
    CriteriaQuery<T> select(Root<T> root);
    CriteriaQuery<T> where(Predicate... predicates);
    CriteriaQuery<T> orderBy(Order... orders);
    String build();
}
