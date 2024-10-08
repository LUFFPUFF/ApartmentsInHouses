package apartment.in.houses.util.orm.manager.entitymanager.util;

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
                "DELETE FROM %s WHERE %s = ",
                tableName,
                primaryKeyName);
    }

    public static String get(String tableName, String primaryKeyName) {
        return String.format(
                "SELECT * FROM %s WHERE %s = ?",
                tableName,
                primaryKeyName);
    }

}
