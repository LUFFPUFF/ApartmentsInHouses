package apartment.in.houses.util.logger.exception;

public class LoggerConfigurationException extends RuntimeException {

    public LoggerConfigurationException(String message) {
        super(message);
    }

    public LoggerConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
