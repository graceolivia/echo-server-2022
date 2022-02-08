package echoServer.HTTP;

import echoServer.http.StatusCode;

public class Router {

    public static echoServer.http.StatusCode router(HTTPRequest request) {
        if (request.requestType == "GET" && request.route == "/simple_get"){
            return StatusCode.OK;
        }
        else {
            return StatusCode.PAGE_NOT_FOUND;
        }
    }

}
