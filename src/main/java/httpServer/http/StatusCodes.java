package httpServer.http;

public enum StatusCodes {
    PAGE_NOT_FOUND("HTTP/1.1 404 Not Found"),
    BAD_REQUEST("HTTP/1.1 400 Bad Request"),
    OK("HTTP/1.1 200 OK"),
    NOT_ACCEPTABLE("HTTP/1.1 405 Not Acceptable");

    public final String httpResponse;

    private StatusCodes(String httpResponse) {
        this.httpResponse = httpResponse;
    }
}
