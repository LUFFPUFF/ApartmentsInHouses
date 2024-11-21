package apartment.in.houses.framework.spring.orm.transaction.interf;

import java.sql.SQLException;

public interface Transaction {

    void begin() throws SQLException;
    void commit() throws SQLException;
    void rollback();
    boolean isActive();
}
