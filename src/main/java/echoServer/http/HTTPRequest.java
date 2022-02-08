package echoServer.HTTP;

public class HTTPRequest {

    String httpType;
    String code;
    String message;

    public HTTPRequest(String httpType, String code, String message) {
        this.httpType = httpType;
        this.code = code;
        this.message = message;
    }


}
