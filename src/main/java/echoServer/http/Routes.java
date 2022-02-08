package echoServer.HTTP;

import echoServer.http.StatusCode;

public class Routes {

    public static echoServer.http.StatusCode router(HTTPRequest httpResponse) {

        if (httpResponse.requestPath == "/simple_get" && httpResponse.requestType == "GET" ) {
            return StatusCode.OK;
        }

        else {
            return StatusCode.PAGE_NOT_FOUND;
        }

    }

}
