package httpServer.routes;

import httpServer.http.HTTPMethods;
import httpServer.http.request.HTTPRequest;
import httpServer.http.response.HTTPResponse;
import httpServer.http.response.HTTPResponseBuilder;
import httpServer.http.StatusCodes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Router {

    Map<String, HTTPMethods[]> routes;
    HTTPResponseBuilder responseBuilder;

    public Router(Map routes, HTTPResponseBuilder responseBuilder) {
        this.routes = routes;
        this.responseBuilder = responseBuilder;
    }

    public HTTPResponse getResponse(HTTPRequest httpRequest) {
        responseBuilder = setStatusCodeLine(httpRequest, responseBuilder);
        responseBuilder = setBody(httpRequest, responseBuilder);
        responseBuilder = setHeaders(httpRequest, responseBuilder);
        HTTPResponse httpResponse =  responseBuilder.build();
        return httpResponse;
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

    private HTTPResponseBuilder setHeaders(HTTPRequest httpRequest, HTTPResponseBuilder responseBuilder) {
        if (responseBuilder.statusLine.contains("405")) {
            responseBuilder = responseBuilder.makeAllowHeader(List.of(routes.get(httpRequest.resource)));
        }
        return responseBuilder
                .setContentLengthHeader()
                .setContentTypeHeader(httpRequest);
    }

    private HTTPResponseBuilder setBody(HTTPRequest httpRequest, HTTPResponseBuilder responseBuilder) {
        if (httpRequest.resource.equals("/echo_body")) {
            responseBuilder = responseBuilder.setBody(httpRequest.body);
            return responseBuilder;
        } else if (httpRequest.resource.equals("/json_response")) {
            responseBuilder = responseBuilder.setBody("{ key1: \'value1\', key2: \'value2\' }");
            return responseBuilder;
        } else {
            responseBuilder = responseBuilder.setBody("");
            return responseBuilder;
        }

    }

    private boolean isResourceValid(HTTPRequest httpRequest) {
        return routes.containsKey(httpRequest.resource);
    }

    private boolean isRouteInExistingResource(HTTPRequest httpRequest) {

        HTTPMethods[] allowedRoutes = routes.get(httpRequest.resource);
        List allowedRoutesAsList = Arrays.asList(allowedRoutes);

        return allowedRoutesAsList
                .stream()
                .map(e -> e.toString()).
                anyMatch(x -> x.equals(httpRequest.method));

    }
}
