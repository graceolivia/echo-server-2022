package httpServer.http.response;

import httpServer.http.StatusCodes;
import httpServer.http.request.HTTPRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        StringBuilder allowedMethods = new StringBuilder();
        for (int i = 0; i < methods.size(); i++) {
            allowedMethods.append(methods.get(i));
            if (i < (methods.size() - 1)) {
                allowedMethods.append(", ");
            }
        }
        this.headers.put("Allow", allowedMethods.toString());
        return this;
    }

    public HTTPResponseBuilder setContentLengthHeader() {
        if (body == null) {
            this.headers.put("Content-Length", "0");
        } else {
            int bodyLength = body.length();
            this.headers.put("Content-Length", String.valueOf(bodyLength));
        }

        return this;
    }

    public HTTPResponseBuilder setContentTypeHeader() {
        this.headers.put("Content-Type", "text/plain");
        return this;
    }

    public HTTPResponse build(HTTPRequest request) {
        return new HTTPResponse(statusLine, body, headers);
    }

}
