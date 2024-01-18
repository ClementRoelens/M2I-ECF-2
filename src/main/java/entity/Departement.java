package entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name ="departement")
public class Departement {
    @Id
    @Column(name = "id_departement")
    private String id;
    @Column(name = "name_departement")
    private String name;
    @OneToMany(mappedBy = "departement")
    private List<Teacher> teachers;
    @OneToMany(mappedBy = "departement")
    private List<SchoolClass> schoolClasses;


    // Constructeurs


    public Departement() {
    }

    public Departement(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.teachers = new ArrayList<>();
        this.schoolClasses = new ArrayList<>();
    }

    public Departement(String id, String name, List<Teacher> teachers, List<SchoolClass> schoolClasses) {
        this.id = id;
        this.name = name;
        this.teachers = teachers;
        this.schoolClasses = schoolClasses;
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

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<SchoolClass> getSchoolClasses() {
        return schoolClasses;
    }

    public void setSchoolClasses(List<SchoolClass> schoolClasses) {
        this.schoolClasses = schoolClasses;
    }


    // Autres méthodes


    @Override
    public String toString() {
        return "Departement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teachers=" + teachers +
                '}';
    }
}
