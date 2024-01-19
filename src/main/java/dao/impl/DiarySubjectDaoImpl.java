package dao.impl;

import dao.IDao;
import entity.Diary;
import entity.DiarySubject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class DiarySubjectDaoImpl implements IDao<DiarySubject> {
    private StandardServiceRegistry standardServiceRegistry;
    private SessionFactory sessionFactory;
    private static DiarySubjectDaoImpl instance = null;
    private static Object lock = new Object();


    public static DiarySubjectDaoImpl getInstance() {
        if (instance == null) {
            synchronized (lock) {
                instance = new DiarySubjectDaoImpl();
            }
        }
        return instance;
    }


    private DiarySubjectDaoImpl() {
        standardServiceRegistry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(standardServiceRegistry).buildMetadata().buildSessionFactory();
    }




    @Override
    public DiarySubject create(DiarySubject diarySubject) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        try {
            session.save(diarySubject);
            transaction.commit();
            return diarySubject;

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
    public List<DiarySubject> read() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        Query<DiarySubject> query;
        List<DiarySubject> diarySubjects = new ArrayList<>();

        transaction.begin();

        try {
            query = session.createQuery("FROM DiarySubject");
            diarySubjects = query.list();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return diarySubjects;
    }

    @Override
    public DiarySubject read(String id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        try {
            return session.get(DiarySubject.class, id);
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
