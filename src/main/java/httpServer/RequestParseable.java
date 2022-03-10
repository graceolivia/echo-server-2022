package httpServer;

import httpServer.http.request.HTTPRequest;

public interface RequestParseable {

    public HTTPRequest storeHttpRequest(ClientReadable bufferedReader);

}
