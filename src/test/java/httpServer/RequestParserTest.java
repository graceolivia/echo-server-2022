package httpServer;

import httpServer.http.Constants;
import httpServer.http.HTTPRequest;
import httpServer.http.RequestParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RequestParserTest {

    @Test
    void testRequestParserReturnsCorrectHTTPResponseObject() throws IOException {

        HTTPRequest httpRequestExpected = new HTTPRequest("GET", "/", "HTTP/1.1");
        String request = "GET / HTTP/1.1" + Constants.CRLF +
                "Host: localhost:5000" + Constants.CRLF +
                "User-Agent: cur/7.64.1" + Constants.CRLF +
                "Accept: */*" + Constants.CRLF;

        HTTPRequest httpParsed = RequestParser.parse(request);
        assertTrue(httpParsed.equals(httpParsed));

    }
}
