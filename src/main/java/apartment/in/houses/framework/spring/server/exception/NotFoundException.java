package apartment.in.houses.framework.spring.server.exception;

import apartment.in.houses.framework.spring.http.httpresponse.StatusCode;

public class NotFoundException extends ApplicationException {
    public NotFoundException(String message) {
        super(StatusCode.NOT_FOUND, message);
    }
}
