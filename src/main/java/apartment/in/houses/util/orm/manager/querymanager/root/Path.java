package apartment.in.houses.util.orm.manager.querymanager.root;

public class Path<T> {

    private final String tableName;
    private final String attributeName;

    public Path(String tableName, String attributeName) {
        this.tableName = tableName;
        this.attributeName = attributeName;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getTableName() {
        return tableName;
    }
    public String toSql() {
        return tableName + "." + attributeName;
    }
}
