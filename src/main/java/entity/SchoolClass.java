package entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "school_class")
public class SchoolClass {
    @Id
    @Column(name = "id_school_class")
    private String id;
    @Column(name = "name_school_class")
    private String name;
    @Column(name = "year_school_class")
    private int year;
    @OneToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;
    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;
    @OneToMany(mappedBy = "schoolClass", cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();


    // Constructeurs


    public SchoolClass() {
    }

    public SchoolClass(String name, int year, Departement departement) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = year;
        this.departement = departement;
        this.diary = new Diary();
    }

    public SchoolClass(String id, String name, int year, Diary diary, Departement departement,List<Student> students ) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.diary = diary;
        this.departement = departement;
        this.students = students;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Diary getDiary() {
        return diary;
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }


// Autres méthodes


    @Override
    public String toString() {
        return String.format("%s - niveau %d\n", name, year);
    }
}
