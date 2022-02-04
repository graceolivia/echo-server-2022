package echoServer.HTTP;

public class HTTPResponse {

    StatusCode statusCode;

    public HTTPResponse(StatusCode statusCode) {
        this.statusCode = statusCode;
    }


    public static String toString(StatusCode statusCode){
        switch(statusCode){
            case PAGE_NOT_FOUND:
                return "HTTP/1.1 404 Not Found\r\nContent-Length: 0\r\n";

        }
        return "This shouldn't happen.";
    }

}
