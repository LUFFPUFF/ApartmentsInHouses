package apartment.in.houses.framework.spring.server.util;

import apartment.in.houses.framework.spring.annotation.*;
import apartment.in.houses.framework.spring.http.exception.handler.ExceptionHandler;
import apartment.in.houses.framework.spring.http.exception.handler.GlobalExceptionHandlerRegistry;
import apartment.in.houses.framework.spring.http.httprequest.HttpRequest;
import apartment.in.houses.framework.spring.http.httprequest.route.Route;
import apartment.in.houses.framework.spring.http.httprequest.route.RouteHandler;
import apartment.in.houses.framework.spring.http.httpresponse.HttpResponse;
import apartment.in.houses.framework.spring.http.httpresponse.StatusCode;
import apartment.in.houses.framework.spring.server.exception.ApplicationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatcherUtil {

    public static void registerController(Object controller, List<Route> routes) {
        Class<?> clazz = controller.getClass();

        if (!clazz.isAnnotationPresent(Controller.class)) {
            throw new IllegalArgumentException("Class is not annotated with @Controller: " + clazz.getName());
        }

        for (Method method : clazz.getDeclaredMethods()) {
            String httpMethod = getHttpMethod(method);
            if (httpMethod != null) {
                String pathPattern = getPathPattern(method);
                RouteHandler handler = ArgumentBinder.createHandler(controller, method);
                routes.add(new Route(httpMethod, pathPattern, handler));
            }
        }
    }

    private static String getHttpMethod(Method method) {
        Map<Class<?>, String> annotationToMethod = Map.of(
                GetMapping.class, "GET",
                PostMapping.class, "POST",
                DeleteMapping.class, "DELETE",
                PutMapping.class, "PUT",
                HeadMapping.class, "HEAD",
                OptionsMapping.class, "OPTIONS"
        );

        for (Map.Entry<Class<?>, String> entry : annotationToMethod.entrySet()) {
            if (method.isAnnotationPresent((Class<? extends Annotation>) entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    static String getPathPattern(Method method) {
        Map<Class<?>, String> annotationToPath = Map.of(
                GetMapping.class, method.isAnnotationPresent(GetMapping.class) ? method.getAnnotation(GetMapping.class).value() : "",
                PostMapping.class, method.isAnnotationPresent(PostMapping.class) ? method.getAnnotation(PostMapping.class).value() : "",
                DeleteMapping.class, method.isAnnotationPresent(DeleteMapping.class) ? method.getAnnotation(DeleteMapping.class).value() : ""
        );

        return annotationToPath.values().stream().filter(val -> !val.isEmpty()).findFirst().orElse("");
    }

    public static HttpResponse handleException(Exception e) {
        HttpResponse response = new HttpResponse();

        if (e instanceof ApplicationException appException) {
            StatusCode statusCode = appException.getStatusCode();
            response.setStatusCode(statusCode);
            response.setHtmlBody("""
            <h1>%s</h1>
            <p>%s</p>
        """.formatted(statusCode.getReasonPhrase(), e.getMessage()));
        } else if (e instanceof IllegalArgumentException) {
            response.setStatusCode(StatusCode.BAD_REQUEST);
            response.setHtmlBody("""
            <h1>%s</h1>
            <p>Invalid argument: %s</p>
        """.formatted(StatusCode.BAD_REQUEST.getReasonPhrase(), e.getMessage()));
        } else {
            response.setStatusCode(StatusCode.INTERNAL_SERVER_ERROR);
            response.setHtmlBody("""
            <h1>%s</h1>
            <p>Unexpected error: %s</p>
        """.formatted(StatusCode.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage()));
        }

        return response;
    }

    public static HttpResponse notFoundResponse() {
        HttpResponse response = new HttpResponse();
        response.setStatusCode(StatusCode.NOT_FOUND);
        response.setHtmlBody("<h1>404 Not Found</h1>");
        return response;
    }
}
