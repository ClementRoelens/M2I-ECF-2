CREATE DATABASE ecf_2;

USE ecf_2;

CREATE TABLE person(
   id_person CHAR(36),
   firstname_person VARCHAR(50) NOT NULL,
   lastname_person VARCHAR(50) NOT NULL,
   birthday_person DATE NOT NULL,
   PRIMARY KEY(id_person)
);

CREATE TABLE departement(
   id_departement CHAR(36),
   name_departement VARCHAR(50) NOT NULL,
   PRIMARY KEY(id_departement)
);

CREATE TABLE teacher(
   id_person CHAR(36),
   rank_teacher CHAR(1) NOT NULL,
   is_referent_teacher BOOLEAN NOT NULL,
   is_departement_director BOOLEAN NOT NULL,
   departement_id CHAR(36) NOT NULL,
   PRIMARY KEY(id_person),
   FOREIGN KEY(id_person) REFERENCES person(id_person),
   FOREIGN KEY(departement_id) REFERENCES departement(id_departement)
);

CREATE TABLE diary(
   id_diary CHAR(36),
   PRIMARY KEY(id_diary)
);

CREATE TABLE school_class(
   id_school_class CHAR(36),
   name_school_class VARCHAR(50) NOT NULL,
   year_school_class INT NOT NULL,
   diary_id CHAR(36) NOT NULL,
   departement_id CHAR(36) NOT NULL,
   PRIMARY KEY(id_school_class),
   FOREIGN KEY(diary_id) REFERENCES diary(id_diary),
   FOREIGN KEY(departement_id) REFERENCES departement(id_departement)
);

CREATE TABLE student(
   id_person CHAR(36),
   email_student VARCHAR(50) NOT NULL,
   school_class_id CHAR(36) NOT NULL,
   PRIMARY KEY(id_person),
   FOREIGN KEY(id_person) REFERENCES person(id_person),
   FOREIGN KEY(school_class_id) REFERENCES school_class(id_school_class)
);

CREATE TABLE subject(
   id_subject CHAR(36),
   name_subject VARCHAR(100) NOT NULL,
   description_subject VARCHAR(255) NOT NULL,
   duration_subject INT NOT NULL,
   coefficient_subject INT NOT NULL,
   PRIMARY KEY(id_subject)
);

CREATE TABLE grade(
   id_grade CHAR(36),
   value_grade DECIMAL(3,1) NOT NULL,
   comment_grade VARCHAR(255) NOT NULL,
   subject_id CHAR(36) NOT NULL,
   student_id CHAR(36) NOT NULL,
   PRIMARY KEY(id_grade),
   FOREIGN KEY(subject_id) REFERENCES subject(id_subject),
   FOREIGN KEY(student_id) REFERENCES student(id_person)
);

CREATE TABLE teacher_subject(
   teacher_id CHAR(36),
   subject_id CHAR(36),
   PRIMARY KEY(teacher_id, subject_id),
   FOREIGN KEY(teacher_id) REFERENCES teacher(id_person),
   FOREIGN KEY(subject_id) REFERENCES subject(id_subject)
);

CREATE TABLE teacher_school_class(
   teacher_id CHAR(36),
   school_class_id CHAR(36),
   PRIMARY KEY(teacher_id, school_class_id),
   FOREIGN KEY(teacher_id) REFERENCES teacher(id_person),
   FOREIGN KEY(school_class_id) REFERENCES school_class(id_school_class)
);

CREATE TABLE diary_subject(
   subject_id CHAR(36),
   diary_id CHAR(36),
   day_diary_subject VARCHAR(50) NOT NULL,
   time_diary_subject TIME NOT NULL,
   PRIMARY KEY(subject_id, diary_id),
   FOREIGN KEY(subject_id) REFERENCES subject(id_subject),
   FOREIGN KEY(diary_id) REFERENCES diary(id_diary)
);
