package apartment.in.houses.framework.spring.server.exception;

import apartment.in.houses.framework.spring.http.httpresponse.StatusCode;

public class BadRequestException extends ApplicationException {
    public BadRequestException(StatusCode statusCode, String message) {
        super(statusCode, message);
    }
}
