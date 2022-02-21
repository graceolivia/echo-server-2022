package httpServer.http.response;

import httpServer.http.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static httpServer.http.Constants.crlf;

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
        return statusLine + crlf + convertHeadersToString() + crlf + crlf + bodyIfNotNull(responseBody);
    }

    private String convertHeadersToString() {
        return headers.entrySet()
                        .stream()
                        .<String>map(entry -> entry.getKey() + ": " + entry.getValue())
                        .collect(Collectors.joining(crlf));
    }

    private StringBuilder appendIfNotNull(StringBuilder stringBuilder, String lineOrHeader) {
        if (!(lineOrHeader == null)) {
            stringBuilder.append(lineOrHeader);
            stringBuilder.append(crlf);
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
                k + ": " + v + crlf));
        return stringBuilder;
    }

}
