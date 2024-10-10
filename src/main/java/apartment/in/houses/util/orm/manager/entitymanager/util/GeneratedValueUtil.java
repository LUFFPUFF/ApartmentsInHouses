package apartment.in.houses.util.orm.manager.entitymanager.util;

import apartment.in.houses.util.orm.session.interf.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GeneratedValueUtil {
    private static final String sequenceSql = "SELECT nextval('your_sequence_name')";
    private static final String selectSql = "SELECT next_id FROM id_generation_table FOR UPDATE";
    private static final String updateSql = "UPDATE id_generation_table SET next_id = ? WHERE next_id = ?";

    public static Long getNextSequenceValue(Session session) throws SQLException {
        try (PreparedStatement statement = session.getConnection().prepareStatement(sequenceSql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new SQLException("Не удалось получить следующее значение из последовательности.");
            }
        }
    }

    public static Long getNextTableValue(Session session) throws SQLException {
        try (PreparedStatement statement = session.getConnection().prepareStatement(selectSql)) {
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long currentId = resultSet.getLong(1);
                Long nextId = currentId + 1;

                try (PreparedStatement updateStatement = session.getConnection().prepareStatement(updateSql)) {
                    updateStatement.setLong(1, nextId);
                    updateStatement.setLong(2, currentId);
                    updateStatement.executeUpdate();
                }

                return nextId;
            } else {
                throw new SQLException("Не удалось получить следующее значение из таблицы генерации.");
            }
        }
    }
}
