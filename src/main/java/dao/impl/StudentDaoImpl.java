package dao.impl;

import dao.IDao;
import entity.Student;
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

public class StudentDaoImpl implements IDao<Student> {
    private StandardServiceRegistry standardServiceRegistry;
    private SessionFactory sessionFactory;
    private static StudentDaoImpl instance = null;
    private static Object lock = new Object();


    public static StudentDaoImpl getInstance(){
        if (instance == null){
            synchronized (lock){
                instance = new StudentDaoImpl();
            }
        }
        return instance;
    }

    private StudentDaoImpl() {
        standardServiceRegistry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(standardServiceRegistry).buildMetadata().buildSessionFactory();
    }



    @Override
    public Student create(Student student) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        try {
            session.save(student);
            transaction.commit();
            return student;
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
    public List<Student> read() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        Query<Student> query;
        List<Student> students = new ArrayList<>();

        transaction.begin();

        try {
            query = session.createQuery("FROM Student");
            students = query.list();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return students;
    }

    @Override
    public Student read(String id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        try {
            return session.get(Student.class,id);
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
