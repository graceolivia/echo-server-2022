package echoServer.routes;

import echoServer.http.HTTPMethods;
import echoServer.http.HTTPRequest;
import echoServer.http.StatusCode;

import java.util.Arrays;
import java.util.Map;

public class Router {

    Map<String, HTTPMethods[]> routes;

    public Router(Map routes) {
        this.routes = routes;
    }

    public StatusCode getResponse(HTTPRequest request) {
        if (routes.containsKey(request.resource)) {
            HTTPMethods[] allowedRoutes = routes.get(request.resource);
            System.err.println("Hi.");
            System.err.println(allowedRoutes);
            boolean blah = Arrays.asList(allowedRoutes).contains(request.method);
            System.err.println(blah);
            if (Arrays.asList(allowedRoutes).contains(request.method)) {
                System.err.println("Hi.");
                return StatusCode.OK;
            }
        } return StatusCode.PAGE_NOT_FOUND;
    }

}
