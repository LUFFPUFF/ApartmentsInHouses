package apartment.in.houses.framework.spring.http.httprequest.route;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Route {
    private String method;
    private String pathPattern;
    private Pattern compiledPattern;
    private RouteHandler routeHandler;

    public Route(String method, String pathPattern, RouteHandler routeHandler) {
        this.method = method;
        this.pathPattern = pathPattern;
        this.compiledPattern = compilePathPattern(pathPattern);
        this.routeHandler = routeHandler;
    }

    public boolean matches(String method, String path) {
        if (!this.method.equalsIgnoreCase(method)) {
            return false;
        }
        return compiledPattern.matcher(path).matches();
    }

    public Map<String, String> extractPathParameters(String path) {
        Matcher matcher = compiledPattern.matcher(path);
        Map<String, String> pathParameters = new HashMap<>();
        if (matcher.matches()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                String paramName = matcher.group(i);
                pathParameters.put(paramName, matcher.group(i));
            }
        }
        return pathParameters;
    }

    public RouteHandler getHandler() {
        return routeHandler;
    }

    public String getPathPattern() {
        return pathPattern;
    }

    private Pattern compilePathPattern(String pathPattern) {
        String regex = pathPattern.replaceAll("\\{([^/]+)}", "(?<$1>[^/]+)");
        return Pattern.compile(regex);
    }
}
