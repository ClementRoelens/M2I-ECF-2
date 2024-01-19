package controller;

import entity.*;
import exception.TooShortNameException;
import exception.TooYoungException;
import exception.WrongMailException;
import service.SchoolService;

import java.sql.Date;
import java.sql.Time;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class HCI {
    private SchoolService schoolService = SchoolService.getInstance();
    private Scanner scanner = new Scanner(System.in);


    public void menu() {
        int choice;

        choice = scanInt("\nBonjour, que voulez-vous faire ?\n" +
                "1 - Crée un département\n" +
                "2 - Créer un professeur\n" +
                "3 - Créer une classe\n" +
                "4 - Créer un élève\n" +
                "5 - Créer une matière\n" +
                "6 - Créer un emploi du temps\n" +
                "7 - Noter un élève\n" +
                "8 - Afficher toutes les classes\n" +
                "9 - Afficher le nombre de matières d'un élève\n" +
                "10 - Afficher la liste des notes d'un élève\n" +
                "11 - Afficher la moyenne d'un élève\n" +
                "12 - Afficher le nombre d'élèves d'un département\n" +
                "13 - Afficher les élèves d'un niveau\n" +
                "14 - Afficher l'emploi du temps d'une classe\n" +
                "0 - Quitter");

        switch (choice) {
            case 1 -> createDepartement();
            case 2 -> createTeacher();
            case 3 -> createSchoolClass();
            case 4 -> createStudent();
            case 5 -> createSubject();
            case 6 -> createDiary();
            case 7 -> gradeStudent();
            case 8 -> showClasses();
            case 9 -> showSubjectNumberOfAStudent();
            case 10 -> showStudentGrades();
            case 11 -> showStudentAvgGrade();
            case 12 -> showDepartementStudentNumber();
            case 13 -> showAllStudentsFromOneLevel();
            case 14 -> showDiary();
            case 0 -> leave();
        }

        if (choice != 0) {
            menu();
        }
    }


    private void createDepartement() {
        String name;
        Departement departement;

        name = scanString("Entrez le nom du département");

        departement = new Departement(name);
        departement = schoolService.postDepartement(departement);

        if (departement != null) {
            System.out.println("Département correctement créé, son id est " + departement.getId());
        } else {
            System.out.println("Département non-créé");
        }
    }

    private void createTeacher() {
        String firstname;
        String lastname;
        Date birthdate;
        char rank = ' ';
        boolean isReferent;
        int isReferentInt = -1;
        List<Departement> departements = schoolService.getAllDepartements();
        int departementChoice;
        Departement departement = null;
        int isDirectorInt = -1;
        boolean isDirector;
        Teacher teacher;

        firstname = scanString("Entrez le prénom du professeur (au moins 3 lettres)");
        lastname = scanString("Entrez le nom du professeur (au moins 3 lettres)");
        birthdate = scanDate("Entrez la date de naissance du professeur au format 2023-31-12 (il doit au moins avoir 18 ans)");
        while (rank == ' ') {
            System.out.println("Entrez le grade du professeur (A, B ou C)");
            String rawValue = scanner.nextLine();
            if (rawValue.equals("A") || rawValue.equals("B") || rawValue.equals("C")) {
                rank = rawValue.charAt(0);
            }
        }

        while (isReferentInt != 0 && isReferentInt != 1) {
            isReferentInt = scanInt("Entrez 1 si le professeur est professeur principal, sinon entrez 0");
        }
        isReferent = (isReferentInt == 1);


        while (departement == null) {
            for (int i = 0; i < departements.size(); i++) {
                System.out.printf("%d - %s\n", i, departements.get(i).getName());
            }

            departementChoice = scanInt("Entrez le numéro du département du professeur");
            try {
                departement = departements.get(departementChoice);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Entrez un numéro correspondant à un des départements disponibles");
            }
        }

        while (isDirectorInt != 0 && isDirectorInt != 1) {
            isDirectorInt = scanInt("Entrez 1 si le professeur est le directeur de son département, sinon entrez 0");
        }
        isDirector = (isDirectorInt == 1);

        try {
            teacher = new Teacher(firstname, lastname, birthdate, rank, isReferent, departement, isDirector);
            teacher = schoolService.postTeacher(teacher);
            if (teacher != null) {
                System.out.println("Professeur correctement créé, son id est " + teacher.getId());
            } else {
                System.out.println("Professeur non-créé");
            }
        } catch (TooShortNameException e) {
            System.out.println("Votre nom/prénom doit faire au moins 3 lettres. Revenez quand vous aurez changer de nom/prénom");
        } catch (TooYoungException e) {
            System.out.println("Revenez quand vous aurez 18 ans");
        }
    }

    private void createSchoolClass() {
        String name;
        int year;
        List<Departement> departements = schoolService.getAllDepartements();
        int departementChoice;
        Departement departement = null;
        SchoolClass schoolClass;

        name = scanString("Entrez le nom de la classe");
        year = scanInt("Entrez le niveau de la classe");
        while (departement == null) {
            for (int i = 0; i < departements.size(); i++) {
                System.out.printf("%d - %s\n", i, departements.get(i).getName());
            }

            departementChoice = scanInt("Entrez le numéro du département de la classe");
            try {
                departement = departements.get(departementChoice);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Entrez un numéro correspondant à un des départements disponibles");
            }
        }

        schoolClass = new SchoolClass(name, year, departement);
        schoolClass = schoolService.postSchoolClass(schoolClass);

        if (schoolClass != null) {
            System.out.println("Classe correctement créé, son id est " + schoolClass.getId());
        } else {
            System.out.println("Classe non-créée");
        }
    }

    private void createStudent() {
        String firstname;
        String lastname;
        Date birthdate;
        String email;
        List<SchoolClass> schoolClasses = schoolService.getAllClasses();
        int classChoice;
        SchoolClass schoolClass = null;
        Student student;

        firstname = scanString("Entrez le prénom de l'élève (au moins 3 lettres)");
        lastname = scanString("Entrez le nom de l'élève (au moins 3 lettres)");
        birthdate = scanDate("Entrez la date de naissance de l'élève au format 2023-31-12");
        email = scanString("Entrez l'adresse e-mail de l'élève (doit finir par @gmail.com)");

        while (schoolClass == null) {
            for (int i = 0; i < schoolClasses.size(); i++) {
                System.out.printf("%d - %s\n", i, schoolClasses.get(i).getName());
            }

            classChoice = scanInt("Entrez le numéro de la classe de l'élève");
            try {
                schoolClass = schoolClasses.get(classChoice);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Entrez un numéro correspondant à une des classes disponibles");
            }
        }
        try {
            student = new Student(firstname, lastname, birthdate, email, schoolClass);
            student = schoolService.postStudent(student);

            if (student != null) {
                System.out.println("Élève correctement créé, son id est " + student.getId());
            } else {
                System.out.println("Élève non-créé");
            }
        } catch (TooShortNameException e) {
            System.out.println("Revenez quand vous aurez changer de nom/prénom");
        } catch (WrongMailException e){
            System.out.println("Recommencez et faites attention à votre adresse e-mail");
        }

    }

    private void createSubject() {
        String name;
        String description;
        int duration;
        int coefficient;
        Subject subject;

        name = scanString("Entrez le nom de la matière");
        description = scanString("Entrez la description de la matière");
        duration = scanInt("Entrez la durée en minutes d'un cours de cette matière");
        coefficient = scanInt("Entrez le coefficient de la matière (nombre entier)");

        subject = new Subject(name, description, duration, coefficient);
        subject = schoolService.postSubject(subject);

        if (subject != null) {
            System.out.println("Matière bien créée, son id est " + subject.getId());
        } else {
            System.out.println("Matière non-créée");
        }
    }

    private void gradeStudent() {
        List<Subject> subjects = schoolService.getAllSubjects();
        int subjectChoice = -1;
        Subject subject = null;
        List<Student> students = schoolService.getAllStudents();
        int studentChoice = -1;
        Student student = null;
        double value;
        String comment;
        Grade grade;

        while (subject == null) {
            for (int i = 0; i < subjects.size(); i++) {
                System.out.printf("%d - %s\n", i, subjects.get(i).getName());
            }

            subjectChoice = scanInt("Entrez le numéro de la matière évaluée");
            try {
                subject = subjects.get(subjectChoice);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Entrez un numéro correspondant à une des matières disponibles");
            }
        }

        while (student == null) {
            for (int i = 0; i < students.size(); i++) {
                System.out.printf("%d - %s %s\n", i, students.get(i).getFirstname(), students.get(i).getLastname());
            }

            studentChoice = scanInt("Entrez le numéro de l'étudiant évalué");
            try {
                student = students.get(studentChoice);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Entrez un numéro correspondant à un des étudiants disponibles");
            }
        }

        value = scanDouble("Entrez la note (avec une virgule, pas un point)");
        comment = scanString("Entrez le commentaire");

        grade = new Grade(value, comment, subject, student);
        grade = schoolService.postGrade(grade);

        if (grade != null) {
            System.out.println("Note créée, son id est " + grade.getId());
        } else {
            System.out.println("Note non-créée");
        }
    }

    private void createDiary() {
        List<SchoolClass> schoolClasses = schoolService.getAllClasses();
        int classChoice = -1;
        SchoolClass schoolClass = null;
        Diary diary;
        List<Subject> subjects = schoolService.getAllSubjects();
        boolean finished = false;
        int addSubjectInt;
        int subjectChoice = -1;
        Subject subject;
        String day;
        Time time;
        DiarySubject diarySubject;


            while (schoolClass == null) {
                for (int i = 0; i < schoolClasses.size(); i++) {
                    System.out.printf("%d - %s\n", i, schoolClasses.get(i));
                }
                classChoice = scanInt("Entre le numéro de la classe pour laquelle tu veux créer un emploi du temps");

                try {
                    schoolClass = schoolClasses.get(classChoice);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Entrez un numéro correspondant à une des classes disponibles");
                }
            }

            diary = schoolClass.getDiary();

            while (!finished) {
                for (int i = 0; i < subjects.size(); i++) {
                    System.out.printf("%d - %s\n", i, subjects.get(i));
                }
                subjectChoice = scanInt("Entre le numéro de la matière que vous voulez ajouter");

                try {
                    subject = subjects.get(subjectChoice);

                    day = scanString("Quel jour a lieu ce cours ?");

                    time = null;
                    while (time == null){
                        try {
                            time = Time.valueOf(scanString("À quelle heure ? (HH:mm)")+ ":00");
                        } catch (IllegalArgumentException e){
                            System.out.println("Respectez bien le format pour l'heure");
                        }
                    }

                    diarySubject = new DiarySubject(day,time, diary, subject);

                    if (schoolService.addDiaryLine(diarySubject)){
                        System.out.println("Matière correctement ajoutée");

                        subjects.remove(subject);

                        addSubjectInt = scanInt("Appuyez sur 1 pour continuer à ajouter des matières");
                        finished = (addSubjectInt != 1);
                    } else {
                        System.out.println("Matière non-ajoutée, quelque chose s'est mal passé");
                        finished = true;
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Entrez un numéro correspondant à une des matières disponibles");
                }
            }

    }

    private void showClasses() {
        for (SchoolClass s : schoolService.getAllClasses()) {
            System.out.println(s);
        }
    }

    private void showSubjectNumberOfAStudent(){
        List<Student> students = schoolService.getAllStudents();
        int studentChoice = -1;
        Student student = null;

        while (student == null) {
            for (int i = 0; i < students.size(); i++) {
                System.out.printf("%d - %s %s\n", i, students.get(i).getFirstname(), students.get(i).getLastname());
            }

            studentChoice = scanInt("Entrez le numéro de l'étudiant");
            try {
                student = students.get(studentChoice);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Entrez un numéro correspondant à un des étudiants disponibles");
            }
        }

        System.out.printf("%s %s a %d matières\n",
                student.getFirstname(),
                student.getLastname(),
                student.getSchoolClass().getDiary().getDiarySubjects().size());
    }

    private void showStudentGrades(){
        List<Student> students = schoolService.getAllStudents();
        int studentChoice = -1;
        Student student = null;

        while (student == null) {
            for (int i = 0; i < students.size(); i++) {
                System.out.printf("%d - %s %s\n", i, students.get(i).getFirstname(), students.get(i).getLastname());
            }

            studentChoice = scanInt("Entrez le numéro de l'étudiant");
            try {
                student = students.get(studentChoice);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Entrez un numéro correspondant à un des étudiants disponibles");
            }
        }

        for (Grade g : student.getGrades()){
            System.out.println(g);
        }
    }

    private void showStudentAvgGrade(){
        List<Student> students = schoolService.getAllStudents();
        int studentChoice = -1;
        Student student = null;
        List<Double> gradeValues;
        double sum = 0D;

        while (student == null) {
            for (int i = 0; i < students.size(); i++) {
                System.out.printf("%d - %s %s\n", i, students.get(i).getFirstname(), students.get(i).getLastname());
            }

            studentChoice = scanInt("Entrez le numéro de l'étudiant");
            try {
                student = students.get(studentChoice);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Entrez un numéro correspondant à un des étudiants disponibles");
            }
        }

        gradeValues = student.getGrades().stream().map(g -> g.getValue()).collect(Collectors.toList());
        for (double gv : gradeValues){
            sum += gv;
        }

        System.out.printf("La moyenne de %s %s est de %.2f\n",
                student.getFirstname(), student.getLastname(), sum/((double)gradeValues.size()));
    }

    private void showDepartementStudentNumber(){
        List<Departement> departements = schoolService.getAllDepartements();
        int choice = -1;
        Departement departement = null;
        int nbStudents = 0;

        while (departement == null) {
            for (int i = 0; i < departements.size(); i++) {
                System.out.printf("%d - %s\n", i, departements.get(i).getName());
            }

            choice = scanInt("Entrez le numéro du département");
            try {
                departement = departements.get(choice);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Entrez un numéro correspondant à un des départements disponibles");
            }
        }

        for (SchoolClass s : departement.getSchoolClasses()){
            nbStudents += s.getStudents().size();
        }

        System.out.printf("%s accueille %d élèves\n", departement.getName(), nbStudents);
    }

    private void showAllStudentsFromOneLevel(){
        int levelChosen;
        List<SchoolClass> schoolClasses;

        levelChosen = scanInt("Entrez le niveau concerné");
        schoolClasses = schoolService.getClassesFromALevel(levelChosen);

        if (schoolClasses.size() > 0){
            for (SchoolClass sc : schoolClasses){
                for (Student s : sc.getStudents()){
                    System.out.println(s);
                }
            }
        } else {
            System.out.println("Il n'y a aucune classe de ce niveau");
        }
    }

    private void showDiary(){
        List<SchoolClass> schoolClasses = schoolService.getAllClasses();
        int classChoice;
        SchoolClass schoolClass = null;

        while (schoolClass == null) {
            for (int i = 0; i < schoolClasses.size(); i++) {
                System.out.printf("%d - %s\n", i, schoolClasses.get(i));
            }
            classChoice = scanInt("Entre le numéro de la classe");

            try {
                schoolClass = schoolClasses.get(classChoice);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Entrez un numéro correspondant à une des classes disponibles");
            }
        }

        System.out.println(schoolClass.getDiary());
    }

    private void leave() {
        System.out.println("Au revoir");
        schoolService.closeAll();
    }


    // Méthodes utilitaires


    private int scanInt(String message) {
        int returnedValue = -1;
        while (returnedValue == -1) {
            try {
                System.out.println(message);
                returnedValue = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrez un nombre entier positif");
            } finally {
                scanner.nextLine();
            }
        }
        return returnedValue;
    }

    private int scanInt(String message, int defaultValue) {
        int returnedValue = 0;

        System.out.println(message);

        try {
            returnedValue = scanner.nextInt();
        } catch (InputMismatchException e) {
            returnedValue = defaultValue;
        } finally {
            scanner.nextLine();
        }

        return (returnedValue == 0 ? defaultValue : returnedValue);
    }

    private double scanDouble(String message) {
        double returnedValue = 0D;
        while (returnedValue == 0D) {
            try {
                System.out.println(message);
                returnedValue = scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Entrez un nombre, utilisez la virgule et pas le point");
            } finally {
                scanner.nextLine();
            }
        }
        return returnedValue;
    }

    private double scanDouble(String message, double defaultValue) {
        double returnedValue;

        System.out.println(message);

        try {
            returnedValue = scanner.nextDouble();
        } catch (InputMismatchException e) {
            returnedValue = defaultValue;
        } finally {
            scanner.nextLine();
        }

        return (returnedValue == 0D ? defaultValue : returnedValue);
    }

    private String scanString(String message) {
        String returnedValue = "";
        while (returnedValue.isEmpty()) {
            System.out.println(message);
            returnedValue = scanner.nextLine();
        }
        return returnedValue;
    }

    private String scanString(String message, String defaultValue) {
        String returnedValue;

        System.out.println(message);
        returnedValue = scanner.nextLine();

        return (returnedValue.isEmpty() ? defaultValue : returnedValue);
    }

    private Date scanDate(String message) {
        Date returnedValue = null;

        while (returnedValue == null) {
            System.out.println(message);
            try {
                returnedValue = Date.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Format invalide");
            }
        }
        return returnedValue;
    }

    private Date scanDate(String message, Date defaultValue) {
        System.out.println(message);
        try {
            return Date.valueOf(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }

    }


}
