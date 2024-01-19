package entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "diary")
public class Diary {
    @Id
    @Column(name = "id_diary")
    private String id;
    @OneToOne(mappedBy = "diary")
    private SchoolClass schoolClass;
//    @ManyToMany
//    @JoinTable(
//            name = "diary_subject",
//            joinColumns = @JoinColumn(name = "diary_id"),
//            inverseJoinColumns = @JoinColumn(name = "subject_id"))
//    private List<Subject> subjects = new ArrayList<>();
    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DiarySubject> diarySubjects = new ArrayList<>();


    // Constructeurs


    public Diary() {
        this.id = UUID.randomUUID().toString();
    }

    public Diary(SchoolClass schoolClass, List<DiarySubject> diarySubjects) {
        this();
        this.schoolClass = schoolClass;
        this.diarySubjects = diarySubjects;
    }

    public Diary(String id, SchoolClass schoolClass, List<DiarySubject> diarySubjects) {
        this(schoolClass,diarySubjects);
        this.id = id;
    }


    // Getters Setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

//    public List<Subject> getSubjects() {
//        return subjects;
//    }
//
//    public void setSubjects(List<Subject> subjects) {
//        this.subjects = subjects;
//    }

    public List<DiarySubject> getDiarySubjects() {
        return diarySubjects;
    }

    public void setDiarySubjects(List<DiarySubject> diarySubjects) {
        this.diarySubjects = diarySubjects;
    }


    // Autres méthodes


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Emploi du temps de la classe de " + schoolClass.getName());
        stringBuilder.append("\n\n");
        for (DiarySubject ds : diarySubjects){
            stringBuilder.append(ds);
        }

        return stringBuilder.toString();
    }
}
