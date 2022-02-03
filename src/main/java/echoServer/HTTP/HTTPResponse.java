package echoServer.HTTP;

import static echoServer.HTTP.StatusCode.PAGE_NOT_FOUND;

public class HTTPResponse {

    // Int code = new Int();
    // use enum with values for status code type
    StatusCode statusCode;

    public HTTPResponse(StatusCode statusCode) {
        this.statusCode = statusCode;
    }


    public String buildResponse(StatusCode statusCode){
        switch(statusCode){
            case PAGE_NOT_FOUND:
                return "HTTP/1.0 404 Not Found\n";
        }
        return "Meow";
    }

}
