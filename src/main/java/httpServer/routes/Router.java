package httpServer.routes;

import httpServer.http.HTTPMethods;
import httpServer.http.request.HTTPRequest;
import httpServer.http.response.HTTPResponse;
import httpServer.http.response.HTTPResponseBuilder;
import httpServer.http.StatusCodes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Router {

    Map<String, HTTPMethods[]> routes;
    HTTPResponseBuilder responseBuilder;

    public Router(Map routes, HTTPResponseBuilder responseBuilder) {
        this.routes = routes;
        this.responseBuilder = responseBuilder;
    }

    public HTTPResponse getResponse(HTTPRequest httpRequest) {
        responseBuilder = setStatusCodeLine(httpRequest, responseBuilder);
        responseBuilder = responseBuilder.setBody(httpRequest.body);

        return responseBuilder.build(httpRequest);
    }

    private HTTPResponseBuilder setStatusCodeLine(HTTPRequest httpRequest, HTTPResponseBuilder responseBuilder) {
        if (!isResourceValid(httpRequest)) {
            responseBuilder = responseBuilder.setStatusLine(StatusCodes.PAGE_NOT_FOUND, httpRequest);
        } else if (isResourceValid(httpRequest) && !isRouteInExistingResource(httpRequest)) {
            responseBuilder = responseBuilder.setStatusLine(StatusCodes.NOT_ACCEPTABLE, httpRequest);
        } else if (isResourceValid(httpRequest) && isRouteInExistingResource(httpRequest)) {
            responseBuilder = responseBuilder.setStatusLine(StatusCodes.OK, httpRequest);
        }

        return responseBuilder;
    }

//    private HTTPResponseBuilder setHeaders(HTTPRequest httpRequest, HTTPResponseBuilder responseBuilder) {
//        if (responseBuilder.statusLine.contains("405")) {
//
//        }
//    }

    private boolean isResourceValid(HTTPRequest httpRequest) {
        return routes.containsKey(httpRequest.resource);
    }

    private boolean isRouteInExistingResource(HTTPRequest httpRequest) {

        HTTPMethods[] allowedRoutes = routes.get(httpRequest.resource);
        List allowedRoutesAsList = Arrays.asList(allowedRoutes);

        for (int i = 0; i < allowedRoutesAsList.size(); i++) {
            String methodAllowed = String.valueOf(allowedRoutesAsList.get(i));
            if (httpRequest.method.equals(methodAllowed)) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

}
