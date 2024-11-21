package apartment.in.houses.framework.spring.server.exception;

import apartment.in.houses.framework.spring.http.httpresponse.StatusCode;

public class ForbiddenException extends ApplicationException {
    public ForbiddenException(String message) {
        super(StatusCode.FORBIDDEN, message);
    }
}
