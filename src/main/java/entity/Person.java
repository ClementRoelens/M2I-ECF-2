package entity;

import exception.TooShortNameException;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person {
    @Id
    @Column(name = "id_person", length = 36)
    protected String id;
    @Column(name = "firstname_person")
    protected String firstname;
    @Column(name = "lastname_person")
    protected String lastname;
    @Column(name = "birthday_person")
    protected Date birthday;


    // Constructeurs


    public Person() {
    }

    public Person(String firstname, String lastname, Date birthday) throws TooShortNameException {
        if (firstname.length() < 3 || lastname.length() < 3) {
            throw new TooShortNameException();
        }
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.id = UUID.randomUUID().toString();
        System.out.println("");
    }


    // Getters Setters


    public Person(String id, String firstname, String lastname, Date birthday) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
