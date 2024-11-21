package apartment.in.houses.framework.spring.http.exception;

import java.io.IOException;

public class HttpResponseWriteException extends IOException {

    public HttpResponseWriteException(String message) {
        super(message);
    }

    public HttpResponseWriteException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpResponseWriteException(Throwable cause) {
        super(cause);
    }
}
