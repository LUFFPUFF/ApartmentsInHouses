package apartment.in.houses.framework.spring.server.exception;

import apartment.in.houses.framework.spring.http.httpresponse.StatusCode;

public class NotImplementedException extends ApplicationException {
    public NotImplementedException(String message) {
        super(StatusCode.NOT_IMPLEMENTED, message);
    }
}
