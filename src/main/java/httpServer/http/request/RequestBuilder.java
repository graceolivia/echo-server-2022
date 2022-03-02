package httpServer.http.request;

import java.util.HashMap;
import java.util.Map;

import static httpServer.http.Constants.CRLF;

public class RequestBuilder {

    public String method;
    public String resource;
    public String httpVersionNumber;
    public Map<String, String> headers = new HashMap<>();
    public String body;

    public RequestBuilder buildRequestLine(String line) {
        String[] requestLine = line.split(" ");
        this.method = requestLine[0];
        this.resource = requestLine[1];
        this.httpVersionNumber = requestLine[2].replace(CRLF, "");;
        return this;
    }

    public RequestBuilder buildHeaderLine(String line) {
        String[] requestLine = line.trim().split(": ");
        String removedCRLF = requestLine[1].replace(CRLF, "");
        this.headers.put(requestLine[0], removedCRLF);
        return this;
    }

    public RequestBuilder setBody(String line) {
        this.body = line;
        return this;
    }

    public String toString() {
        String requestLine = method + resource + httpVersionNumber + CRLF;
        System.out.println(requestLine);
        headers.forEach((k, v) -> System.out.println(k + ": " + v));
        if (body != null) {
            System.out.println("body: " + body);
        }

        return requestLine;
    }

    public HTTPRequest build() {
        return new HTTPRequest(method, resource, httpVersionNumber, headers, body);
    }

    public int getContentLengthInt() {
        String contentLengthString = headers.get("Content-Length");
        if (contentLengthString == null) {
            return 0;
        }
        Integer length = Integer.valueOf(contentLengthString);
        return length;
    }


}
