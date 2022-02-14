package httpServer.http;


public class HTTPResponse {

    String statusLine;
    String allowHeader;
    String contentLengthHeader;
    String responseBody;

    public HTTPResponse(String statusLine, String allowHeader, String contentLengthHeader, String responseBody) {
        this.statusLine = statusLine;
        this.allowHeader = allowHeader;
        this.contentLengthHeader = contentLengthHeader;
        this.responseBody = responseBody;
    }

    public String getFullResponse() {
        StringBuilder fullResponse = new StringBuilder();
        appendIfNotNull(fullResponse, statusLine);
        appendIfNotNull(fullResponse, allowHeader);
        appendIfNotNull(fullResponse, contentLengthHeader);

        return fullResponse.toString();
    }

    private StringBuilder appendIfNotNull(StringBuilder stringBuilder, String lineOrHeader) {
        if (!(lineOrHeader == null)) {
            stringBuilder.append(lineOrHeader);
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
