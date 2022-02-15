package httpServer.http;

import java.util.HashMap;
import java.util.Map;

public class HTTPRequest {

    public String method;
    public String resource;
    public String httpVersionNumber;
    public Map<String, String> headers = new HashMap<>();

    public HTTPRequest(String method, String resource, String httpVersionNumber, String[] headers;) {
        this.method = method;
        this.resource = resource;
        this.httpVersionNumber = httpVersionNumber;
        this.headers = headers;
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
