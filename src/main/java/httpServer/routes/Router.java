package httpServer.routes;

import httpServer.http.HTTPMethods;
import httpServer.http.HTTPRequest;
import httpServer.http.ResponseBuilder;
import httpServer.http.StatusCodes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Router {

    Map<String, HTTPMethods[]> routes;
    ResponseBuilder responseBuilder;

    public Router(Map routes, ResponseBuilder responseBuilder) {
        this.routes = routes;
        this.responseBuilder = responseBuilder;
    }

    public String getResponse(HTTPRequest request) {
        List allowedRoutesAsList = null;
        if (routes.containsKey(request.resource)) {
            HTTPMethods[] allowedRoutes = routes.get(request.resource);
            allowedRoutesAsList = Arrays.asList(allowedRoutes);

            for (int i = 0; i < allowedRoutesAsList.size(); i++) {
                String methodAllowed = String.valueOf(allowedRoutesAsList.get(0));
                if (request.method.equals(methodAllowed)) {
                    return responseBuilder.buildResponse(StatusCodes.OK, allowedRoutesAsList, request);
                }
                else {
                    return responseBuilder.buildResponse(StatusCodes.NOT_ACCEPTABLE, allowedRoutesAsList, request);
                }
            }

        }
        return responseBuilder.buildResponse(StatusCodes.PAGE_NOT_FOUND, allowedRoutesAsList, request);
    }
}
