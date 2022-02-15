package httpServer.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class HTTPRequest {

    public String method;
    public String resource;
    public String httpVersionNumber;
    public Map<String, String> headers = new HashMap<>();
    public String body;

    public HTTPRequest(String method, String resource, String httpVersionNumber, Map<String, String> headers, String body) {
        this.method = method;
        this.resource = resource;
        this.httpVersionNumber = httpVersionNumber;
        this.headers = headers;
        this.body = body;
    }

    public HTTPRequest() {
        method = null;
        resource = null;
        httpVersionNumber = null;
        headers = null;
        body = null;
    }

    public int getContentLength() {
        System.out.println("Here come the headers:");
            int size = headers.size();
        System.out.println("boop");
            System.out.println(String.valueOf(size));
            Set<String> set = headers.keySet();
            System.out.println(String.join(",", set));
            String contentLengthString = headers.get("Content-Length");
            return Integer.valueOf(contentLengthString);

    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(obj == null || obj.getClass()!= this.getClass())
            return false;

        HTTPRequest httpRequest = (HTTPRequest) obj;

        return (httpRequest.method == this.method && httpRequest.resource == this.resource && httpRequest.httpVersionNumber == this.httpVersionNumber);
    }

}
