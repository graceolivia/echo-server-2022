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
        List asList = null;
        if (routes.containsKey(request.resource)) {
            HTTPMethods[] allowedRoutes = routes.get(request.resource);
            asList = Arrays.asList(allowedRoutes);

            for (int i = 0; i < asList.size(); i++) {
                String methodAllowed = String.valueOf(asList.get(0));
                if (request.method.equals(methodAllowed)) {
                    return responseBuilder.buildResponse(StatusCodes.OK, asList);
                }
                else {
                    return responseBuilder.buildResponse(StatusCodes.NOT_ACCEPTABLE, asList);
                }
            }

        }
        return responseBuilder.buildResponse(StatusCodes.PAGE_NOT_FOUND, asList);
    }
}
