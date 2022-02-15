package httpServer.http;

import java.util.HashMap;
import java.util.Map;

public class RequestBuilder {

    public static String method;
    public static String resource;
    public static String httpVersionNumber;
    public Map<String, String> headers = new HashMap<>();
    public String body;


    public RequestBuilder buildRequestLine(String line) {
        String[] requestLine = line.split(" ");
        this.method = requestLine[0];
        this.resource = requestLine[1];
        this.httpVersionNumber = requestLine[2];
        return this;
    }

    public RequestBuilder buildHeaderLine(String line) {
        String[] requestLine = line.split(": ");
        this.headers.put(requestLine[0], requestLine[1]);
        return this;
    }

    public RequestBuilder setBody(String line) {
        this.body = line;
        return this;
    }

    public HTTPRequest build() {
        return new HTTPRequest(method, resource, httpVersionNumber, headers, body);
    }

}
