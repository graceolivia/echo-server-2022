package httpServer.http;

public enum StatusCodes {
    PAGE_NOT_FOUND("404 Not Found"),
    BAD_REQUEST("400 Bad Request"),
    OK("200 OK"),
    NOT_ACCEPTABLE("405 Not Acceptable");

    public final String httpResponse;

    private StatusCodes(String httpResponse) {
        this.httpResponse = httpResponse;
    }
}
