package apartment.in.houses.util.orm;

import apartment.in.houses.data_access.apartmentsdatabase.entitie.*;
import apartment.in.houses.util.orm.manager.querymanager.criteriabuilder.CriteriaBuilder;
import apartment.in.houses.util.orm.manager.querymanager.criteriabuilder.CriteriaBuilderImpl;
import apartment.in.houses.util.orm.manager.querymanager.criteriaquery.CriteriaQuery;
import apartment.in.houses.util.orm.manager.querymanager.root.Root;
import apartment.in.houses.util.orm.session.connection.ConnectionManagerImpl;
import apartment.in.houses.util.orm.session.interf.Session;
import apartment.in.houses.util.orm.session.interf.SessionFactory;
import apartment.in.houses.util.orm.transaction.interf.Transaction;

import java.sql.SQLException;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        testEmbeddable();

    }

    public static void testCriteriaQuery() {

        SessionFactory sessionFactory = ConnectionManagerImpl.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            Transaction transaction = session.beginTransaction();

            CriteriaBuilder builder = new CriteriaBuilderImpl();
            CriteriaQuery<Apartment> query = builder.createQuery(Apartment.class);
            Root<Apartment> root = query.from(Apartment.class);

            query.select(root)
                    .where(builder.greaterThan(root.get("floor"), 4));

            List<Apartment> apartments = session.createQuery(query).getResultList();

            for (Apartment apartment : apartments) {
                System.out.println(apartment);
            }

            transaction.commit();
            session.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private static void testEmbeddable() {
        Role role = new Role();
        role.setId(1);
        role.setName("SUPER");

        Permission permission = new Permission();
        permission.setId(1);
        permission.setName("BOG");

        RolePermissionId id = new RolePermissionId();
        id.setPermission_id(permission.getId());
        id.setRole_id(role.getId());

        RolePermission rolePermission = new RolePermission();
        rolePermission.setId(id);
        rolePermission.setRole(role);
        rolePermission.setPermission(permission);

        SessionFactory sessionFactory = ConnectionManagerImpl.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.save(rolePermission);

    }
}
