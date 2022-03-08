package httpServer;

import static httpServer.http.Constants.CRLF;

public class RequestParserHelper {

    public boolean isRequestLine(String line) {
        if (line.contains(": ")) {
            return false;
        }
        return true;
    }

    public boolean atEndOfHeaders(StringBuilder requestScannedInSoFar) {
        return requestScannedInSoFar.toString().contains(CRLF + CRLF);
    }

}
