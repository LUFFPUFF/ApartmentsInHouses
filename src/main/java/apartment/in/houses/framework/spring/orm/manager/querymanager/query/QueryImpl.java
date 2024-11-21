package apartment.in.houses.framework.spring.orm.manager.querymanager.query;

import apartment.in.houses.framework.spring.orm.manager.entitymanager.entitymanager.EntityManager;
import apartment.in.houses.framework.spring.orm.manager.entitymanager.entitymanager.EntityManagerImpl;
import apartment.in.houses.framework.spring.orm.manager.entitymanager.util.ResultSetUtil;
import apartment.in.houses.framework.spring.orm.session.interf.Session;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryImpl<T> implements Query<T> {
    private final String sql;
    private final Session session;
    private final Class<T> entityClass;
    private final List<Object> parameters = new ArrayList<>();

    public QueryImpl(String sql, Session session, Class<T> entityClass) {
        this.sql = sql;
        this.session = session;
        this.entityClass = entityClass;
    }
    @Override
    public List<T> getResultList() throws SQLException {
        List<T> results = new ArrayList<>();

        PreparedStatement statement = session.getConnection().prepareStatement(sql);
        for (int i = 0; i < parameters.size(); i++) {
            statement.setObject(i + 1, parameters.get(i));
        }

        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                T entity = entityClass.getDeclaredConstructor().newInstance();
                EntityManager entityManager = new EntityManagerImpl(session);
                ResultSetUtil.populateEntityFields(entity, resultSet, entityClass, entityManager);
                results.add(entity);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new SQLException("Error instantiating entity: " + e.getMessage());
        } catch (NoSuchFieldException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    @Override
    public Query<T> setParameter(int position, Object value) {
        parameters.add(position - 1, value);
        return this;
    }
}
