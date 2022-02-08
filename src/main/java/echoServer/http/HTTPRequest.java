package echoServer.http;

public class HTTPRequest {

    public String method;
    public String resource;
    public String httpVersionNumber;

    public HTTPRequest(String method, String resource, String httpVersionNumber) {
        this.method = method;
        this.resource = resource;
        this.httpVersionNumber = httpVersionNumber;
    }

}
