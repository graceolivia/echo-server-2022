package httpServer;

import httpServer.http.request.HTTPRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static httpServer.http.Constants.CRLF;
import static org.junit.jupiter.api.Assertions.*;

public class RequestParserTest {

    RequestParser requestParserHelper;
    StringBuilder stringBuilder;

    @BeforeEach
    void setUp() throws IOException {
        requestParserHelper = new RequestParser();
        stringBuilder = new StringBuilder();
    }

    @Test
    void storeHttpRequestWithBodyInHttpRequestObjectWorks() throws IOException {
        ClientReadable mockBufferedReaderWrapper = new MockBufferedReaderWrapper("GET / HTTP/1.1" + CRLF +
                "Content-Length: 2" + CRLF +
                "Host: localhost:5000" + CRLF +
                "User-Agent: JUnit" + CRLF +
                "Accept: */*" + CRLF + CRLF +
                "hi");
        HTTPRequest request = requestParserHelper.storeHttpRequest(mockBufferedReaderWrapper);
        assertEquals("GET", request.method);
        assertEquals("/", request.resource);
        assertEquals("HTTP/1.1", request.httpVersionNumber);
        assertEquals("2", request.headers.get("Content-Length"));
        assertEquals("localhost:5000", request.headers.get("Host"));
        assertEquals("JUnit", request.headers.get("User-Agent"));
        assertEquals("*/*", request.headers.get("Accept"));
        assertEquals("hi", request.body);
        assertEquals(4, request.headers.size());
    }

    @Test
    void storeHttpRequestInHttpRequestObjectWorks() throws IOException {
        ClientReadable mockBufferedReaderWrapper = new MockBufferedReaderWrapper("GET / HTTP/1.1" + CRLF +
                "Content-Length: 0" + CRLF +
                "Host: localhost:5000" + CRLF +
                "User-Agent: JUnit" + CRLF +
                "Accept: */*" + CRLF + CRLF );
        HTTPRequest request = requestParserHelper.storeHttpRequest(mockBufferedReaderWrapper);
        assertEquals("GET", request.method);
        assertEquals("/", request.resource);
        assertEquals("HTTP/1.1", request.httpVersionNumber);
        assertEquals("0", request.headers.get("Content-Length"));
        assertEquals("localhost:5000", request.headers.get("Host"));
        assertEquals("JUnit", request.headers.get("User-Agent"));
        assertEquals("*/*", request.headers.get("Accept"));
        assertEquals(null, request.body);
        assertEquals(4, request.headers.size());
    }

    @Test
    void storeHttpRequestThrowsErrorForBlankRequest() throws IOException {
        ClientReadable mockBufferedReaderWrapper = new MockBufferedReaderWrapper("");

        Exception exception = assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            requestParserHelper.storeHttpRequest(mockBufferedReaderWrapper);
        });

        String expectedMessage = "Index 1 out of bounds for length 1";
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);

        assertTrue(actualMessage.contains(expectedMessage));


    }

}
