package numbers;

public class ParameterException extends IllegalArgumentException{
    public ParameterException() {
    }

    public ParameterException(String s) {
        super(s);
    }

    public ParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterException(Throwable cause) {
        super(cause);
    }
}
