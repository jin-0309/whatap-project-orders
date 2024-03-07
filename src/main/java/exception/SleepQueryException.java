package exception;

public class SleepQueryException extends CustomException {
    public SleepQueryException(Long time) {
        super("sleep time = " + time);
    }
}
