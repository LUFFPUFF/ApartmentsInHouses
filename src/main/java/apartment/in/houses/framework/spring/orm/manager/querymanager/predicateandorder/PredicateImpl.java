package apartment.in.houses.framework.spring.orm.manager.querymanager.predicateandorder;

import apartment.in.houses.framework.spring.orm.manager.querymanager.predicateandorder.interf.Predicate;

public class PredicateImpl implements Predicate {

    private final String expression;

    public PredicateImpl(String expression) {
        this.expression = expression;
    }

    @Override
    public String toSql() {
        return expression;
    }
}
