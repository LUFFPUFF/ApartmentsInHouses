package apartment.in.houses.framework.spring.server.util;

import apartment.in.houses.framework.spring.annotation.PathVariable;
import apartment.in.houses.framework.spring.annotation.RequestBody;
import apartment.in.houses.framework.spring.http.httprequest.HttpRequest;
import apartment.in.houses.framework.spring.http.httprequest.route.RouteHandler;
import apartment.in.houses.framework.spring.http.httpresponse.HttpResponse;
import apartment.in.houses.framework.spring.http.httpresponse.StatusCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class ArgumentBinder {

    public static RouteHandler createHandler(Object controller, Method method) {
        return (request, response) -> {
            try {
                logRequestDetails(request, method);

                Object[] args = bindArguments(method, request, response);
                System.out.println("Аргументы успешно связаны: " + Arrays.toString(args));

                Object result = method.invoke(controller, args);
                System.out.println("Метод выполнен, результат: " + result);

                handleResponse(result, response);

                System.out.println("Обработка запроса завершена.");
                System.out.println("========================================");
            } catch (IllegalArgumentException e) {
                handleException("Ошибка в аргументах метода", e, response, StatusCode.BAD_REQUEST);
            } catch (InvocationTargetException e) {
                handleException("Ошибка при вызове метода", e.getTargetException(), response, StatusCode.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
                handleException("Общая ошибка", e, response, StatusCode.INTERNAL_SERVER_ERROR);
            }
        };
    }

    private static Object[] bindArguments(Method method, HttpRequest request, HttpResponse response) throws IOException {
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];

            if (parameter.isAnnotationPresent(PathVariable.class)) {
                String variableName = parameter.getAnnotation(PathVariable.class).value();
                String value = request.getPathParameters().get(variableName);
                if (value == null) {
                    throw new IllegalArgumentException("Переменная пути отсутствует: " + variableName);
                }
                args[i] = convertType(value, parameter.getType());

            } else if (parameter.isAnnotationPresent(RequestBody.class)) {
                ObjectMapper objectMapper = new ObjectMapper();
                InputStream inputStream = request.getBody();
                System.out.println("InputStream available: " + inputStream.available());
                try (inputStream) {
                    args[i] = objectMapper.readValue(inputStream, parameter.getType());
                } catch (IOException e) {
                    throw new IllegalArgumentException("Ошибка при разборе тела запроса: " + e.getMessage(), e);
                }

            } else if (parameter.getType() == HttpRequest.class) {
                args[i] = request;

            } else if (parameter.getType() == HttpResponse.class) {
                args[i] = response;

            } else {
                throw new IllegalArgumentException("Unsupported parameter type: " + parameter.getType());
            }
        }

        return args;
    }

    private static void handleResponse(Object result, HttpResponse response) throws IOException {
        if (result instanceof String) {
            response.setHtmlBody((String) result);
        } else if (result != null) {
            ObjectMapper mapper = new ObjectMapper();
            response.setJsonBody(mapper.writeValueAsString(result));
        }
    }

    private static void handleException(String message, Throwable e, HttpResponse response, StatusCode statusCode) {
        System.err.println(message + ": " + e.getMessage());
        response.setStatusCode(statusCode);
        response.setHtmlBody(message + ": " + e.getMessage());
    }

    private static void logRequestDetails(HttpRequest request, Method method) {
        System.out.println("========================================");
        System.out.println("Обработка метода: " + method.getName());
        System.out.println("Получен запрос:");
        System.out.println("  URL: " + request.getPath());
        System.out.println("  HTTP-метод: " + request.getMethod());
        System.out.println("  Заголовки: " + request.getHeaders());
        System.out.println("  Переменные пути: " + request.getPathParameters());
    }

    private static Object convertType(String value, Class<?> targetType) {
        if (value == null) return null;

        try {
            if (targetType == String.class) return value;
            if (targetType == int.class || targetType == Integer.class) return Integer.parseInt(value);
            if (targetType == long.class || targetType == Long.class) return Long.parseLong(value);
            if (targetType == boolean.class || targetType == Boolean.class) return Boolean.parseBoolean(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ошибка преобразования значения \"" + value + "\" в тип: " + targetType.getName(), e);
        }
        throw new IllegalArgumentException("Неподдерживаемое преобразование типа для: " + targetType.getName());
    }
}


