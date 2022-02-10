package httpServer.http;

public enum StatusCodes {
    PAGE_NOT_FOUND("HTTP/1.1 404 Not Found" + constants.CRLF + "Content-Length: 0" + constants.CRLF ),
    BAD_REQUEST("HTTP/1.1 400 Bad Request" + constants.CRLF + "Content-Length: 0" + constants.CRLF),
    OK("HTTP/1.1 200 OK" + constants.CRLF+ "Content-Length: 0" + constants.CRLF);

    public final String httpResponse;

    private StatusCodes(String httpResponse) {
        this.httpResponse = httpResponse;
    }
}
