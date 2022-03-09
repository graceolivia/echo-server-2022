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
    void isRequestLineCorrectlyIdentifiesNonRequestLine() throws IOException {
        assertFalse(requestParserHelper.isRequestLine("Content-Length: 0"));
    }

    @Test
    void isRequestLineCorrectlyIdentifiesRequestLine() throws IOException {
        assertTrue(requestParserHelper.isRequestLine("HTTP1.1 / GET"));
    }

    @Test
    void correctlyTellEndOfHeadersIsNotReached() throws IOException {
        stringBuilder.append("GET / HTTP/1.1" + CRLF +
                "Content-Length:");
        assertFalse(requestParserHelper.atEndOfHeaders(stringBuilder));
    }

    @Test
    void correctlyTellEndOfHeadersIsReached() throws IOException {
        stringBuilder.append("GET / HTTP/1.1" + CRLF +
            "Content-Length: 0" + CRLF +
            "Host: localhost:5000" + CRLF +
            "User-Agent: JUnit" + CRLF +
            "Accept: */*" + CRLF + CRLF);
        assertTrue(requestParserHelper.atEndOfHeaders(stringBuilder));
    }

    @Test
    void storeHttpRequestInHttpRequestObjectWorks() throws IOException {
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
    }



}
