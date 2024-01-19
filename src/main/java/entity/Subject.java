package entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @Column(name = "id_subject")
    private String id;
    @Column(name = "name_subject")
    private String name;
    @Column(name = "description_subject")
    private String description;
    @Column(name = "duration_subject")
    private int duration;
    @Column(name = "coefficient_subject")
    private int coefficient;
    @OneToMany(mappedBy = "subject", cascade = CascadeType.REMOVE)
    private List<Grade> grades;
//    @ManyToMany(mappedBy = "subjects", cascade = CascadeType.REMOVE)
//    private List<Diary> diaries;
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<DiarySubject> diarySubjects = new ArrayList<>();


    // Constructeurs


    public Subject() {
    }

    public Subject(String name, String description, int duration, int coefficient) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.coefficient = coefficient;
    }

    public Subject(String id, String name, String description, int duration, int coefficient, List<DiarySubject> diarySubjects, List<Grade> grades) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.coefficient = coefficient;
        this.diarySubjects = diarySubjects;
        this.grades = grades;
    }


    // Getters Setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

//    public List<Diary> getDiaries() {
//        return diaries;
//    }
//
//    public void setDiaries(List<Diary> diaries) {
//        this.diaries = diaries;
//    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public List<DiarySubject> getDiarySubjects() {
        return diarySubjects;
    }

    public void setDiarySubjects(List<DiarySubject> diarySubjects) {
        this.diarySubjects = diarySubjects;
    }



    // Autres méthodes


    @Override
    public String toString() {
        return String.format("%s - coefficient %d - cours de %d minutes\n%s\n",
                name, coefficient, duration, description);
    }
}
