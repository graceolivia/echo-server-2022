package httpServer;

import httpServer.http.request.HTTPRequest;

import java.util.HashMap;
import java.util.Map;

public class MockRequestParser implements RequestParseable {

    boolean storeHttpRequestWasCalled = false;

    @Override
    public HTTPRequest storeHttpRequest(ClientReadable bufferedReader){
        storeHttpRequestWasCalled = true;
        Map<String, String> headers = new HashMap<>();
        return new HTTPRequest("GET", "/", "HTTP/1.1", headers, "");
    };

}
