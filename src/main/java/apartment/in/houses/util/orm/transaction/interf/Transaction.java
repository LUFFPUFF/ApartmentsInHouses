package apartment.in.houses.util.orm.transaction.interf;

import java.sql.SQLException;

public interface Transaction {

    void begin() throws SQLException;
    void commit() throws SQLException;
    void rollback();
    boolean isActive();
}
