package httpServer;

import httpServer.http.Constants;
import httpServer.http.request.HTTPRequest;
import httpServer.http.request.RequestBuilder;
import httpServer.http.response.HTTPResponseBuilder;
import httpServer.routes.Router;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RequestBuilderTest {
    RequestBuilder requestBuilder;
    Map<String, String> headers;
    HTTPRequest httpRequestExpected;
    String request;

    @BeforeEach
    void setUp() throws IOException {
        requestBuilder = new RequestBuilder();
        headers = new HashMap();
        headers.put("Host", "localhost:5000");
        httpRequestExpected = new HTTPRequest("GET", "/", "HTTP/1.1", headers, null);
        request = "GET / HTTP/1.1" + Constants.crlf +
                "Host: localhost:5000" + Constants.crlf +
                "User-Agent: JUnit" + Constants.crlf +
                "Accept: */*" + Constants.crlf;
    }

    @Test
    void testRequestBuilderCorrectlyParsesStatusLine() throws IOException {
        requestBuilder = requestBuilder.buildRequestLine("GET / HTTP/1.1");

        HTTPRequest httpParsed = requestBuilder.build();
        assertEquals(httpRequestExpected.method, httpParsed.method);
        assertEquals(httpRequestExpected.resource, httpParsed.resource);
        assertEquals(httpRequestExpected.httpVersionNumber, httpParsed.httpVersionNumber);
    }

    @Test
    void testRequestBuilderCorrectlyParsesHeader() throws IOException {
        requestBuilder = requestBuilder.buildHeaderLine("Host: localhost:5000");

        HTTPRequest httpParsed = requestBuilder.build();
        assertEquals(httpRequestExpected.headers.get("Host"), httpParsed.headers.get("Host"));
    }


}
