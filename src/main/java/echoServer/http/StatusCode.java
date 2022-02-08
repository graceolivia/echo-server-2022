package echoServer.http;

import echoServer.CRLF;

public enum StatusCode {
    PAGE_NOT_FOUND("HTTP/1.1 404 Not Found" + CRLF.CRLF + "Content-Length: 0" + CRLF.CRLF ),
    BAD_REQUEST("HTTP/1.1 400 Bad Request" + CRLF.CRLF + "Content-Length: 0" + CRLF.CRLF),
    OK("HTTP/1.1 200 OK" + CRLF.CRLF+ "Content-Length: 0" + CRLF.CRLF);

    public final String httpResponse;

    private StatusCode(String httpResponse) {
        this.httpResponse = httpResponse;
    }
}