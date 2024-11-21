package apartment.in.houses.framework.spring.http.exception.handler;

import java.util.HashMap;
import java.util.Map;

public class GlobalExceptionHandlerRegistry {

    private static final Map<Class<? extends Exception>, ExceptionHandler<? extends Exception>> handlers = new HashMap<>();

    public static <T extends Exception> void registerHandler(Class<T> exceptionClass, ExceptionHandler<T> handler) {
        handlers.put(exceptionClass, handler);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Exception> ExceptionHandler<T> getHandler(Class<T> exceptionClass) {
        return (ExceptionHandler<T>) handlers.get(exceptionClass);
    }
}
