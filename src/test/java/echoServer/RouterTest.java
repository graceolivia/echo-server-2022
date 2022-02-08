package echoServer;

import echoServer.HTTP.HTTPRequest;
import echoServer.HTTP.Router;
import echoServer.http.StatusCode;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouterTest {
    @Test
    void testRouterReturns200ForGetRequestToSimpleGetRoute() throws IOException {
        HTTPRequest request = new HTTPRequest("GET", "/simple_get", "HTTP/1.1");
        echoServer.http.StatusCode expectedStatusCode = StatusCode.OK;
        StatusCode returnedCode = Router.router(request);
        System.out.print(returnedCode);
        assertEquals(expectedStatusCode, returnedCode);
    }

    @Test
    void testRouterReturns404ForInvalidRoute() throws IOException {
        HTTPRequest request = new HTTPRequest("GET", "/anything_invalid", "HTTP/1.1");
        echoServer.http.StatusCode expectedStatusCode = StatusCode.PAGE_NOT_FOUND;
        StatusCode returnedCode = Router.router(request);
        System.out.print(returnedCode);
        assertEquals(expectedStatusCode, returnedCode);
    }

}
