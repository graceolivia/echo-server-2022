package echoServer;

import echoServer.HTTP.HTTPResponse;
import echoServer.HTTP.StatusCode;
import org.junit.jupiter.api.Test;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class HTTPResponseTest {

    @Test
    void testInvalidResponseReturns() throws IOException {
        StatusCode code = StatusCode.PAGE_NOT_FOUND;
        String expectedResponse = "HTTP/1.0 404 Not Found\n";
        String response = HTTPResponse.buildResponse(code);
        assertEquals(response, expectedResponse);

    }

}
