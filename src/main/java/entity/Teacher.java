package entity;

import exception.TooShortNameException;
import exception.TooYoungException;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "teacher")
@PrimaryKeyJoinColumn(name = "id_teacher")
public class Teacher extends Person {
    @Column(name = "rank_teacher")
    private char rank;
    @Column(name = "is_referent_teacher")
    private boolean isReferentTeacher;
    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;
    @Column(name = "is_departement_director")
    private boolean isDepartementDirector;


    // Constructeurs


    public Teacher() {
    }

    public Teacher(
            String firstname,
            String lastname,
            Date birthday,
            char rank,
            boolean isReferentTeacher,
            Departement departement,
            boolean isDepartementDirector
    ) throws TooShortNameException, TooYoungException {
        super(firstname, lastname, birthday);
        LocalDate today = LocalDate.now();
        if (today.getYear() - birthday.toLocalDate().getYear() >= 18) {
            if (
                    (today.getYear() - birthday.toLocalDate().getYear() > 18
                    ||
                    today.getMonth().compareTo(birthday.toLocalDate().getMonth()) >= 0)
            ) {
                if (
                        (today.getMonth().compareTo(birthday.toLocalDate().getMonth()) > 0)
                                ||
                                (today.getDayOfMonth() >= birthday.toLocalDate().getDayOfMonth())
                ) {
                    this.rank = rank;
                    this.isReferentTeacher = isReferentTeacher;
                    this.departement = departement;
                    this.isDepartementDirector = isDepartementDirector;
                    return;
                }
            }
        }
        throw new TooYoungException();
    }

    public Teacher(
            String id,
            String firstname,
            String lastname,
            Date birthday,
            char rank,
            boolean isReferentTeacher,
            Departement departement,
            boolean isDepartementDirector
    ) {
        super(id, firstname, lastname, birthday);
        this.rank = rank;
        this.isReferentTeacher = isReferentTeacher;
        this.departement = departement;
        this.isDepartementDirector = isDepartementDirector;
    }


    // Getters Setters


    public char getRank() {
        return rank;
    }

    public void setRank(char rank) {
        this.rank = rank;
    }

    public boolean isDepartementDirector() {
        return isDepartementDirector;
    }

    public void setDepartementDirector(boolean departementDirector) {
        isDepartementDirector = departementDirector;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public boolean isReferentTeacher() {
        return isReferentTeacher;
    }

    public void setReferentTeacher(boolean referentTeacher) {
        isReferentTeacher = referentTeacher;
    }


    // Autres méthodes


    @Override
    public String toString() {
        return "Teacher{" +
                ", id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthday=" + birthday +
                "rank=" + rank +
                ", isReferentTeacher=" + isReferentTeacher +
                ", departement=" + departement.getName() +
                ", isDepartementDirector=" + isDepartementDirector +
                '}';
    }
}
