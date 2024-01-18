package service;

import dao.impl.*;
import entity.*;
import exception.TooShortNameException;
import exception.TooYoungException;

import java.util.List;

public class SchoolService {
    private StudentDaoImpl studentDao;
    private SubjectDaoImpl subjectDao;
    private TeacherDaoImpl teacherDao;
    private DiaryDaoImpl diaryDao;
    private SchoolClassDaoImpl schoolClassDao;
    private DepartementDaoImpl departementDao;

    private static SchoolService instance = null;
    private static Object lock = new Object();



    private SchoolService(){
        studentDao = StudentDaoImpl.getInstance();
        subjectDao = SubjectDaoImpl.getInstance();
        teacherDao = TeacherDaoImpl.getInstance();
        diaryDao = DiaryDaoImpl.getInstance();
        schoolClassDao = SchoolClassDaoImpl.getInstance();
        departementDao = DepartementDaoImpl.getInstance();
    }

    public static SchoolService getInstance(){
        if (instance == null){
            synchronized (lock){
                instance = new SchoolService();
            }
        }
        return instance;
    }



    public Teacher postTeacher(Teacher teacher) throws TooShortNameException, TooYoungException {
        return teacherDao.create(teacher);
    }
    public Student postStudent(Student student) throws TooShortNameException{
        return studentDao.create(student);
    }
    public Subject postSubject(Subject subject){
        return subjectDao.create(subject);
    }
    public Diary postDiary(Diary diary){
        return diaryDao.create(diary);
    }
    public SchoolClass postSchoolClass(SchoolClass schoolClass){
        return schoolClassDao.create(schoolClass);
    }
    public List<SchoolClass> getAllClasses(){
        return schoolClassDao.read();
    }
    public Departement postDepartement(Departement departement){
        return departementDao.create(departement);
    }
    public List<Departement> getAllDepartements(){
        return departementDao.read();
    }

    public void closeAll(){
        teacherDao.close();
        studentDao.close();
        subjectDao.close();
        diaryDao.close();
        schoolClassDao.close();
        departementDao.close();
    }
}
