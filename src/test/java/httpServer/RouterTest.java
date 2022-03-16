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

import static httpServer.http.Constants.CRLF;
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
        String expectedResponse = "HTTP/1.1 " + StatusCodes.OK.httpResponse + CRLF +
                "Content-Length: 0" + CRLF +
                "Content-Type: text/plain" + CRLF + CRLF;
        String returnedResponse = router.getResponse(request).toString();
        assertEquals(expectedResponse, returnedResponse);
    }

    @Test
    void testRouterReturns404ForNonExistentResource() throws IOException {
        Map<String, String> headers = new HashMap();
        HTTPRequest request = new HTTPRequest("GET", "/anything_invalid", "HTTP/1.1", headers, null);
        String expectedResponse = "HTTP/1.1 " + StatusCodes.PAGE_NOT_FOUND.httpResponse + CRLF +
                "Content-Length: 0" + CRLF +
                "Content-Type: text/plain" + CRLF + CRLF;
        String returnedResponse = router.getResponse(request).toString();
        assertEquals(expectedResponse, returnedResponse);
    }

    @Test
    void testRouterReturns405ForInvalidMethodRequestToExistingResource() throws IOException {
        Map<String, String> headers = new HashMap();
        HTTPRequest request = new HTTPRequest("GET", "/head_request", "HTTP/1.1", headers, null);
        String expectedResponse = "HTTP/1.1 " + StatusCodes.NOT_ACCEPTABLE.httpResponse +  CRLF +
                "Content-Length: 0" + CRLF +
                "Allow: HEAD, OPTIONS" + CRLF +
                "Content-Type: text/plain" + CRLF + CRLF;;
        String returnedResponse = router.getResponse(request).toString();
        assertEquals(expectedResponse, returnedResponse);
    }

    @Test
    void testRouterReturns200ForFirstAllowedRequestTypeToExistingResourceWithMultipleMethods() throws IOException {
        Map<String, String> headers = new HashMap();
        HTTPRequest request = new HTTPRequest("OPTIONS", "/head_request", "HTTP/1.1", headers, null);
        String expectedResponse = "HTTP/1.1 " + StatusCodes.OK.httpResponse + CRLF +
                "Content-Length: 0" + CRLF +
                "Content-Type: text/plain" + CRLF + CRLF;
        String returnedResponse = router.getResponse(request).toString();
        assertEquals(expectedResponse, returnedResponse);
    }

    @Test
    void testRouterReturns200ForSecondAllowedRequestTypeToExistingResourceWithMultipleMethods() throws IOException {
        Map<String, String> headers = new HashMap();
        HTTPRequest request = new HTTPRequest("HEAD", "/head_request", "HTTP/1.1", headers, null);
        String expectedResponse = "HTTP/1.1 " + StatusCodes.OK.httpResponse + CRLF +
                "Content-Length: 0" + CRLF +
                "Content-Type: text/plain" + CRLF + CRLF;
        String returnedResponse = router.getResponse(request).toString();
        assertEquals(expectedResponse, returnedResponse);
    }

    @Test
    void testRouterReturns200AndBodyUponPostToEchoBody() throws IOException {
        Map<String, String> headers = new HashMap();
        HTTPRequest request = new HTTPRequest("POST", "/echo_body", "HTTP/1.1", headers, "test");
        String expectedResponse = "HTTP/1.1 " + StatusCodes.OK.httpResponse + CRLF +
                "Content-Length: 4" + CRLF +
                "Content-Type: text/plain" + CRLF + CRLF +
                "test";
        String returnedResponse = router.getResponse(request).toString();
        assertEquals(expectedResponse, returnedResponse);
    }

    @Test
    void testRouterReturns200AndBodyUponGetToJsonResponse() throws IOException {
        Map<String, String> headers = new HashMap();
        HTTPRequest request = new HTTPRequest("GET", "/json_response", "HTTP/1.1", headers, "");
        String expectedResponse = "HTTP/1.1 " + StatusCodes.OK.httpResponse + CRLF +
                "Content-Length: 33" + CRLF +
                "Content-Type: application/json;charset=utf-8" + CRLF + CRLF +
                "{\"key1\":\"value1\",\"key2\":\"value2\"}";
        String returnedResponse = router.getResponse(request).toString();
        assertEquals(expectedResponse, returnedResponse);
    }

    @Test
    void testRouterReturns200AndBodyUponGetToReactRoute() throws IOException {
        Map<String, String> headers = new HashMap();
        HTTPRequest request = new HTTPRequest("GET", "/react", "HTTP/1.1", headers, "");
        String expectedResponse = "HTTP/1.1 " + StatusCodes.OK.httpResponse + CRLF +
                "Content-Length: 33" + CRLF +
                "Content-Type: application/json;charset=utf-8" + CRLF + CRLF +
                "{\"people\": [{\"craft\": \"ISS\", \"name\": \"Mark Vande Hei\"}, {\"craft\": \"ISS\", \"name\": \"Pyotr Dubrov\"}, {\"craft\": \"ISS\", \"name\": \"Anton Shkaplerov\"}, {\"craft\": \"Shenzhou 13\", \"name\": \"Zhai Zhigang\"}, {\"craft\": \"Shenzhou 13\", \"name\": \"Wang Yaping\"}, {\"craft\": \"Shenzhou 13\", \"name\": \"Ye Guangfu\"}, {\"craft\": \"ISS\", \"name\": \"Raja Chari\"}, {\"craft\": \"ISS\", \"name\": \"Tom Marshburn\"}, {\"craft\": \"ISS\", \"name\": \"Kayla Barron\"}, {\"craft\": \"ISS\", \"name\": \"Matthias Maurer\"}], \"message\": \"success\", \"number\": 10}";
        String returnedResponse = router.getResponse(request).toString();
        assertEquals(expectedResponse, returnedResponse);
    }


}
