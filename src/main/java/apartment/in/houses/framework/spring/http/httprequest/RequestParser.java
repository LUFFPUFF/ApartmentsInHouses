package apartment.in.houses.framework.spring.http.httprequest;

import apartment.in.houses.framework.spring.http.exception.HttpRequestException;
import apartment.in.houses.framework.spring.http.httprequest.factory.HttpRequestFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    public HttpRequest parse(InputStream inputStream) throws HttpRequestException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String startLine;
        try {
            startLine = reader.readLine();
        } catch (IOException e) {
            throw new HttpRequestException("Ошибка при чтении стартовой строки", e);
        }

        if (startLine == null || startLine.isEmpty()) {
            throw new HttpRequestException("Пустой запрос");
        }

        String[] parts = startLine.split(" ");
        if (parts.length < 3) {
            throw new HttpRequestException("Неверная строка запроса: " + startLine);
        }

        String method = parts[0];
        String rawPath = parts[1];
        String path = extractPath(rawPath);
        Map<String, String> queryParameters = extractQueryParameters(rawPath);

        Map<String, String> headers = new HashMap<>();
        String line;
        try {
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                String[] headerParts = line.split(":", 2);
                if (headerParts.length == 2) {
                    headers.put(headerParts[0].trim(), headerParts[1].trim());
                }
            }
        } catch (IOException e) {
            throw new HttpRequestException("Ошибка при чтении заголовков", e);
        }

        InputStream body = null;
        if (headers.containsKey("Content-Length")) {
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            int maxBodyBufferSize = 1024 * 1024;
            if (contentLength > maxBodyBufferSize) {
                throw new HttpRequestException("Тело запроса слишком большое (больше " + maxBodyBufferSize + " байт)");
            }
            body = new LimitedInputStream(inputStream, contentLength);
        } else if (headers.containsKey("Transfer-Encoding") && headers.get("Transfer-Encoding").equals("chunked")) {
            body = new ChunkedInputStream(inputStream);
        }


        return HttpRequestFactory.create(method, path, headers, queryParameters, null, null, null, null, body);
    }

    private String extractPath(String rawPath) {
        int queryIndex = rawPath.indexOf("?");
        return (queryIndex >= 0) ? rawPath.substring(0, queryIndex) : rawPath;
    }

    private Map<String, String> extractQueryParameters(String rawPath) {
        int queryIndex = rawPath.indexOf("?");
        Map<String, String> queryParams = new HashMap<>();
        if (queryIndex >= 0) {
            String[] pairs = rawPath.substring(queryIndex + 1).split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=", 2);
                String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                String value = (keyValue.length > 1) ? URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8) : "";
                queryParams.put(key, value);
            }
        }
        return queryParams;
    }

    /**
     * Ограничивает поток чтения тела до указанной длины.
     */
    private static class LimitedInputStream extends InputStream {
        private final InputStream inputStream;
        private int remaining;

        public LimitedInputStream(InputStream inputStream, int limit) {
            this.inputStream = inputStream;
            this.remaining = limit;
        }

        @Override
        public int read() throws IOException {
            if (remaining <= 0) {
                return -1;
            }
            int data = inputStream.read();
            if (data != -1) {
                remaining--;
            }
            return data;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            if (remaining <= 0) {
                return -1;
            }
            len = Math.min(len, remaining);
            int bytesRead = inputStream.read(b, off, len);
            if (bytesRead != -1) {
                remaining -= bytesRead;
            }
            return bytesRead;
        }
    }

    private static class ChunkedInputStream extends InputStream {
        private final InputStream inputStream;
        private int chunkSize;
        private int bytesRead;

        public ChunkedInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            this.chunkSize = -1;
            this.bytesRead = 0;
        }

        @Override
        public int read() throws IOException {
            if (chunkSize == 0) return -1;
            if (bytesRead == chunkSize) {
                parseChunkSize();
            }
            if (chunkSize == 0) return -1;
            bytesRead++;
            return inputStream.read();
        }

        private void parseChunkSize() throws IOException {
            StringBuilder chunkSizeString = new StringBuilder();
            int read;
            while ((read = inputStream.read()) != '\r') {
                chunkSizeString.append((char) read);
            }
            inputStream.read(); // пропустить \n
            chunkSize = Integer.parseInt(chunkSizeString.toString(), 16);
            bytesRead = 0;
        }
    }
}

