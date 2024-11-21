package apartment.in.houses.framework.spring.web.server.exception;

public class ServerException extends RuntimeException {

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ServerException(Throwable throwable) {
        super(throwable);
    }
}
