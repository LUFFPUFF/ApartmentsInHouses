package apartment.in.houses.framework.spring.web.server.exception;

public class LifecycleException extends RuntimeException {

    public LifecycleException(String message) {
        super(message);
    }

    public LifecycleException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public LifecycleException(Throwable throwable) {
        super(throwable);
    }
}
