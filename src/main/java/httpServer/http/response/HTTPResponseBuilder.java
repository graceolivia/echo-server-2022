package httpServer.http.response;

import httpServer.http.StatusCodes;
import httpServer.http.request.HTTPRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static httpServer.http.Constants.CRLF;

public class HTTPResponseBuilder {

    public String statusLine;
    public Map<String, String> headers = new HashMap<>();
    public String body;


    public HTTPResponseBuilder setStatusLine(StatusCodes statusCode, HTTPRequest request) {
       this.statusLine = request.httpVersionNumber + " " + statusCode.httpResponse;
       return this;
    }

    public HTTPResponseBuilder setHeaderLine(String header, String value) {
    return this;
    }

    public HTTPResponseBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    private HTTPResponseBuilder makeAllowHeader(List methods) {
        StringBuilder allowedMethods = new StringBuilder();
        for (int i = 0; i < methods.size(); i++) {
            allowedMethods.append(methods.get(i));
            if (i < (methods.size() - 1)) {
                allowedMethods.append(", ");
            }
        }
        this.headers.put("Allow: ", allowedMethods.toString());
        return this;
    }

    public HTTPResponse build(HTTPRequest request) {

        return new HTTPResponse(statusLine, "", makeContentLengthHeader(0), body);
    }
//
//    public String buildResponse(StatusCodes statusCode, List methods, HTTPRequest request){
//        String statusLine;
//        String allowHeader = null;
//        String contentLengthHeader;
//        String responseContent = null;
//        statusLine = setStatusLine(statusCode, request);
//        if (!statusCode.equals(StatusCodes.PAGE_NOT_FOUND)) {
//            allowHeader = makeAllowHeader(methods);
//        }
//        contentLengthHeader = makeContentLengthHeader(0);
//        HTTPResponse response = new HTTPResponse(statusLine, allowHeader, contentLengthHeader, responseContent);
//        return(response.responseString());
//    }




    private String makeContentLengthHeader(int length) {
        return("Content-Length: " + String.valueOf(length));
    }

}
