package httpServer.http.request;

import java.util.HashMap;
import java.util.Map;

import static httpServer.http.Constants.crlf;

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
        this.httpVersionNumber = requestLine[2].replace(crlf, "");;
        return this;
    }

    public RequestBuilder buildHeaderLine(String line) {
        String[] requestLine = line.split(": ");
        String removedCLDR = requestLine[1].replace(crlf, "");
        this.headers.put(requestLine[0], removedCLDR);
        return this;
    }

    public RequestBuilder setBody(String line) {
        this.body = line;
        return this;
    }

    public String toString() {
        String requestLine = method + resource + httpVersionNumber + crlf;
        System.out.println(requestLine);
        headers.forEach((k, v) -> System.out.println("key: " + k + ", value: " + v));
        if (body != null) {
            System.out.println("body: " + body);
        }

        return requestLine;
    }

    public HTTPRequest build() {
        return new HTTPRequest(method, resource, httpVersionNumber, headers, body);
    }

    public int length() {
        return headers.size();
    }

    public boolean doesRequestContainBody() {
        return headers.containsKey("Content-Length");
    }

    public int getContentLengthInt() {
        String contentLengthString = headers.get("Content-Length");
        Integer length = Integer.valueOf(contentLengthString);
        return length;
    }

    public boolean hasEntireBodyBeenReadIn() {
        if (body == null) {
            return false;
        }
        int expectedBodyLength = getContentLengthInt();
        int currentBodyLength = body.length();
        System.out.println(expectedBodyLength);
        System.out.println(currentBodyLength);
        System.out.println(body);
        return (expectedBodyLength == currentBodyLength);

    }

}
