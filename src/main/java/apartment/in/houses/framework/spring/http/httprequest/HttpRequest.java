package apartment.in.houses.framework.spring.http.httprequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class HttpRequest {
    private final String method;
    private final String path;
    private final Map<String, String> headers;
    private final Map<String, String> queryParameters;
    private final Map<String, String> pathParameters;
    private final Map<String, String> formData;
    private final Map<String, byte[]> files;
    private final Map<String, String> cookies;
    private InputStream body;

    public HttpRequest(String method, String path, Map<String, String> headers,
                       Map<String, String> queryParameters, Map<String, String> pathParameters,
                       Map<String, String> formData, Map<String, byte[]> files,
                       Map<String, String> cookies, InputStream body) {
        this.method = method;
        this.path = path;
        this.headers = headers != null ? headers : new HashMap<>();
        this.queryParameters = queryParameters != null ? queryParameters : new HashMap<>();
        this.pathParameters = pathParameters != null ? pathParameters : new HashMap<>();
        this.formData = formData != null ? formData : new HashMap<>();
        this.files = files != null ? files : new HashMap<>();
        this.cookies = cookies != null ? cookies : new HashMap<>();
        this.body = body;
    }

    public HttpRequest(String method, String path, Map<String, String> headers,
                       Map<String, String> queryParameters, Map<String, String> pathParameters,
                       Map<String, String> formData, Map<String, byte[]> files,
                       Map<String, String> cookies) {
        this.method = method;
        this.path = path;
        this.headers = headers != null ? headers : new HashMap<>();
        this.queryParameters = queryParameters != null ? queryParameters : new HashMap<>();
        this.pathParameters = pathParameters != null ? pathParameters : new HashMap<>();
        this.formData = formData != null ? formData : new HashMap<>();
        this.files = files != null ? files : new HashMap<>();
        this.cookies = cookies != null ? cookies : new HashMap<>();
    }

    public String getMethod() { return method; }
    public String getPath() { return path; }
    public String getHeader(String name) { return headers.get(name); }
    public String getQueryParam(String name) { return queryParameters.get(name); }
    public String getPathParameter(String name) { return pathParameters.get(name); }
    public String getFormField(String name) { return formData.get(name); }
    public byte[] getFile(String name) { return files.get(name); }
    public String getCookie(String name) { return cookies.get(name); }

    public Map<String, String> getPathParameters() {
        return pathParameters;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public InputStream getBody() {
        return body;
    }

    public <T> T getBodyAs(Class<T> type) throws IOException {
        if (body == null) {
            throw new IllegalStateException("Request body is null");
        }
        try (InputStream stream = body) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(stream, type);
        } catch (IOException e) {
            System.err.println("Failed to deserialize request body: " + e.getMessage());
            throw e;
        }
    }

}
