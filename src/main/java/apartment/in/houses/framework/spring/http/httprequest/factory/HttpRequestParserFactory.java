package apartment.in.houses.framework.spring.http.httprequest.factory;

import apartment.in.houses.framework.spring.http.httprequest.HttpRequest;
import apartment.in.houses.framework.spring.http.httprequest.RequestParser;

public class HttpRequestParserFactory {
    public static RequestParser create() {
        return new RequestParser();
    }
}
