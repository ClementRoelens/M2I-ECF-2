package exception;

public class TooShortNameException extends Exception {
    public TooShortNameException(){
        System.out.println("Un nom doit faire au moins 3 caractères");
    }
}
