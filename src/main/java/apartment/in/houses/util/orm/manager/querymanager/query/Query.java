package apartment.in.houses.util.orm.manager.querymanager.query;

import java.sql.SQLException;
import java.util.List;

public interface Query<T> {
    List<T> getResultList() throws SQLException;
    Query<T> setParameter(int position, Object value);
}
