package entity;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name ="grade")
public class Grade {
    @Id
    @Column(name = "id_grade")
    private String id;
    @Column(name = "value_grade")
    private float value;
    @Column(name = "comment_grade")
    private String comment;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;





    public Grade() {
    }

    public Grade(float value, String comment, Subject subject) {
        this.id = UUID.randomUUID().toString();
        this.value = value;
        this.comment = comment;
        this.subject = subject;
    }

    public Grade(String id, float value, String comment, Subject subject) {
        this.id = id;
        this.value = value;
        this.comment = comment;
        this.subject = subject;
    }





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
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





    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", value=" + value +
                ", comment='" + comment + '\'' +
                ", subject=" + subject.getName() +
                '}';
    }
}
