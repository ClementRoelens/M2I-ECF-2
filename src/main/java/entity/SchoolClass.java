package entity;

import javax.persistence.*;
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


    // Constructeurs


    public SchoolClass() {
    }

    public SchoolClass(String name, int year, Departement departement) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = year;
        this.departement = departement;
    }

    public SchoolClass(String id, String name, int year, Diary diary, Departement departement) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.diary = diary;
        this.departement = departement;
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


    // Autres méthodes


    @Override
    public String toString() {
        return "SchoolClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                '}';
    }
}
