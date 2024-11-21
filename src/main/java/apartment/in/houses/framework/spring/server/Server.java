package apartment.in.houses.framework.spring.server;

import apartment.in.houses.framework.spring.http.adapter.HttpAdapter;
import apartment.in.houses.framework.spring.http.exception.HttpRequestException;
import apartment.in.houses.framework.spring.http.httprequest.HttpRequest;
import apartment.in.houses.framework.spring.http.httprequest.RequestParser;
import apartment.in.houses.framework.spring.http.httprequest.factory.HttpRequestParserFactory;
import apartment.in.houses.framework.spring.http.httpresponse.HttpResponse;
import apartment.in.houses.framework.spring.http.httpresponse.ResponseWriter;
import apartment.in.houses.framework.spring.http.httpresponse.StatusCode;
import apartment.in.houses.framework.spring.server.util.ThreadPoolUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int port;
    private final Dispatcher dispatcher;

    public Server(int port, Dispatcher dispatcher) {
        this.port = port;
        this.dispatcher = dispatcher;
    }

    public void start() {
        ThreadPoolUtil.initialize(10);

        System.out.println("Starting server on port " + port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ThreadPoolUtil.getThreadPool().submit(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            HttpRequest request = HttpAdapter.fromSocketRequest(clientSocket);

            HttpResponse response = dispatcher.dispatch(request);

            HttpAdapter.applyToSocketResponse(response, clientSocket);
        } catch (Exception e) {
            generateErrorResponse(clientSocket, e);
            throw new RuntimeException(e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void generateErrorResponse(Socket clientSocket, Exception e) {
        try {
            HttpResponse response = new HttpResponse();
            response.setStatusCode(StatusCode.INTERNAL_SERVER_ERROR);
            response.setHtmlBody("<h1>Internal Server Error</h1><p>" + e.getMessage() + "</p>");

            HttpAdapter.applyToSocketResponse(response, clientSocket);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
