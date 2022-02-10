package httpServer;

import httpServer.http.HTTPRequest;
import httpServer.routes.Router;
import httpServer.http.StatusCode;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouterTest {
    @Test
    void testRouterReturns200ForGetRequestToSimpleGetRoute() throws IOException {
        HTTPRequest request = new HTTPRequest("GET", "/simple_get", "HTTP/1.1");
        httpServer.http.StatusCode expectedStatusCode = StatusCode.OK;
        Router router = new Router(EchoServer.getRoutes());
        StatusCode returnedCode = router.getResponse(request);
        System.out.print(returnedCode);
        assertEquals(expectedStatusCode, returnedCode);
    }

    @Test
    void testRouterReturns404ForInvalidRoute() throws IOException {
        HTTPRequest request = new HTTPRequest("GET", "/anything_invalid", "HTTP/1.1");
        httpServer.http.StatusCode expectedStatusCode = StatusCode.PAGE_NOT_FOUND;
        Router router = new Router(EchoServer.getRoutes());
        StatusCode returnedCode = router.getResponse(request);
        System.out.print(returnedCode);
        assertEquals(expectedStatusCode, returnedCode);
    }

}
