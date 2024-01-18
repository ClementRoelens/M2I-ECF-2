package exception;

public class TooYoungException extends Exception {
    public TooYoungException(){
        System.out.println("Un professeur doit avoir au moins 18 ans");
    }
}
