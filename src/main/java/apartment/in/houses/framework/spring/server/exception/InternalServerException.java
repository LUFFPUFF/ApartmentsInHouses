package apartment.in.houses.framework.spring.server.exception;

import apartment.in.houses.framework.spring.http.httpresponse.StatusCode;

public class InternalServerException extends ApplicationException {
    public InternalServerException(String message) {
        super(StatusCode.INTERNAL_SERVER_ERROR, message);
    }
}
