package httpServer.http.response;

import httpServer.http.StatusCodes;
import httpServer.http.request.HTTPRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HTTPResponseBuilder {

    public String statusLine;
    public Map<String, String> headers = new HashMap<>();
    public String body;

    public HTTPResponseBuilder setStatusLine(StatusCodes statusCode, HTTPRequest request) {
        this.statusLine = request.httpVersionNumber + " " + statusCode.httpResponse;
        return this;
    }

    public HTTPResponseBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public HTTPResponseBuilder makeAllowHeader(List methods) {
        String stringOfMethods = (String) methods.stream().map(Object::toString).collect(Collectors.joining(", "));
        this.headers.put("Allow", stringOfMethods);
        return this;
    }

    public HTTPResponseBuilder setContentLengthHeader() {
        if (body == null) {
            this.headers.put("Content-Length", "0");
        } else {
            this.headers.put("Content-Length", String.valueOf(body.length()));
        }
        return this;
    }

    public HTTPResponseBuilder setContentTypeHeader(HTTPRequest request) {
        if (request.resource.equals("/json_response") || request.resource.equals("/react")) {
            this.headers.put("Content-Type", "application/json;charset=utf-8");
        } else {
            this.headers.put("Content-Type", "text/plain");
        }
        return this;
    }

    public HTTPResponseBuilder setCorsHeader() {
        this.headers.put("Access-Control-Allow-Origin", "*");
        return this;
    }

    public HTTPResponse build() {
        return new HTTPResponse(statusLine, body, headers);
    }

}
