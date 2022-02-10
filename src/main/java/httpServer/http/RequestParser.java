package httpServer.http;

public class RequestParser {

    public static HTTPRequest parse(String rawRequest) {

        String[] lines = rawRequest.split(constants.CRLF);
        String[] requestLine = lines[0].split(" ");

        HTTPRequest httpRequest = new HTTPRequest(requestLine[0], requestLine[1], requestLine[2]);
        return httpRequest;
    }
}
