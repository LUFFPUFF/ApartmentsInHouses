package apartment.in.houses.framework.spring.http.httprequest.factory;

import apartment.in.houses.framework.spring.http.httprequest.HttpRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class HttpRequestFactory {
    public static HttpRequest create(String method, String path, Map<String, String> headers,
                              Map<String, String> queryParameters, Map<String, String> pathParameters,
                              Map<String, String> formData, Map<String, byte[]> files,
                              Map<String, String> cookies, InputStream body) {
        return new HttpRequest(method, path, headers, queryParameters, pathParameters,
                formData, files, cookies, body);
    }

    public static HttpRequest createNoBody(String method, String path, Map<String, String> headers,
                                     Map<String, String> queryParameters, Map<String, String> pathParameters,
                                     Map<String, String> formData, Map<String, byte[]> files,
                                     Map<String, String> cookies) {
        return new HttpRequest(method, path, headers, queryParameters, pathParameters,
                formData, files, cookies);
    }
}
