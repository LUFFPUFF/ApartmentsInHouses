package apartment.in.houses.util.orm.manager.querymanager.predicateandexpression;

import apartment.in.houses.util.orm.manager.querymanager.predicateandexpression.interf.Predicate;

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
