package echoServer;

import echoServer.HTTP.HTTPResponse;
import echoServer.HTTP.StatusCode;
import org.junit.jupiter.api.Test;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class HTTPResponseTest {

    @Test
    void testInvalidRequestReturns404HTTPResponse() throws IOException {
        StatusCode code = StatusCode.PAGE_NOT_FOUND;
        String expectedResponse = "HTTP/1.1 404 Not Found\r\nContent-Length: 0\r\n";
        String response = HTTPResponse.toString(code);
        assertEquals(response, expectedResponse);

    }

}
