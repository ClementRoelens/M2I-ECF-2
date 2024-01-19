package entity;

import exception.TooShortNameException;
import exception.WrongMailException;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
@PrimaryKeyJoinColumn(name = "id_person")
public class Student extends Person {
    @Column(name = "email_student")
    private String mail;
    @ManyToOne
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass;
    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
    private List<Grade> grades = new ArrayList<>();


    // Constructeurs


    public Student() {
    }

    public Student(String firstname, String lastname, Date birthday, String mail, SchoolClass schoolClass) throws TooShortNameException, WrongMailException {
        super(firstname, lastname, birthday);
        if (mail.matches(".*@gmail\\.com$")){
            this.mail = mail;
            this.schoolClass = schoolClass;
            return;
        }
        throw new WrongMailException();
    }

    public Student(
            String id,
            String firstname,
            String lastname,
            Date birthday,
            String mail,
            SchoolClass schoolClass,
            List<Grade> grades
    ) {
        super(id, firstname, lastname, birthday);
        this.mail = mail;
        this.schoolClass = schoolClass;
        this.grades = grades;
    }


    // Getters Setters


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }


// Autres méthodes


    @Override
    public String toString() {
        return String.format("%s %s, né le %s, est dans la classe de %s. Peut être contacté à %s\n",
                firstname, lastname, birthday, schoolClass.getName(), mail);
    }
}
