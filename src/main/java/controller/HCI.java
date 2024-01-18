package controller;

import entity.Departement;
import entity.SchoolClass;
import entity.Student;
import entity.Teacher;
import exception.TooShortNameException;
import exception.TooYoungException;
import service.SchoolService;

import java.sql.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class HCI {
    private SchoolService schoolService = SchoolService.getInstance();
    private Scanner scanner = new Scanner(System.in);


    public void menu(){
        int choice;

        choice = scanInt("\nBonjour, que voulez-vous faire ?\n" +
                "1 - Crée un département\n" +
                "2 - Créer un professeur\n" +
                "3 - Créer une classe\n" +
                "4 - Créer un élève\n" +
                "5 - Créer une matière\n" +
                "6 - Créer un emploi du temps\n" +
                "0 - Quitter");

        switch (choice){
            case 1 -> createDepartement();
            case 2 -> createTeacher();
            case 3 -> createSchoolClass();
            case 4 -> createStudent();
            case 5 -> createSubject();
            case 6 -> createDiary();
            case 0 -> leave();
        }

        if (choice != 0){
            menu();
        }
    }


    private void createDepartement(){
        String name;
        Departement departement;

        name = scanString("Entrez le nom du département");

        departement = new Departement(name);
        departement = schoolService.postDepartement(departement);

        if (departement != null){
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
        birthdate = scanDate("Entrez la date de naissance du professeur au format 31-12-2023 (il doit au moins avoir 18 ans)");
        while (rank == ' '){
            System.out.println("Entrez le grade du professeur (A, B ou C)");
            String rawValue = scanner.nextLine();
            if (rawValue.equals("A") || rawValue.equals("B") || rawValue.equals("C")){
                rank = rawValue.charAt(0);
            }
        }

        while (isReferentInt != 0 && isReferentInt !=1){
            isReferentInt = scanInt("Entrez 1 si le professeur est professeur principal, sinon entrez 0");
        }
        isReferent = (isReferentInt == 1);


        while (departement == null){
            for (int i = 0; i < departements.size(); i++) {
                System.out.printf("%d - %s\n", i, departements.get(i).getName());
            }

            departementChoice = scanInt("Entrez le numéro du département du professeur");
            try {
                departement = departements.get(departementChoice);
            } catch (IndexOutOfBoundsException e){
                System.out.println("Entrez un numéro correspondant à un des départements disponibles");
            }
        }

        while (isDirectorInt != 0 && isDirectorInt !=1){
            isDirectorInt = scanInt("Entrez 1 si le professeur est le directeur de son département, sinon entrez 0");
        }
        isDirector = (isDirectorInt == 1);

        try {
            teacher = new Teacher(firstname,lastname,birthdate,rank, isReferent, departement,isDirector);
            teacher = schoolService.postTeacher(teacher);
            if (teacher != null){
                System.out.println("Professeur correctement créé, son id est " + teacher.getId());
            } else {
                System.out.println("Professeur non-créé");
            }
        } catch (TooShortNameException e){
            System.out.println(e);
        } catch (TooYoungException e){
            System.out.println(e);
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
        while (departement == null){
            for (int i = 0; i < departements.size(); i++) {
                System.out.printf("%d - %s\n", i, departements.get(i).getName());
            }

            departementChoice = scanInt("Entrez le numéro du département de la classe");
            try {
                departement = departements.get(departementChoice);
            } catch (IndexOutOfBoundsException e){
                System.out.println("Entrez un numéro correspondant à un des départements disponibles");
            }
        }

        schoolClass = new SchoolClass(name,year,departement);
        schoolClass = schoolService.postSchoolClass(schoolClass);

        if (schoolClass != null){
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
        birthdate = scanDate("Entrez la date de naissance de l'élève au format 31-12-2032");
        email = scanString("Entrez l'adresse e-mail de l'élève");

        while (schoolClass == null){
            for (int i = 0; i < schoolClasses.size(); i++) {
                System.out.printf("%d - %s\n", i, schoolClasses.get(i).getName());
            }

            classChoice = scanInt("Entrez le numéro de la classe de l'élève");
            try {
                schoolClass = schoolClasses.get(classChoice);
            } catch (IndexOutOfBoundsException e){
                System.out.println("Entrez un numéro correspondant à une des classes disponibles");
            }
        }
        try {
            student = new Student(firstname,lastname,birthdate,email,schoolClass);
            student = schoolService.postStudent(student);

            if (student != null){
                System.out.println("Élève correctement créé, son id est " + student.getId());
            } else {
                System.out.println("Élève non-créé");
            }
        } catch (TooShortNameException e){
            System.out.println(e);
        }

    }

    private void createSubject() {

    }

    private void createDiary() {

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
