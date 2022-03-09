package httpServer;

import static httpServer.http.Constants.CRLF;

public class RequestParser {

    public boolean isRequestLine(String line) {
        if (line.contains(": ")) {
            return false;
        }
        return true;
    }

    public boolean atEndOfHeaders(StringBuilder httpRequestStringBuilder) {
        return httpRequestStringBuilder.toString().contains(CRLF + CRLF);
    }

}
