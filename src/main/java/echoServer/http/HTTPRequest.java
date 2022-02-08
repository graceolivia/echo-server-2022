package echoServer.HTTP;

public class HTTPRequest {
    String requestType;
    String requestPath;
    String httpType;

    public HTTPRequest(String requestType, String requestPath, String httpType) {
        this.requestType = requestType;
        this.requestPath = requestPath;
        this.httpType = httpType;
    }
}