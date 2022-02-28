package httpServer.http.response;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static httpServer.http.Constants.CRLF;

public class HTTPResponse {

    String statusLine;
    public Map<String, String> headers = new HashMap<>();
    public String responseBody;

    public HTTPResponse(String statusLine, String responseBody, Map<String, String> headers) {
        this.statusLine = statusLine;
        this.responseBody = responseBody;
        this.headers = headers;
    }

    public String toString() {
        return statusLine + CRLF + convertHeadersToString() + CRLF + CRLF + bodyIfNotNull(responseBody);
    }

    private String convertHeadersToString() {
        return headers.entrySet()
                        .stream()
                        .<String>map(entry -> entry.getKey() + ": " + entry.getValue())
                        .collect(Collectors.joining(CRLF));
    }

    private StringBuilder appendIfNotNull(StringBuilder stringBuilder, String lineOrHeader) {
        if (!(lineOrHeader == null)) {
            stringBuilder.append(lineOrHeader);
            stringBuilder.append(CRLF);
        }
        return stringBuilder;
    }

    private String bodyIfNotNull(String body) {
        if (body == null) {
            return ("");
        }
        return body;

    }

    private StringBuilder appendHeaders(StringBuilder stringBuilder, Map<String, String> headerMap) {
        headerMap.forEach((k, v) -> stringBuilder.append(
                k + ": " + v + CRLF));
        return stringBuilder;
    }

}
