package httpServer.http;

import java.util.List;

import static httpServer.http.Constants.CRLF;

public class HTTPResponseWriter {


    public String buildResponse(StatusCodes statusCode, List methods, HTTPRequest request){
        String statusLine;
        String allowHeader = null;
        String contentLengthHeader;
        statusLine = makeStatusLine(statusCode, request);
        if (!statusCode.equals(StatusCodes.PAGE_NOT_FOUND)) {
            allowHeader = makeAllowHeader(methods);
        }
        contentLengthHeader = makeContentLengthHeader(0);
        HTTPResponse response = new HTTPResponse(statusLine, allowHeader, contentLengthHeader);
        return(response.getFullResponse());
    }

    private String makeStatusLine(StatusCodes statusCode, HTTPRequest request) {
        return (request.httpVersionNumber + " " + statusCode.httpResponse + CRLF);
    }

    private String makeAllowHeader(List methods) {
        StringBuilder allowLine = new StringBuilder();
        allowLine.append("Allow: ");
        for (int i = 0; i < methods.size(); i++) {
            allowLine.append(methods.get(i));
            if (i < (methods.size() - 1)) {
                allowLine.append(", ");
            }
        }
        allowLine.append(CRLF);

        return allowLine.toString();
    }

    private String makeContentLengthHeader(int length) {
    return("Content-Length: " + String.valueOf(length) + CRLF);
    }

}
