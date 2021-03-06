package httpServer.http.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HTTPRequest {

    public String method;
    public String resource;
    public String httpVersionNumber;
    public Map<String, String> headers;
    public String body;

    public HTTPRequest(String method, String resource, String httpVersionNumber, Map<String, String> headers, String body) {

        this.method = method;
        this.resource = resource;
        this.httpVersionNumber = httpVersionNumber;
        this.headers = headers;
        this.body = body;
    }

    public HTTPRequest() {
        this.headers = new HashMap<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        HTTPRequest httpRequest = (HTTPRequest) obj;

        return (httpRequest.method == this.method && httpRequest.resource == this.resource && httpRequest.httpVersionNumber == this.httpVersionNumber);
    }

}
