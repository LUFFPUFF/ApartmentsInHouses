package apartment.in.houses.framework.spring.http.httpresponse;

import apartment.in.houses.framework.spring.http.exception.HttpResponseWriteException;
import apartment.in.houses.util.logger.CreateLogger;
import apartment.in.houses.util.logger.CustomLogger;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseWriter {

    public static void writeResponse(HttpResponse response, OutputStream outputStream) throws HttpResponseWriteException {
        try {
            response.write(outputStream);
        } catch (IOException e) {
            throw new HttpResponseWriteException(e);
        }
    }

    private static void handleErrorResponse(OutputStream outputStream) throws HttpResponseWriteException {
        HttpResponse errorResponse = new HttpResponse();
        errorResponse.setStatusCode(StatusCode.INTERNAL_SERVER_ERROR);
        errorResponse.setHtmlBody("<h1>500 Internal Server Error</h1>");

        try {
            errorResponse.write(outputStream);
        } catch (IOException e)  {
            throw new HttpResponseWriteException(e);
        }
    }
}
