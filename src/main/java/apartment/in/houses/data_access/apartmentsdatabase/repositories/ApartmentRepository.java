package apartment.in.houses.data_access.apartmentsdatabase.repositories;

import apartment.in.houses.data_access.apartmentsdatabase.dao.ApartmentDAO;
import apartment.in.houses.data_access.apartmentsdatabase.entitie.Apartment;

import apartment.in.houses.util.DI.annotation.Component;
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

@Component
public class ApartmentRepository implements ApartmentDAO {

    public ApartmentRepository() {
    }

    private static final SessionFactory sessionFactory = ConnectionManagerImpl.getSessionFactory();

    @Override
    public List<Apartment> getAllApartments() {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();

            CriteriaBuilder builder = new CriteriaBuilderImpl();
            CriteriaQuery<Apartment> query = builder.createQuery(Apartment.class);
            Root<Apartment> root = query.from(Apartment.class);

            query.select(root);

            transaction.commit();

            return session.createQuery(query).getResultList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public Apartment getApartment(int id) {
        Apartment apartment;

        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            apartment = session.get(Apartment.class, id);
            transaction.commit();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return apartment;
    }

    @Override
    public boolean insert(Apartment apartment) {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();

            session.save(apartment);

            transaction.commit();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Apartment apartment) {
        return false;
    }

    @Override
    public boolean delete(Apartment apartment) {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();

            session.delete(apartment);

            transaction.commit();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
