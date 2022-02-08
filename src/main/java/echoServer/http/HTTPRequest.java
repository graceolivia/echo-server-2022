package echoServer.HTTP;

public class HTTPRequest {

    String requestType;
    String route;
    String httpType;

    public HTTPRequest(String requestType, String route, String httpType) {
        this.requestType = requestType;
        this.route = route;
        this.httpType = httpType;
    }

}
