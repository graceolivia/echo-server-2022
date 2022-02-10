package echoServer.routes;

import echoServer.CRLF;
import echoServer.http.HTTPMethods;
import echoServer.http.HTTPRequest;
import echoServer.http.StatusCode;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Router {

    Map<String, HTTPMethods[]> routes;

    public Router(Map routes) {
        this.routes = routes;
    }

    public StatusCode getResponse(HTTPRequest request) {
        if (routes.containsKey(request.resource)) {
            HTTPMethods[] allowedRoutes = routes.get(request.resource);
            List asList = Arrays.asList(allowedRoutes);
            String firstMethodAllowed = String.valueOf(asList.get(0));
            if (request.method.equals(firstMethodAllowed)) {
                return StatusCode.OK;
            }
        } return StatusCode.PAGE_NOT_FOUND;
    }

}
