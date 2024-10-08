package apartment.in.houses.util.orm;

import apartment.in.houses.data_access.apartmentsdatabase.entities.Apartment;
import apartment.in.houses.util.orm.manager.querymanager.criteriabuilder.CriteriaBuilder;
import apartment.in.houses.util.orm.manager.querymanager.criteriabuilder.CriteriaBuilderImpl;
import apartment.in.houses.util.orm.manager.querymanager.criteriaquery.CriteriaQuery;
import apartment.in.houses.util.orm.manager.querymanager.root.Root;
import apartment.in.houses.util.orm.session.connection.ConnectionManagerFactory;
import apartment.in.houses.util.orm.session.interf.Session;
import apartment.in.houses.util.orm.session.interf.SessionFactory;
import apartment.in.houses.util.orm.transaction.interf.Transaction;

import java.sql.SQLException;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        testCriteriaQuery();

    }

    public static void testCriteriaQuery() {

        SessionFactory sessionFactory = ConnectionManagerFactory.createSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            Transaction transaction = session.beginTransaction();

            CriteriaBuilder builder = new CriteriaBuilderImpl();
            CriteriaQuery<Apartment> query = builder.createQuery(Apartment.class);
            Root<Apartment> root = query.from(Apartment.class);

            query.select(root)
                    .where(builder.greaterThan(root.get("floor"), 4));

            String sql = query.build();

            System.out.println(sql);

            List<Apartment> apartments = session.createQuery(sql, Apartment.class).getResultList();

            for (Apartment apartment : apartments) {
                System.out.println(apartment);
            }

            transaction.commit();
            session.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
