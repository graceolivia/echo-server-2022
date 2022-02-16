package httpServer.routes;

import httpServer.http.HTTPMethods;
import httpServer.http.request.HTTPRequest;
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



    public String getResponse(HTTPRequest httpRequest) {

        if (!isResourceValid(httpRequest)) {
            responseBuilder = responseBuilder.setStatusLine(StatusCodes.PAGE_NOT_FOUND, httpRequest);
        }
        else if (isResourceValid(httpRequest) && !isRouteInExistingResource(httpRequest)) {

        }



//
//        if (routes.containsKey(request.resource)) {
//            HTTPMethods[] allowedRoutes = routes.get(request.resource);
//            List allowedRoutesAsList = Arrays.asList(allowedRoutes);
//
//            for (int i = 0; i < allowedRoutesAsList.size(); i++) {
//                String methodAllowed = String.valueOf(allowedRoutesAsList.get(i));
//                if (request.method.equals(methodAllowed)) {
//                    return responseBuilder.buildResponse(StatusCodes.OK, allowedRoutesAsList, request);
//                }
//                else {
//                    continue;
//                }
//            }
//            return responseBuilder.buildResponse(StatusCodes.NOT_ACCEPTABLE, allowedRoutesAsList, request);
//        }
//        return responseBuilder.buildResponse(StatusCodes.PAGE_NOT_FOUND, null, request);
    }

    private boolean isResourceValid(HTTPRequest httpRequest) {
        return routes.containsKey(httpRequest.resource);
    }

    private boolean isRouteInExistingResource(HTTPRequest httpRequest) {

        HTTPMethods[] allowedRoutes = routes.get(httpRequest.resource);
        List allowedRoutesAsList = Arrays.asList(allowedRoutes);

        for (int i = 0; i < allowedRoutesAsList.size(); i++) {
            String methodAllowed = String.valueOf(allowedRoutesAsList.get(i));
            if (httpRequest.method.equals(methodAllowed)) {
                return responseBuilder.buildResponse(StatusCodes.OK, allowedRoutesAsList, httpRequest);
            }
            else {
                continue;
            }
        }
        return responseBuilder.buildResponse(StatusCodes.NOT_ACCEPTABLE, allowedRoutesAsList, httpRequest);
    }


}
