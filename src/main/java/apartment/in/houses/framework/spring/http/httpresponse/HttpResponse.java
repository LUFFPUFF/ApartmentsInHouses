package apartment.in.houses.framework.spring.http.httpresponse;

import apartment.in.houses.framework.spring.http.exception.HttpResponseWriteException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private String httpVersion;
    private StatusCode statusCode;
    private final Map<String, String> headers = new HashMap<>();
    private byte[] body;

    public HttpResponse() {
        this.httpVersion = "HTTP/1.1";
        this.statusCode = StatusCode.OK;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
        if (body != null) {
            addHeader("Content-Length", String.valueOf(body.length));
        }
    }

    public void setBody(String body, String contentType) {
        this.body = body.getBytes();
        addHeader("Content-Type", contentType);
        addHeader("Content-Length", String.valueOf(this.body.length));
    }

    public void setJsonBody(String json) {
        setBody(json, "application/json");
    }

    public void setHtmlBody(String html) {
        setBody(html, "text/html");
    }

    public void write(OutputStream outputStream) throws IOException {
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder
                .append(httpVersion)
                .append(" ")
                .append(statusCode.getCode())
                .append(" ")
                .append(statusCode.getReasonPhrase())
                .append("\r\n");

        for (Map.Entry<String, String> header : headers.entrySet()) {
            responseBuilder.append(header.getKey()).append(": ").append(header.getValue()).append("\r\n");
        }
        responseBuilder.append("\r\n");

        outputStream.write(responseBuilder.toString().getBytes());
        if (body != null) {
            outputStream.write(body);
        }
        outputStream.flush();
    }

}
