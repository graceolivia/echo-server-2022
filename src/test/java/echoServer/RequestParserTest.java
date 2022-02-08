package echoServer;

import echoServer.HTTP.HTTPRequest;
import echoServer.outputManagement.ClientWriteableFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RequestParserTest {

    @Test
    void testRequestParserReturnsCorrectHTTPResponseObject() throws IOException {

        HTTPRequest httpRequest = new HTTPRequest("GET", "/", "HTTP/1.1");



    }

}
