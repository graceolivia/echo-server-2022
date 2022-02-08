package echoServer.HTTP;



public class RequestParser {
    String httpRequest;

    public RequestParser(String httpRequest) {
        this.httpRequest = httpRequest;
    }

    public static HTTPRequest parser(String httpRequest) {
       String[] httpRequestArray = httpRequest.split("\r\n");
       String[] headerFirstRow = httpRequestArray[0].split("\r\n");

       String requestType = headerFirstRow[0];
       String requestPath = headerFirstRow[1];
       String httpType = headerFirstRow[2];

       HTTPRequest response = new HTTPRequest(requestType, requestPath, httpType);

       return(response);

    }

}
