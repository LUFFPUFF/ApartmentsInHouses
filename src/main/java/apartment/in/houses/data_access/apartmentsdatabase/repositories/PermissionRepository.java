package apartment.in.houses.data_access.apartmentsdatabase.repositories;

import apartment.in.houses.data_access.apartmentsdatabase.dao.PermissionDAO;
import apartment.in.houses.data_access.apartmentsdatabase.entitie.Apartment;
import apartment.in.houses.data_access.apartmentsdatabase.entitie.Permission;
import apartment.in.houses.framework.spring.DI.annotation.Component;
import apartment.in.houses.framework.spring.orm.manager.querymanager.criteriabuilder.CriteriaBuilder;
import apartment.in.houses.framework.spring.orm.manager.querymanager.criteriabuilder.CriteriaBuilderImpl;
import apartment.in.houses.framework.spring.orm.manager.querymanager.criteriaquery.CriteriaQuery;
import apartment.in.houses.framework.spring.orm.manager.querymanager.root.Root;
import apartment.in.houses.framework.spring.orm.session.connection.ConnectionManagerImpl;
import apartment.in.houses.framework.spring.orm.session.interf.Session;
import apartment.in.houses.framework.spring.orm.session.interf.SessionFactory;
import apartment.in.houses.framework.spring.orm.transaction.interf.Transaction;

import java.sql.SQLException;
import java.util.List;

@Component
public class PermissionRepository implements PermissionDAO {

    private static final SessionFactory sessionFactory = ConnectionManagerImpl.getSessionFactory();

    @Override
    public List<Permission> getPermissions() {
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        CriteriaBuilder builder = new CriteriaBuilderImpl();
        CriteriaQuery<Permission> query = builder.createQuery(Permission.class);
        Root<Permission> root = query.from(Permission.class);

        query.select(root);

        try {
            transaction.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            return session.createQuery(query).getResultList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Permission getPermission(int id) {
        Permission permission;

        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            permission = session.get(Permission.class, id);
            transaction.commit();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return permission;
    }

    @Override
    public boolean insert(Permission permission) {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();

            session.save(permission);

            transaction.commit();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Permission permission) {
        return false;
    }

    @Override
    public boolean delete(Permission permission) {
        return false;
    }
}
