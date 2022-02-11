package httpServer;

import httpServer.http.Constants;
import httpServer.http.HTTPRequest;
import httpServer.http.HTTPResponseWriter;
import httpServer.outputManagement.ClientWriteableFactory;
import httpServer.routes.Router;
import httpServer.http.StatusCodes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static httpServer.HttpServer.getRoutes;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouterTest {

    HTTPResponseWriter responseBuilder;
    Router router;

    @BeforeEach
    void setUp() throws IOException {
        responseBuilder = new HTTPResponseWriter();
        router = new Router(HttpServer.getRoutes(), responseBuilder);
    }
    @Test
    void testRouterReturns200ForGetRequestToResourceWithGetAllowed() throws IOException {
        HTTPRequest request = new HTTPRequest("GET", "/simple_get", "HTTP/1.1");
        String expectedResponse = "HTTP/1.1 " + StatusCodes.OK.httpResponse + Constants.CRLF + "Allow: GET" + Constants.CRLF + "Content-Length: 0" + Constants.CRLF;
        String returnedResponse = router.getResponse(request);
        assertEquals(expectedResponse, returnedResponse);
    }

    @Test
    void testRouterReturns404ForNonExistentResource() throws IOException {
        HTTPRequest request = new HTTPRequest("GET", "/anything_invalid", "HTTP/1.1");
        String expectedResponse = "HTTP/1.1 " + StatusCodes.PAGE_NOT_FOUND.httpResponse + Constants.CRLF + "Content-Length: 0" + Constants.CRLF;
        String returnedResponse = router.getResponse(request);
        assertEquals(expectedResponse.toString(), returnedResponse);
    }

    @Test
    void testRouterReturns405ForInvalidMethodRequestToExistingResource() throws IOException {
        HTTPRequest request = new HTTPRequest("GET", "/head_request", "HTTP/1.1");
        String expectedResponse = "HTTP/1.1 " + StatusCodes.NOT_ACCEPTABLE.httpResponse + Constants.CRLF + "Allow: HEAD, OPTIONS" + Constants.CRLF +  "Content-Length: 0" + Constants.CRLF;;
        String returnedResponse = router.getResponse(request);
        assertEquals(expectedResponse, returnedResponse);
    }

    @Test
    void testRouterReturns200ForFirstAllowedRequestTypeToExistingResourceWithMultipleMethods() throws IOException {
        HTTPRequest request = new HTTPRequest("OPTIONS", "/head_request", "HTTP/1.1");
        String expectedResponse = "HTTP/1.1 " + StatusCodes.OK.httpResponse + Constants.CRLF + "Allow: HEAD, OPTIONS" + Constants.CRLF +  "Content-Length: 0" + Constants.CRLF;;
        String returnedResponse = router.getResponse(request);
        assertEquals(expectedResponse, returnedResponse);
    }

    @Test
    void testRouterReturns200ForSecondAllowedRequestTypeToExistingResourceWithMultipleMethods() throws IOException {
        HTTPRequest request = new HTTPRequest("HEAD", "/head_request", "HTTP/1.1");
        String expectedResponse = "HTTP/1.1 " + StatusCodes.OK.httpResponse + Constants.CRLF + "Allow: HEAD, OPTIONS" + Constants.CRLF +  "Content-Length: 0" + Constants.CRLF;;
        String returnedResponse = router.getResponse(request);
        assertEquals(expectedResponse, returnedResponse);
    }

}
