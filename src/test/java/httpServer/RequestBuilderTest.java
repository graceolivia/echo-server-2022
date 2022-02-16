package httpServer;

import httpServer.http.Constants;
import httpServer.http.request.HTTPRequest;
import httpServer.http.request.RequestBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RequestBuilderTest {

    @Test
    void testRequestParserReturnsCorrectHTTPResponseObject() throws IOException {

        HTTPRequest httpRequestExpected = new HTTPRequest("GET", "/", "HTTP/1.1");
        String request = "GET / HTTP/1.1" + Constants.CRLF +
                "Host: localhost:5000" + Constants.CRLF +
                "User-Agent: JUnit" + Constants.CRLF +
                "Accept: */*" + Constants.CRLF;

        HTTPRequest httpParsed = RequestBuilder.parse(request);
        assertEquals(httpRequestExpected.method, httpParsed.method);
        assertEquals(httpRequestExpected.resource, httpParsed.resource);
        assertEquals(httpRequestExpected.httpVersionNumber, httpParsed.httpVersionNumber);

    }
}
