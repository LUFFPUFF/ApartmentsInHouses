package apartment.in.houses.framework.spring.http.adapter;

import apartment.in.houses.framework.spring.http.httprequest.HttpRequest;
import apartment.in.houses.framework.spring.http.httpresponse.HttpResponse;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpAdapter {
    public static HttpRequest fromSocketRequest(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Map<String, String> headers = new HashMap<>();
        Map<String, String> queryParameters = new HashMap<>();
        String method = null;
        String path = null;
        String body = null;

        // Парсинг стартовой строки (метод, путь и версия HTTP)
        String startLine = reader.readLine();
        if (startLine != null && !startLine.isEmpty()) {
            String[] parts = startLine.split(" ");
            method = parts[0];
            String[] pathParts = parts[1].split("\\?", 2);
            path = pathParts[0];

            // Парсинг query параметров
            if (pathParts.length > 1) {
                String[] queryParams = pathParts[1].split("&");
                for (String param : queryParams) {
                    String[] keyValue = param.split("=", 2);
                    queryParameters.put(
                            keyValue[0],
                            keyValue.length > 1 ? keyValue[1] : ""
                    );
                }
            }
        }

        // Парсинг заголовков
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            int colonIndex = line.indexOf(":");
            if (colonIndex != -1) {
                String name = line.substring(0, colonIndex).trim();
                String value = line.substring(colonIndex + 1).trim();
                headers.put(name, value);
            }
        }

        // Считывание тела запроса, если есть
        if (headers.containsKey("Content-Length")) {
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            char[] bodyChars = new char[contentLength];
            reader.read(bodyChars);
            body = new String(bodyChars);
        }

        return new HttpRequest(
                method,
                path,
                headers,
                queryParameters,
                new HashMap<>(), // pathParameters (можно обработать отдельно, если потребуется)
                new HashMap<>(), // formData (обрабатывается отдельно)
                new HashMap<>(), // files (обрабатывается отдельно)
                new HashMap<>(), // cookies (можно добавить поддержку)
                body != null ? new ByteArrayInputStream(body.getBytes()) : null
        );
    }

    public static void applyToSocketResponse(HttpResponse httpResponse, Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        StringBuilder responseBuilder = new StringBuilder();

        // Формирование стартовой строки ответа
        responseBuilder
                .append(httpResponse.getHttpVersion())
                .append(" ")
                .append(httpResponse.getStatusCode().getCode())
                .append(" ")
                .append(httpResponse.getStatusCode().getReasonPhrase())
                .append("\r\n");

        // Добавление заголовков
        for (Map.Entry<String, String> header : httpResponse.getHeaders().entrySet()) {
            responseBuilder.append(header.getKey()).append(": ").append(header.getValue()).append("\r\n");
        }

        // Завершение заголовков
        responseBuilder.append("\r\n");

        // Отправка заголовков
        outputStream.write(responseBuilder.toString().getBytes());

        // Отправка тела ответа
        if (httpResponse.getBody() != null) {
            outputStream.write(httpResponse.getBody());
        }

        outputStream.flush();
    }
}

