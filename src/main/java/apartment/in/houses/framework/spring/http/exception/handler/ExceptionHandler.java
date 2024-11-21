package apartment.in.houses.framework.spring.http.exception.handler;

import apartment.in.houses.framework.spring.http.httprequest.HttpRequest;
import apartment.in.houses.framework.spring.http.httpresponse.HttpResponse;

@FunctionalInterface
public interface ExceptionHandler<T extends Exception> {
    void handle(T exception, HttpRequest request, HttpResponse response);
}
