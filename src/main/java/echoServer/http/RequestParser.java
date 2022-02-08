package echoServer.HTTP;

public class RequestParser {

    public static HTTPRequest RequestParser(String rawRequest) {
        HTTPRequest httpRequest = new HTTPRequest();
        String[] rows = rawRequest.split("\r\n");
        String[] firstRequestRow = rows[0].split(" ");

        httpRequest.requestType = firstRequestRow[0];
        httpRequest.route = firstRequestRow[1];
        httpRequest.httpType = firstRequestRow[2];
        return httpRequest;

    }


}
