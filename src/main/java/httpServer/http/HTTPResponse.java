package httpServer.http;


public class HTTPResponse {

    String statusLine;
    String allowLine;
    String contentLengthLine;

    public HTTPResponse(String statusLine, String allowLine, String contentLengthLine, String fullHttpResponse) {
        this.statusLine = statusLine;
        this.allowLine = allowLine;
        this.contentLengthLine = contentLengthLine;
    }

}
