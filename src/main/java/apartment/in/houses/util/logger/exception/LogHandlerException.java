package apartment.in.houses.util.logger.exception;

public class LogHandlerException extends RuntimeException {

    public LogHandlerException(String message) {
        super(message);
    }

    public LogHandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
