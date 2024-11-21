package apartment.in.houses.framework.spring.http.exception;

public class HttpRequestAndResponseException extends RuntimeException {

    public HttpRequestAndResponseException() {
    }

    public HttpRequestAndResponseException(String message) {
        super(message);
    }

    public HttpRequestAndResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpRequestAndResponseException(Throwable cause) {
        super(cause);
    }

    public HttpRequestAndResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
