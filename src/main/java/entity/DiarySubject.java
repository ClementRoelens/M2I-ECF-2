package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Entity
@IdClass(DiarySubjectPK.class)
@Table(name = "diary_subject")
public class DiarySubject implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;
    @Id
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
    @Column(name = "day_diary_subject")
    private String day;
    @Column(name = "time_diary_subject")
    private Time time;




    // Constructeurs


    public DiarySubject() {
    }

    public DiarySubject(String day, Time time, Diary diary, Subject subject) {
        this.day = day;
        this.time = time;
        this.diary = diary;
        this.subject = subject;
    }


    // Getters Setters


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Diary getDiary() {
        return diary;
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }


    // Autres méthodes


    @Override
    public String toString() {
        return String.format("%s le %s à %s\n", subject.getName(), day, time);
    }
}
