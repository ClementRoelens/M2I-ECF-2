package entity;

import exception.TooShortNameException;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "student")
@PrimaryKeyJoinColumn(name = "id_student")
public class Student extends Person {
    @Column(name = "email_student")
    private String mail;
    @ManyToOne
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass;



    public Student() {
    }

    public Student(String firstname, String lastname, Date birthday, String mail, SchoolClass schoolClass) throws TooShortNameException {
        super(firstname, lastname, birthday);
        this.mail = mail;
        this.schoolClass = schoolClass;
    }

    public Student(String id, String firstname, String lastname, Date birthday, String mail, SchoolClass schoolClass) {
        super(id, firstname, lastname, birthday);
        this.mail = mail;
        this.schoolClass = schoolClass;
    }



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



    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthday=" + birthday +
                ", mail='" + mail + '\'' +
                ", schoolClass=" + schoolClass +
                '}';
    }
}
