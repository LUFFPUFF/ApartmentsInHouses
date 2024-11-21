package apartment.in.houses.framework.spring.http.httprequest.route;

import apartment.in.houses.framework.spring.http.exception.HttpRequestAndResponseException;
import apartment.in.houses.framework.spring.http.exception.handler.ExceptionHandler;
import apartment.in.houses.framework.spring.http.exception.handler.GlobalExceptionHandlerRegistry;
import apartment.in.houses.framework.spring.http.httprequest.HttpRequest;
import apartment.in.houses.framework.spring.http.httpresponse.HttpResponse;
import apartment.in.houses.framework.spring.http.httpresponse.StatusCode;

public interface RouteHandler {

    @SuppressWarnings("unchecked")
    default void handle(HttpRequest request, HttpResponse response) {
        try {
            handleRequest(request, response);
        } catch (Exception e) {
            ExceptionHandler<Exception> handler = GlobalExceptionHandlerRegistry.getHandler((Class<Exception>) e.getClass());
            if (handler != null) {
                handler.handle(e, request, response);
            } else {
                response.setStatusCode(StatusCode.INTERNAL_SERVER_ERROR);
                response.setHtmlBody("<h1>Internal Server Error</h1><p>" + e.getMessage() + "</p>");
            }
        }
    }

    void handleRequest(HttpRequest request, HttpResponse response) throws Exception;
}
