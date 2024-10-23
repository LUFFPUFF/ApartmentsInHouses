package apartment.in.houses.util.orm.manager.entitymanager.util;

import apartment.in.houses.util.orm.session.interf.Session;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RemoveUtil {
    public static <T> void handleRemoveWithEmbeddedId(T entity, Field primaryKeyField, String tableName, Session session) throws SQLException {
        List<String> embeddedIdColumnNames = TableUtil.getColumnNames(primaryKeyField.getType());
        String sql = FormatSQLUtil.deleteWithEmbeddedId(tableName, embeddedIdColumnNames);

        try (PreparedStatement statement = session.getConnection().prepareStatement(sql)) {
            Object embeddedIdValue = FieldUtil.getFieldValue(entity, primaryKeyField);
            assert embeddedIdValue != null;
            List<Object> embeddedIdValues = FieldUtil.getValues(embeddedIdValue);
            for (int i = 1; i <= embeddedIdValues.size(); i++) {
                statement.setObject(i, embeddedIdValues.get(i - 1));
            }
            statement.executeUpdate();
        }
    }

    public static <T> void handleRemoveWithPrimaryKey(T entity, Field primaryKeyField, String tableName, Session session) throws SQLException {
        String primaryKeyName = FieldUtil.getPrimaryKeyName(entity.getClass());
        Object primaryKeyValue = FieldUtil.getPrimaryKeyValue(entity, primaryKeyField.getName());
        String sql = FormatSQLUtil.delete(tableName, primaryKeyName);

        try (PreparedStatement statement = session.getConnection().prepareStatement(sql)) {
            statement.setObject(1, primaryKeyValue);
            statement.executeUpdate();
        }
    }

}
