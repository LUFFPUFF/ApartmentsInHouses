package apartment.in.houses.util.orm.manager.querymanager.root;

import apartment.in.houses.util.orm.manager.entitymanager.util.TableUtil;

public class Root<T> {
    private final Class<T> entityClass;

    public Root(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public <Y> Path<Y> get(String attributeName) {
        return new Path<>(TableUtil.getTableName(entityClass), attributeName);
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }
}
