package apartment.in.houses.framework.spring.http.test;

import apartment.in.houses.framework.spring.http.exception.handler.GlobalExceptionHandlerRegistry;
import apartment.in.houses.framework.spring.http.httprequest.HttpRequest;
import apartment.in.houses.framework.spring.http.httprequest.factory.HttpRequestParserFactory;
import apartment.in.houses.framework.spring.http.httprequest.route.Route;
import apartment.in.houses.framework.spring.http.httpresponse.HttpResponse;
import apartment.in.houses.framework.spring.http.httpresponse.ResponseWriter;
import apartment.in.houses.framework.spring.http.httpresponse.StatusCode;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TestHttp {

    private static final List<Route> routes = new ArrayList<>();

    public static void main(String[] args) {
        int port = 8080;
        System.out.println("Starting HTTP server on port " + port + "...");

        defineRoutes();
        defineGlobalExceptionHandlers();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    handleClient(clientSocket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            HttpRequest request = HttpRequestParserFactory.create().parse(inputStream);

            Route matchingRoute = routes.stream()
                    .filter(route -> route.matches(request.getMethod(), request.getPath()))
                    .findFirst()
                    .orElse(null);

            HttpResponse response = new HttpResponse();

            if (matchingRoute != null) {
                matchingRoute.getHandler().handle(request, response);
            } else {
                response.setStatusCode(StatusCode.NOT_FOUND);
                response.setHtmlBody("<h1>404 Not Found</h1>");
            }

            ResponseWriter.writeResponse(response, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void defineRoutes() {
        routes.add(new Route("GET", "/", (request, response) -> {
            response.setHtmlBody("<h1>Welcome to the HTTP Test Server!</h1>");
        }));

        routes.add(new Route("GET", "/json", (request, response) -> {
            response.setJsonBody("{\"message\": \"This is a JSON response\"}");
        }));

        // 404 route
        routes.add(new Route("GET", "/notfound", (request, response) -> {
            response.setStatusCode(StatusCode.NOT_FOUND);
            response.setHtmlBody("<h1>404 Not Found</h1>");
        }));

        routes.add(new Route("GET", "/user/{id}", (request, response) -> {
            String id = request.getPathParameter("id");
            response.setHtmlBody("<h1>User ID: " + id + "</h1>");
        }));

        routes.add(new Route("POST", "/submit", (request, response) -> {
            String body = request.getBody().toString();
            response.setHtmlBody("<h1>Submitted Data:</h1><pre>" + body + "</pre>");
        }));

        routes.add(new Route("PUT", "/user/{id}", (request, response) -> {
            String id = request.getPathParameter("id");
            String body = request.getBody().toString();
            response.setHtmlBody("<h1>Updated User ID: " + id + "</h1><pre>" + body + "</pre>");
        }));

        routes.add(new Route("DELETE", "/user/{id}", (request, response) -> {
            String id = request.getPathParameter("id");
            response.setHtmlBody("<h1>Deleted User ID: " + id + "</h1>");
        }));

        routes.add(new Route("PATCH", "/user/{id}", (request, response) -> {
            String id = request.getPathParameter("id");
            String body = request.getBody().toString();
            response.setHtmlBody("<h1>Partially Updated User ID: " + id + "</h1><pre>" + body + "</pre>");
        }));

        routes.add(new Route("HEAD", "/metadata", (request, response) -> {
            response.setStatusCode(StatusCode.OK);
            response.addHeader("X-Custom-Header", "HeaderValue");
        }));

        routes.add(new Route("OPTIONS", "/options", (request, response) -> {
            response.setStatusCode(StatusCode.OK);
            response.addHeader("Allow", "GET, POST, PUT, DELETE, PATCH, OPTIONS, HEAD");
        }));

        routes.add(new Route("GET", "/error", (request, response) -> {
            throw new IllegalArgumentException("Invalid input provided!");
        }));
    }

    private static void defineGlobalExceptionHandlers() {
        GlobalExceptionHandlerRegistry.registerHandler(IllegalArgumentException.class, (exception, request, response) -> {
            response.setStatusCode(StatusCode.BAD_REQUEST);
            response.setHtmlBody("<h1>Bad Request</h1><p>" + exception.getMessage() + "</p>");
        });

        GlobalExceptionHandlerRegistry.registerHandler(NullPointerException.class, (exception, request, response) -> {
            response.setStatusCode(StatusCode.INTERNAL_SERVER_ERROR);
            response.setHtmlBody("<h1>Null Pointer Exception</h1><p>An unexpected error occurred.</p>");
        });
    }
}
