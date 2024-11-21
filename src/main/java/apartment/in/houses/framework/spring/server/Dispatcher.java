package apartment.in.houses.framework.spring.server;

import apartment.in.houses.framework.spring.http.httprequest.HttpRequest;
import apartment.in.houses.framework.spring.http.httprequest.route.Route;
import apartment.in.houses.framework.spring.http.httpresponse.HttpResponse;
import apartment.in.houses.framework.spring.server.util.DispatcherUtil;

import java.util.ArrayList;
import java.util.List;

public class Dispatcher {
    private final List<Route> routes = new ArrayList<>();
    public void registerController(Object controller) {
        DispatcherUtil.registerController(controller, routes);
    }

    public HttpResponse dispatch(HttpRequest request) {
        for (Route route : routes) {
            if (route.matches(request.getMethod(), request.getPath())) {
                HttpResponse response = new HttpResponse();
                try {
                    route.getHandler().handle(request, response);
                } catch (Exception e) {
                    DispatcherUtil.handleException(e);
                }
                return response;
            }
        }
        return DispatcherUtil.notFoundResponse();
    }
}

