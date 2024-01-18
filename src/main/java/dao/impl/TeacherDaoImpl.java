package dao.impl;

import dao.IDao;
import entity.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class TeacherDaoImpl implements IDao<Teacher> {
    private StandardServiceRegistry standardServiceRegistry;
    private SessionFactory sessionFactory;
    private static TeacherDaoImpl instance = null;
    private static Object lock = new Object();


    public static TeacherDaoImpl getInstance(){
        if (instance == null){
            synchronized (lock){
                instance = new TeacherDaoImpl();
            }
        }
        return instance;
    }


    private TeacherDaoImpl() {
        standardServiceRegistry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(standardServiceRegistry).buildMetadata().buildSessionFactory();
    }


    @Override
    public Teacher create(Teacher teacher) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        try {
            session.save(teacher);
            transaction.commit();
            return teacher;
        } catch (Exception e){
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
    public List<Teacher> read() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        Query<Teacher> query;
        List<Teacher> teachers = new ArrayList<>();

        transaction.begin();

        try {
            query = session.createQuery("FROM Teacher");
            teachers = query.list();
            transaction.commit();
        } catch (Exception e){
            System.out.println(e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return teachers;
    }

    @Override
    public Teacher read(String id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        try {
            return session.get(Teacher.class,id);
        } catch (Exception e){
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
        } catch (Exception e){
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
