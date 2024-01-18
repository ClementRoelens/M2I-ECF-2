package dao.impl;

import dao.IDao;
import entity.Subject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class SubjectDaoImpl implements IDao<Subject> {
    private StandardServiceRegistry standardServiceRegistry;
    private SessionFactory sessionFactory;
    private static SubjectDaoImpl instance = null;
    private static Object lock = new Object();


    public static SubjectDaoImpl getInstance(){
        if (instance == null){
            synchronized (lock){
                instance = new SubjectDaoImpl();
            }
        }
        return instance;
    }


    private SubjectDaoImpl() {
        standardServiceRegistry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(standardServiceRegistry).buildMetadata().buildSessionFactory();
    }


    @Override
    public Subject create(Subject subject) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        try {
            session.save(subject);
            transaction.commit();
            return subject;
        } catch (Exception e) {
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
    public List<Subject> read() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        Query<Subject> query;
        List<Subject> subjects = new ArrayList<>();

        transaction.begin();

        try {
            query = session.createQuery("FROM Subject");
            subjects = query.list();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return subjects;
    }

    @Override
    public Subject read(String id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        try {
            return session.get(Subject.class, id);
        } catch (Exception e) {
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
        } catch (Exception e) {
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
