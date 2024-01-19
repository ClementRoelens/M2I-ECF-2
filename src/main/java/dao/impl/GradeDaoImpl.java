package dao.impl;

import dao.IDao;
import entity.Grade;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class GradeDaoImpl implements IDao<Grade> {
    private StandardServiceRegistry standardServiceRegistry;
    private SessionFactory sessionFactory;
    private static GradeDaoImpl instance = null;
    private static Object lock = new Object();


    public static GradeDaoImpl getInstance() {
        if (instance == null) {
            synchronized (lock) {
                instance = new GradeDaoImpl();
            }
        }
        return instance;
    }


    private GradeDaoImpl() {
        standardServiceRegistry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(standardServiceRegistry).buildMetadata().buildSessionFactory();
    }




    @Override
    public Grade create(Grade grade) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        try {
            session.save(grade);
//            grade.getSubject().getGrades().add(grade);
//            grade.getStudent().getGrades().add(grade);
//            session.merge(grade.getSubject());
//            session.merge(grade.getStudent());
            transaction.commit();
            return grade;
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
    public List<Grade> read() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        Query<Grade> query;
        List<Grade> grades = new ArrayList<>();

        transaction.begin();

        try {
            query = session.createQuery("FROM Grade");
            grades = query.list();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return grades;
    }

    public List<Grade> readAllGradesFromAStudent(String studentId){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        Query<Grade> query;
        List<Grade> grades = new ArrayList<>();

        transaction.begin();

        try {
            query = session.createQuery("FROM Grade WHERE student_id = :studentId");
            query.setParameter("studentId", studentId);
            grades = query.list();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return grades;
    }

    public List<Grade> readAllGradesOfASubject(String subjectId){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        Query<Grade> query;
        List<Grade> grades = new ArrayList<>();

        transaction.begin();

        try {
            query = session.createQuery("FROM Grade WHERE student_id = :subjectId");
            query.setParameter("subjectId", subjectId);
            grades = query.list();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return grades;
    }

    @Override
    public Grade read(String id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        try {
            return session.get(Grade.class,id);
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
            session.delete(id);;
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
