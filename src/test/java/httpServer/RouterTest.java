package httpServer;

import httpServer.http.Constants;
import httpServer.http.HTTPRequest;
import httpServer.http.ResponseBuilder;
import httpServer.routes.Router;
import httpServer.http.StatusCodes;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouterTest {
    @Test
    void testRouterReturns200ForGetRequestToSimpleGetRoute() throws IOException {
        HTTPRequest request = new HTTPRequest("GET", "/simple_get", "HTTP/1.1");
        String expectedResponse = "HTTP/1.1 " + StatusCodes.OK.httpResponse + Constants.CRLF + "Allow: GET" + Constants.CRLF + "Content-Length: 0" + Constants.CRLF;
        ResponseBuilder responseBuilder = new ResponseBuilder();
        Router router = new Router(HttpServer.getRoutes(), responseBuilder);
        String returnedCode = router.getResponse(request);
        System.out.print(returnedCode);
        assertEquals(expectedResponse, returnedCode);
    }

    @Test
    void testRouterReturns404ForInvalidRoute() throws IOException {
        HTTPRequest request = new HTTPRequest("GET", "/anything_invalid", "HTTP/1.1");
        String expectedResponse = "HTTP/1.1 " + StatusCodes.PAGE_NOT_FOUND.httpResponse + Constants.CRLF + "Content-Length: 0" + Constants.CRLF;
        ResponseBuilder responseBuilder = new ResponseBuilder();
        Router router = new Router(HttpServer.getRoutes(), responseBuilder);
        String returnedCode = router.getResponse(request);
        System.out.print(returnedCode);
        assertEquals(expectedResponse.toString(), returnedCode);
    }

    @Test
    void testRouterReturns405ForInvalidMethod() throws IOException {
        HTTPRequest request = new HTTPRequest("GET", "/head_request", "HTTP/1.1");
        String expectedResponse = "HTTP/1.1 " + StatusCodes.NOT_ACCEPTABLE.httpResponse + Constants.CRLF + "Allow: HEAD, OPTIONS" + Constants.CRLF +  "Content-Length: 0" + Constants.CRLF;;
        ResponseBuilder responseBuilder = new ResponseBuilder();
        Router router = new Router(HttpServer.getRoutes(), responseBuilder);
        String returnedCode = router.getResponse(request);
        System.out.print(returnedCode);
        assertEquals(expectedResponse, returnedCode);
    }

}
