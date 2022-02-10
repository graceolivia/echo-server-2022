package httpServer.routes;

import httpServer.http.HTTPMethods;
import httpServer.http.HTTPRequest;
import httpServer.http.StatusCodes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Router {

    Map<String, HTTPMethods[]> routes;

    public Router(Map routes) {
        this.routes = routes;
    }

    public StatusCodes getResponse(HTTPRequest request) {
        if (routes.containsKey(request.resource)) {
            HTTPMethods[] allowedRoutes = routes.get(request.resource);
            List asList = Arrays.asList(allowedRoutes);

            for (int i = 0; i < asList.size(); i++) {
                String firstMethodAllowed = String.valueOf(asList.get(0));
                if (request.method.equals(firstMethodAllowed)) {
                    return StatusCodes.OK;
                }
            }

        } return StatusCodes.PAGE_NOT_FOUND;
    }
}
