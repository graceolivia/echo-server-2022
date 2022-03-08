package httpServer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static httpServer.http.Constants.CRLF;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RequestParserHelperTest {

    RequestParserHelper requestParserHelper;
    StringBuilder stringBuilder;

    @BeforeEach
    void setUp() throws IOException {
        requestParserHelper = new RequestParserHelper();
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



}
