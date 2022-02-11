package httpServer.http;


public class HTTPResponse {

    String statusLine;
    String allowHeader;
    String contentLengthHeader;

    public HTTPResponse(String statusLine, String allowHeader, String contentLengthHeader) {
        this.statusLine = statusLine;
        this.allowHeader = allowHeader;
        this.contentLengthHeader = contentLengthHeader;
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

}
