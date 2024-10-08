package apartment.in.houses.util.logger.exception;

public class LogLevelException extends RuntimeException {
    public LogLevelException(String message) {
        super(message);
    }

    public LogLevelException(String message, Throwable cause) {
        super(message, cause);
    }
}
