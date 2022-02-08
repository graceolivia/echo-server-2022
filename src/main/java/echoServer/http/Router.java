package echoServer.HTTP;

import echoServer.http.StatusCode;

public class Router {

    public static echoServer.http.StatusCode router(HTTPRequest request) {
        if (request.requestType.equals("GET") && request.route.equals("/simple_get")){
            return StatusCode.OK;
        }
        else {
            return StatusCode.PAGE_NOT_FOUND;
        }
    }

}
