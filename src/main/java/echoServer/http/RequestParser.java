package echoServer.HTTP;

public class RequestParser {

    public static HTTPRequest parser(String rawRequest) {

        String[] rows = rawRequest.split("\r\n");
        String[] firstRequestRow = rows[0].split(" ");

        HTTPRequest httpRequest = new HTTPRequest(firstRequestRow[0], firstRequestRow[1], firstRequestRow[2]);
        return httpRequest;

    }


}
