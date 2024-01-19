package exception;

public class WrongMailException extends Exception {
    public WrongMailException(){
        System.out.println("Le mail doit se finir par @gmail.com");
    }
}
