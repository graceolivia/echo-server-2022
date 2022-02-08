package echoServer.http;

public enum StatusCode {
    PAGE_NOT_FOUND("HTTP/1.1 404 Not Found\r\nContent-Length: 0\r\n"),
    BAD_REQUEST("HTTP/1.1 400 Bad Request\r\nContent-Length: 0\r\n"),
    OK("HTTP/1.1 200 OK\r\nContent-Length: 0\r\n");

    public final String httpResponse;

    private StatusCode(String httpResponse) {
        this.httpResponse = httpResponse;
    }
}
