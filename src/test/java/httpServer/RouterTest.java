package httpServer;

import httpServer.http.HTTPRequest;
import httpServer.routes.Router;
import httpServer.http.StatusCodes;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouterTest {
    @Test
    void testRouterReturns200ForGetRequestToSimpleGetRoute() throws IOException {
        HTTPRequest request = new HTTPRequest("GET", "/simple_get", "HTTP/1.1");
        StatusCodes expectedStatusCodes = StatusCodes.OK;
        Router router = new Router(HttpServer.getRoutes());
        StatusCodes returnedCode = router.getResponse(request);
        System.out.print(returnedCode);
        assertEquals(expectedStatusCodes, returnedCode);
    }

    @Test
    void testRouterReturns404ForInvalidRoute() throws IOException {
        HTTPRequest request = new HTTPRequest("GET", "/anything_invalid", "HTTP/1.1");
        StatusCodes expectedStatusCodes = StatusCodes.PAGE_NOT_FOUND;
        Router router = new Router(HttpServer.getRoutes());
        StatusCodes returnedCode = router.getResponse(request);
        System.out.print(returnedCode);
        assertEquals(expectedStatusCodes, returnedCode);
    }

}
