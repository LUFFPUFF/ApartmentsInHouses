package apartment.in.houses.framework.spring.orm.manager.entitymanager.util;

import java.util.Collections;
import java.util.List;

public class FormatSQLUtil {

    public static String insert(String tableName, List<String> columnNames) {
        return String.format(
                "INSERT INTO %s (%s) VALUES (%s)",
                tableName,
                String.join(", ", columnNames),
                String.join(", ", Collections.nCopies(columnNames.size(), "?"))
        );
    }



    public static String delete(String tableName, String primaryKeyName) {
        return String.format(
                "DELETE FROM %s WHERE %s = ?",
                tableName,
                primaryKeyName);
    }

    public static String get(String tableName, String primaryKeyName) {
        return String.format(
                "SELECT * FROM %s WHERE %s = ?",
                tableName,
                primaryKeyName);
    }

    public static String getWithEmbeddedId(String tableName, List<String> embeddedIdColumnNames) {
        String whereClause = whereClause(embeddedIdColumnNames);

        return String.format(
                "SELECT * FROM %s WHERE %s",
                tableName,
                whereClause);
    }

    public static String deleteWithEmbeddedId(String tableName, List<String> embeddedIdColumnNames) {
        String whereClause = whereClause(embeddedIdColumnNames);

        return String.format(
                "DELETE FROM %s WHERE %s",
                tableName,
                whereClause);
    }

    private static String whereClause(List<String> embeddedIdColumnNames) {
        StringBuilder whereClause = new StringBuilder();
        for (int i = 0; i < embeddedIdColumnNames.size(); i++) {
            whereClause.append(embeddedIdColumnNames.get(i)).append(" = ?");
            if (i < embeddedIdColumnNames.size() - 1) {
                whereClause.append(" AND ");
            }
        }
        return whereClause.toString();
    }

    public static String getOneToManyQuery(String tableName, String foreignKeyColumn) {
        return "SELECT * FROM " + tableName + " WHERE " + foreignKeyColumn + " = ?";
    }

    public static String joinClausOneToMany(String targetTable, Class<?> entityClass, String foreignKeyColumn) {
        return String.format(" LEFT JOIN %s ON %s.id = %s.%s",
                targetTable,
                TableUtil.getTableName(entityClass),
                targetTable,
                foreignKeyColumn);
    }

    public static String joinClausManyToOne(String targetTable, Class<?> entityClass, String foreignKeyColumn, String targetPrimaryKey) {
        return String.format(" LEFT JOIN %s ON %s.%s = %s.%s",
                targetTable,
                TableUtil.getTableName(entityClass),
                foreignKeyColumn,
                targetTable,
                targetPrimaryKey);
    }

}