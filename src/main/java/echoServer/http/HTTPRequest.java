package echoServer.HTTP;

public class HTTPRequest {

    public String requestType;
    public String route;
    public String httpType;

    public HTTPRequest(String requestType, String route, String httpType) {
        this.requestType = requestType;
        this.route = route;
        this.httpType = httpType;
    }

}
