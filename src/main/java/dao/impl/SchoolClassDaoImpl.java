package dao.impl;

import dao.IDao;
import entity.SchoolClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class SchoolClassDaoImpl implements IDao<SchoolClass> {
    private StandardServiceRegistry standardServiceRegistry;
    private SessionFactory sessionFactory;
    private static SchoolClassDaoImpl instance = null;
    private static Object lock = new Object();


    public static SchoolClassDaoImpl getInstance() {
        if (instance == null) {
            synchronized (lock) {
                instance = new SchoolClassDaoImpl();
            }
        }
        return instance;
    }


    private SchoolClassDaoImpl() {
        standardServiceRegistry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(standardServiceRegistry).buildMetadata().buildSessionFactory();
    }



    @Override
    public SchoolClass create(SchoolClass schoolClass) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        try {
            session.save(schoolClass);
            transaction.commit();
            return schoolClass;
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
    public List<SchoolClass> read() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        Query<SchoolClass> query;
        List<SchoolClass> schoolClasses = new ArrayList<>();

        transaction.begin();

        try {
            query = session.createQuery("FROM SchoolClass");
            schoolClasses = query.list();
            transaction.commit();
        }catch (Exception e) {
            System.out.println(e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return schoolClasses;
    }

    @Override
    public SchoolClass read(String id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        try {
            return session.get(SchoolClass.class,id);
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
