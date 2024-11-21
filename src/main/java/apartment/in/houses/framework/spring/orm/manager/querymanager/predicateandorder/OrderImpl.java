package apartment.in.houses.framework.spring.orm.manager.querymanager.predicateandorder;

import apartment.in.houses.framework.spring.orm.manager.querymanager.predicateandorder.interf.Order;
import apartment.in.houses.framework.spring.orm.manager.querymanager.root.Path;

public class OrderImpl implements Order {
    private final Path<?> path;
    private final boolean ascending;

    public OrderImpl(Path<?> path, boolean ascending) {
        this.path = path;
        this.ascending = ascending;
    }

    public String getExpression() {
        return path.toSql();
    }

    @Override
    public String toSql() {
        return path.toSql() + (ascending ? " ASC" : " DESC");
    }
}
