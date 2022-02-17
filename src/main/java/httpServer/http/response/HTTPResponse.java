package httpServer.http.response;


import httpServer.http.Constants;

import java.util.HashMap;
import java.util.Map;

public class HTTPResponse {

    String statusLine;
    String allowHeader;
    String contentLengthHeader;
    public Map<String, String> headers = new HashMap<>();
    String responseBody;

    public HTTPResponse(String statusLine, String allowHeader, String contentLengthHeader, String responseBody) {
        this.statusLine = statusLine;
        this.allowHeader = allowHeader;
        this.contentLengthHeader = contentLengthHeader;
        this.responseBody = responseBody;
    }

    public HTTPResponse() {
        statusLine = allowHeader = contentLengthHeader = responseBody = null;

    }

    public String responseString() {
        StringBuilder fullResponse = new StringBuilder();
        appendIfNotNull(fullResponse, statusLine);
        appendIfNotNull(fullResponse, allowHeader);
        appendIfNotNull(fullResponse, contentLengthHeader);
        appendBodyIfNotNull(fullResponse, responseBody);
        return fullResponse.toString();
    }

    private StringBuilder appendIfNotNull(StringBuilder stringBuilder, String lineOrHeader) {
        if (!(lineOrHeader == null)) {
            stringBuilder.append(lineOrHeader);
            stringBuilder.append(Constants.CRLF);
        }
        return stringBuilder;
    }

    private StringBuilder appendBodyIfNotNull(StringBuilder stringBuilder, String body) {
        if (!(body == null)) {
            stringBuilder.append(Constants.CRLF);
            stringBuilder.append(body);
        }
        return stringBuilder;
    }


}
