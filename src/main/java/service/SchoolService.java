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
    private GradeDaoImpl gradeDao;
    private DiarySubjectDaoImpl diarySubjectDao;


    private static SchoolService instance = null;
    private static Object lock = new Object();


    private SchoolService() {
        studentDao = StudentDaoImpl.getInstance();
        subjectDao = SubjectDaoImpl.getInstance();
        teacherDao = TeacherDaoImpl.getInstance();
        diaryDao = DiaryDaoImpl.getInstance();
        schoolClassDao = SchoolClassDaoImpl.getInstance();
        departementDao = DepartementDaoImpl.getInstance();
        gradeDao = GradeDaoImpl.getInstance();
        diarySubjectDao = DiarySubjectDaoImpl.getInstance();
    }

    public static SchoolService getInstance() {
        if (instance == null) {
            synchronized (lock) {
                instance = new SchoolService();
            }
        }
        return instance;
    }


    public Teacher postTeacher(Teacher teacher) throws TooShortNameException, TooYoungException {
        return teacherDao.create(teacher);
    }

    public Student postStudent(Student student) throws TooShortNameException {
        return studentDao.create(student);
    }

    public List<Student> getAllStudents() {
        List<Student> students = studentDao.read();
        for (Student s : students) {
            s.setGrades(gradeDao.readAllGradesFromAStudent(s.getId()));
        }
        return students;
    }

    public Subject postSubject(Subject subject) {
        return subjectDao.create(subject);
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjects = subjectDao.read();
        for (Subject s : subjects) {
            s.setGrades(gradeDao.readAllGradesOfASubject(s.getId()));
        }
        return subjects;
    }

    public boolean addDiaryLine(DiarySubject diarySubject) {
        return (diarySubjectDao.create(diarySubject) != null);
    }

    public SchoolClass postSchoolClass(SchoolClass schoolClass) {
        return schoolClassDao.create(schoolClass);
    }

    public List<SchoolClass> getAllClasses() {
        List<SchoolClass> schoolClasses = schoolClassDao.read();
        for (SchoolClass s : schoolClasses) {
            s.setStudents(studentDao.readAllStudentsFromOneClass(s.getId()));
        }
        return schoolClasses;
    }

    public List<SchoolClass> getClassesFromALevel(int level) {
        List<SchoolClass> schoolClasses = schoolClassDao.readFromALevel(level);
        for (SchoolClass s : schoolClasses) {
            s.setStudents(studentDao.readAllStudentsFromOneClass(s.getId()));
        }
        return schoolClasses;
    }

    public Departement postDepartement(Departement departement) {
        return departementDao.create(departement);
    }

    public List<Departement> getAllDepartements() {
        List<Departement> departements;
        List<SchoolClass> schoolClasses;

        departements = departementDao.read();

        for (Departement d : departements) {
            schoolClasses = schoolClassDao.readFromADepartement(d.getId());

            for (SchoolClass s : schoolClasses) {
                s.setStudents(studentDao.readAllStudentsFromOneClass(s.getId()));
            }

            d.setSchoolClasses(schoolClasses);
        }
        return departements;
    }

    public Grade postGrade(Grade grade) {
        return gradeDao.create(grade);
    }


    public void closeAll() {
        teacherDao.close();
        studentDao.close();
        subjectDao.close();
        diaryDao.close();
        schoolClassDao.close();
        departementDao.close();
        gradeDao.close();
        diarySubjectDao.close();
    }
}
