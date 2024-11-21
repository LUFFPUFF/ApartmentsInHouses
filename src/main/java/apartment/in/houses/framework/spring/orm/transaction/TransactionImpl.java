package apartment.in.houses.framework.spring.orm.transaction;

import apartment.in.houses.framework.spring.orm.session.connection.exception.DatabaseException;
import apartment.in.houses.framework.spring.orm.transaction.interf.Transaction;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionImpl implements Transaction {

    private final Connection connection;
    private boolean active;

    public TransactionImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void begin() throws SQLException {
        connection.setAutoCommit(false);
        active = true;
    }

    @Override
    public void commit() throws SQLException {
        if (!active) {
            throw new DatabaseException("Transaction is not active");
        }
        connection.commit();
        connection.setAutoCommit(true);
        active = false;
    }

    @Override
    public void rollback() {
        if (!active) {
            throw new DatabaseException("Transaction is not active");
        }
        try {
            connection.rollback();
            connection.setAutoCommit(true);
            active = false;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to rollback transaction", e);
        }
    }

    @Override
    public boolean isActive() {
        return active;
    }
}
