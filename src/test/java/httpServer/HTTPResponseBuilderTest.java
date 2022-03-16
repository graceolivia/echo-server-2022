package httpServer;

import httpServer.http.request.HTTPRequest;
import httpServer.http.request.HTTPRequestBuilder;
import httpServer.http.response.HTTPResponse;
import httpServer.http.response.HTTPResponseBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static httpServer.http.HTTPMethods.*;
import static httpServer.http.StatusCodes.OK;
import static httpServer.http.StatusCodes.PAGE_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class HTTPResponseBuilderTest {
    HTTPResponseBuilder responseBuilder;
    HTTPRequest request;

    @BeforeEach
    void setUp() throws IOException {
        request = new HTTPRequestBuilder()
                .buildRequestLine("GET / HTTP/1.1")
                .buildHeaderLine("Host: localhost:5000")
                .buildHeaderLine("Accept: */*")
                .build();
        responseBuilder = new HTTPResponseBuilder();
    }

    @Test
    void testResponseBuilderCorrectlyAssemblesStatusLine() throws IOException {
        HTTPResponse response = responseBuilder.setStatusLine(OK, request).build();
        assertEquals(response.statusLine, "HTTP/1.1 200 OK");
    }

    @Test
    void testResponseBuilderCorrectlySetsBody() throws IOException {
        HTTPResponse response = responseBuilder.setBody("body").build();
        assertEquals(response.body, "body");
    }

    @Test
    void testResponseBuilderCorrectlyMakesAllowHeader() throws IOException {
        HTTPResponse response = responseBuilder.makeAllowHeader(List.of(GET, OPTIONS)).build();
        assertEquals(response.headers.get("Allow"), "GET, OPTIONS");
    }

    @Test
    void testResponseBuilderCorrectlySetsContentLengthHeaderForEmptyBody() throws IOException {
        HTTPResponse response = responseBuilder
                .setContentLengthHeader()
                .build();
        assertEquals(response.headers.get("Content-Length"), "0");
    }

    @Test
    void testResponseBuilderCorrectlySetsContentLengthHeaderForBodyWithContent() throws IOException {
        HTTPResponse response = responseBuilder
                .setBody("hello")
                .setContentLengthHeader()
                .build();
        assertEquals(response.headers.get("Content-Length"), "5");
    }

    @Test
    void testResponseBuilderCorrectlySetsContentLengthHeaderForBodyWithEmptyString() throws IOException {
        HTTPResponse response = responseBuilder
                .setBody("")
                .setContentLengthHeader()
                .build();
        assertEquals(response.headers.get("Content-Length"), "0");
    }

    @Test
    void testResponseBuilderCorrectlySetsContentTypeHeader() throws IOException {
        HTTPResponse response = responseBuilder
                .setContentTypeHeader(request)
                .build();
        assertEquals(response.headers.get("Content-Type"), "text/plain");
    }

    @Test
    void testResponseBuilderCorrectlySetsContentTypeHeaderForJson() throws IOException {
        HTTPRequest jsonRequest = new HTTPRequestBuilder()
                .buildRequestLine("GET /json_response HTTP/1.1")
                .buildHeaderLine("Host: localhost:5000")
                .buildHeaderLine("Accept: */*")
                .build();
        HTTPResponse response = responseBuilder
                .setContentTypeHeader(jsonRequest)
                .build();
        assertEquals("application/json;charset=utf-8", response.headers.get("Content-Type"));
    }

    @Test
    void testResponseBuilderCorrectlyBuildsHTTPResponse() throws IOException {
        HTTPResponse response = responseBuilder
                .setStatusLine(PAGE_NOT_FOUND, request)
                .setBody("hello\nhello again")
                .makeAllowHeader(List.of(GET, OPTIONS, POST))
                .setContentLengthHeader()
                .setCorsHeader()
                .setContentTypeHeader(request)
                .build();
        assertEquals(response.statusLine, "HTTP/1.1 404 Not Found");
        assertEquals(response.headers.keySet(), Set.of("Access-Control-Allow-Origin", "Allow", "Content-Length", "Content-Type"));
        assertEquals(response.headers.get("Access-Control-Allow-Origin"), "*");
        assertEquals(response.headers.get("Content-Length"), "17");
        assertEquals(response.headers.get("Allow"), "GET, OPTIONS, POST");
        assertEquals(response.headers.get("Content-Type"), "text/plain");
        assertEquals(response.body, "hello\nhello again");
    }





}
