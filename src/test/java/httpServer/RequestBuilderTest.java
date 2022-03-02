package httpServer;

import httpServer.http.Constants;
import httpServer.http.request.HTTPRequest;
import httpServer.http.request.RequestBuilder;
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
        headers.put("Content-Length", "5");
        httpRequestExpected = new HTTPRequest("GET", "/", "HTTP/1.1", headers, "hello");
        request = "GET / HTTP/1.1" + Constants.CRLF +
                "Content-Length: 5" + Constants.CRLF +
                "Host: localhost:5000" + Constants.CRLF +
                "User-Agent: JUnit" + Constants.CRLF +
                "Accept: */*" + Constants.CRLF + Constants.CRLF +
                "hello";
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

        @Test
    void testRequestBuilderCorrectlyParsesMultipleHeaders() throws IOException {
        requestBuilder = requestBuilder.buildHeaderLine("Host: localhost:5000");
        requestBuilder = requestBuilder.buildHeaderLine("Content-Length: 5");
        HTTPRequest httpParsed = requestBuilder.build();
        assertEquals(httpRequestExpected.headers.get("Host"), httpParsed.headers.get("Host"));
        assertEquals(httpRequestExpected.headers.get("Content-Length"), httpParsed.headers.get("Content-Length"));
    }

    @Test
    void testRequestBuilderCorrectlyParsesBody() throws IOException {
        requestBuilder = requestBuilder.setBody("hello");
        HTTPRequest httpParsed = requestBuilder.build();
        assertEquals(httpRequestExpected.body, httpParsed.body);
    }



}
