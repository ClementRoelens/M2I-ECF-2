package dao.impl;

import dao.IDao;
import entity.Departement;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class DepartementDaoImpl implements IDao<Departement> {
    private StandardServiceRegistry standardServiceRegistry;
    private SessionFactory sessionFactory;
    private static DepartementDaoImpl instance = null;
    private static Object lock = new Object();


    public static DepartementDaoImpl getInstance() {
        if (instance == null) {
            synchronized (lock) {
                instance = new DepartementDaoImpl();
            }
        }
        return instance;
    }


    private DepartementDaoImpl() {
        standardServiceRegistry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(standardServiceRegistry).buildMetadata().buildSessionFactory();
    }



    @Override
    public Departement create(Departement departement) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        try {
            session.save(departement);
            transaction.commit();
            return departement;
        }catch (Exception e) {
            System.out.println(e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Departement> read() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        Query<Departement> query;
        List<Departement> departements = new ArrayList<>();

        transaction.begin();

        try {
            query = session.createQuery("FROM Departement");
            departements = query.list();
            transaction.commit();
        }catch (Exception e) {
            System.out.println(e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return departements;
    }

    @Override
    public Departement read(String id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        try {
            return session.get(Departement.class,id);
        }catch (Exception e) {
            System.out.println(e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(String id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        try {
            session.delete(id);
            transaction.commit();
            return true;
        }catch (Exception e) {
            System.out.println(e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public void close() {
        sessionFactory.close();
    }
}
