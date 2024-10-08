package apartment.in.houses.util.orm.session.connection.interf;

import java.sql.Connection;

public interface ConnectionManager {
    Connection getConnection();
    void closeConnection(Connection connection);
}
