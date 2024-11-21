package apartment.in.houses.framework.spring.server.exception;

import apartment.in.houses.framework.spring.http.httpresponse.StatusCode;

public class UnauthorizedException extends ApplicationException {
    public UnauthorizedException(String message) {
        super(StatusCode.UNAUTHORIZED, message);
    }
}
