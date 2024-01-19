package entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "grade")
public class Grade {
    @Id
    @Column(name = "id_grade")
    private String id;
    @Column(name = "value_grade")
    private double value;
    @Column(name = "comment_grade")
    private String comment;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;


    // Constructeurs


    public Grade() {
    }

    public Grade(double value, String comment, Subject subject, Student student) {
        this.id = UUID.randomUUID().toString();
        this.value = value;
        this.comment = comment;
        this.subject = subject;
        this.student = student;
    }

    public Grade(String id, double value, String comment, Subject subject, Student student) {
        this.id = id;
        this.value = value;
        this.comment = comment;
        this.subject = subject;
        this.student = student;
    }


    // Getters Setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }



    // Autres méthodes


    @Override
    public String toString() {
        return String.format("%s %s a obtenu %.2f en %s\n%s\n",
                student.firstname, student.getLastname(), value, subject.getName(), comment);
    }
}
