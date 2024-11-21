package apartment.in.houses.framework.spring.orm.session.interf;

import java.sql.Connection;

public interface SessionFactory {

    Session openSession();
    void releaseConnection(Connection connection);
}
