package echoServer;

import echoServer.HTTP.HTTPRequest;
import echoServer.HTTP.RequestParser;
import echoServer.outputManagement.ClientWriteableFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RequestParserTest {

    @Test
    void testRequestParserReturnsCorrectHTTPResponseObject() throws IOException {

        HTTPRequest httpRequestExpected = new HTTPRequest("GET", "/", "HTTP/1.1");
        String request = "GET / HTTP/1.1\r\n" +
                "Host: localhost:5000\r\n" +
                "User-Agent: curl/7.64.1\r\n" +
                "Accept: */*\r\n";

        HTTPRequest httpParsed = RequestParser.parser(request);

        assertEquals(httpRequestExpected.requestType, httpParsed.requestType);
        assertEquals(httpRequestExpected.route, httpParsed.route);
        assertEquals(httpRequestExpected.httpType, httpParsed.httpType);
    }
}
