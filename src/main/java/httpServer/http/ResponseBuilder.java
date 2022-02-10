package httpServer.http;

import java.util.List;

public class ResponseBuilder {


    public String buildResponse(StatusCodes statusCode, List methods){
        StringBuilder responseStringBuilder = new StringBuilder();
        responseStringBuilder.append(statusCode.httpResponse);
        responseStringBuilder.append(Constants.CRLF);
        if (!statusCode.equals(StatusCodes.PAGE_NOT_FOUND)) {
            responseStringBuilder.append("Allow: ");
            for (int i = 0; i < methods.size(); i++) {
                responseStringBuilder.append(methods.get(i));
                if (i < (methods.size() - 1)) {
                    responseStringBuilder.append(", ");
                }
            }
            responseStringBuilder.append(Constants.CRLF);
        }
        responseStringBuilder.append("Content-Length: 0");
        responseStringBuilder.append(Constants.CRLF);
        return responseStringBuilder.toString();
    }

}
