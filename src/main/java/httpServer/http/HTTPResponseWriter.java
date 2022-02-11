package httpServer.http;

import java.util.List;

import static httpServer.http.Constants.CRLF;

public class HTTPResponseWriter {


    public String buildResponse(StatusCodes statusCode, List methods, HTTPRequest request){

        //  HTTPResponse response = new HTTPResponse();
        StringBuilder responseStringBuilder = new StringBuilder();
        responseStringBuilder.append(makeStatusLine(statusCode, request));
        if (!statusCode.equals(StatusCodes.PAGE_NOT_FOUND)) {
            responseStringBuilder.append(makeAllowHeader(methods));
        }
        responseStringBuilder.append(makeContentLengthHeader(0));
        return responseStringBuilder.toString();
    }

    private String makeStatusLine(StatusCodes statusCode, HTTPRequest request) {
        StringBuilder statusLine = new StringBuilder();
        statusLine.append(request.httpVersionNumber + " ");
        statusLine.append(statusCode.httpResponse);
        statusLine.append(CRLF);
        return statusLine.toString();
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
