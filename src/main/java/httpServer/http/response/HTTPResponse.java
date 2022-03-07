package httpServer.http.response;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static httpServer.http.Constants.CRLF;

public class HTTPResponse {

    public String statusLine;
    public Map<String, String> headers;
    public String body;

    public HTTPResponse(String statusLine, String body, Map<String, String> headers) {
        this.statusLine = statusLine;
        this.body = body;
        this.headers = headers;
    }

    public HTTPResponse() {
        this.headers = new HashMap<>();
    }

    public String toString() {
        return statusLine + CRLF + convertHeadersToString() + CRLF + CRLF + bodyIfNotNull(body);
    }

    private String convertHeadersToString() {
        return headers.entrySet()
                        .stream()
                        .<String>map(entry -> entry.getKey() + ": " + entry.getValue())
                        .collect(Collectors.joining(CRLF));
    }

    private String bodyIfNotNull(String body) {
        if (body == null) {
            return ("");
        }
        return body;
    }

}
