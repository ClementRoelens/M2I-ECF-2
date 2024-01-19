package entity;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class DiarySubjectPK implements Serializable {
    private static final long serialVersionUID = 1L;
    private Diary diary;
    private Subject subject;


    public DiarySubjectPK() {
    }

    public DiarySubjectPK(Diary diary, Subject subject) {
        this.diary = diary;
        this.subject = subject;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiarySubjectPK that = (DiarySubjectPK) o;
        return Objects.equals(diary, that.diary) && Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diary, subject);
    }
}
