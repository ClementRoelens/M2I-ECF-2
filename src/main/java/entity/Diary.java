package entity;

import javax.persistence.*;
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
    @ManyToMany
    @JoinTable(
            name = "diary_subject",
            joinColumns = @JoinColumn(name = "diary_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<Subject> subjects;


    // Constructeurs


    public Diary() {
    }

    public Diary(SchoolClass schoolClass, List<Subject> subjects) {
        this.id = UUID.randomUUID().toString();
        this.schoolClass = schoolClass;
        this.subjects = subjects;
    }

    public Diary(String id, SchoolClass schoolClass, List<Subject> subjects) {
        this.id = id;
        this.schoolClass = schoolClass;
        this.subjects = subjects;
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

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }


    // Autres méthodes


    @Override
    public String toString() {
        return "Diary{" +
                "id=" + id +
                ", schoolClass=" + schoolClass +
                ", subjects=" + subjects +
                '}';
    }
}
