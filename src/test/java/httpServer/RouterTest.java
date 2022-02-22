package httpServer;

import httpServer.http.Constants;
import httpServer.http.request.HTTPRequest;
import httpServer.http.response.HTTPResponseBuilder;
import httpServer.routes.Router;
import httpServer.http.StatusCodes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouterTest {

    HTTPResponseBuilder responseBuilder;
    Router router;

    @BeforeEach
    void setUp() throws IOException {
        responseBuilder = new HTTPResponseBuilder();
        router = new Router(HttpServer.getRoutes(), responseBuilder);
    }
    @Test
    void testRouterReturns200ForGetRequestToResourceWithGetAllowed() throws IOException {
        Map<String, String> headers = new HashMap();
        HTTPRequest request = new HTTPRequest("GET", "/simple_get", "HTTP/1.1", headers, null);
        String expectedResponse = "HTTP/1.1 " + StatusCodes.OK.httpResponse + Constants.crlf + "Content-Length: 0" + Constants.crlf + "Content-Type: text/plain" + Constants.crlf + Constants.crlf;
        String returnedResponse = router.getResponse(request).toString();
        assertEquals(expectedResponse, returnedResponse);
    }

    @Test
    void testRouterReturns404ForNonExistentResource() throws IOException {
        Map<String, String> headers = new HashMap();
        HTTPRequest request = new HTTPRequest("GET", "/anything_invalid", "HTTP/1.1", headers, null);
        String expectedResponse = "HTTP/1.1 " + StatusCodes.PAGE_NOT_FOUND.httpResponse + Constants.crlf + "Content-Length: 0" + Constants.crlf + "Content-Type: text/plain" + Constants.crlf + Constants.crlf;
        String returnedResponse = router.getResponse(request).toString();
        assertEquals(expectedResponse, returnedResponse);
    }

    @Test
    void testRouterReturns405ForInvalidMethodRequestToExistingResource() throws IOException {
        Map<String, String> headers = new HashMap();
        HTTPRequest request = new HTTPRequest("GET", "/head_request", "HTTP/1.1", headers, null);
        String expectedResponse = "HTTP/1.1 " + StatusCodes.NOT_ACCEPTABLE.httpResponse +  Constants.crlf +  "Content-Length: 0" + Constants.crlf + "Allow: HEAD, OPTIONS" + Constants.crlf + "Content-Type: text/plain" + Constants.crlf + Constants.crlf;;
        String returnedResponse = router.getResponse(request).toString();
        assertEquals(expectedResponse, returnedResponse);
    }

    @Test
    void testRouterReturns200ForFirstAllowedRequestTypeToExistingResourceWithMultipleMethods() throws IOException {
        Map<String, String> headers = new HashMap();
        HTTPRequest request = new HTTPRequest("OPTIONS", "/head_request", "HTTP/1.1", headers, null);
        String expectedResponse = "HTTP/1.1 " + StatusCodes.OK.httpResponse + Constants.crlf +  "Content-Length: 0" + Constants.crlf + "Content-Type: text/plain" + Constants.crlf + Constants.crlf;
        String returnedResponse = router.getResponse(request).toString();
        assertEquals(expectedResponse, returnedResponse);
    }

    @Test
    void testRouterReturns200ForSecondAllowedRequestTypeToExistingResourceWithMultipleMethods() throws IOException {
        Map<String, String> headers = new HashMap();
        HTTPRequest request = new HTTPRequest("HEAD", "/head_request", "HTTP/1.1", headers, null);
        String expectedResponse = "HTTP/1.1 " + StatusCodes.OK.httpResponse + Constants.crlf +  "Content-Length: 0" + Constants.crlf + "Content-Type: text/plain" + Constants.crlf + Constants.crlf;
        String returnedResponse = router.getResponse(request).toString();
        assertEquals(expectedResponse, returnedResponse);
    }

    @Test
    void testRouterReturns200AndBodyUponPostToEchoBody() throws IOException {
        Map<String, String> headers = new HashMap();
        HTTPRequest request = new HTTPRequest("POST", "/echo_body", "HTTP/1.1", headers, "test");
        String expectedResponse = "HTTP/1.1 " + StatusCodes.OK.httpResponse + Constants.crlf +  "Content-Length: 4" + Constants.crlf + "Content-Type: text/plain" + Constants.crlf + Constants.crlf + "test";
        String returnedResponse = router.getResponse(request).toString();
        assertEquals(expectedResponse, returnedResponse);
    }

}
