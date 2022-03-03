package httpServer;

import httpServer.http.request.HTTPRequest;
import httpServer.http.request.HTTPRequestBuilder;
import httpServer.http.response.HTTPResponse;
import httpServer.routes.Router;
import httpServer.http.StatusCodes;
import httpServer.outputManagement.ClientWriteableFactory;
import httpServer.outputManagement.ClientWriteable;
import httpServer.socketManagement.ServerSocketInterface;

import java.io.IOException;
import java.net.Socket;
import static httpServer.http.Constants.CRLF;

public class ListenerAndResponder implements ListenAndRespondable {
    ServerSocketInterface serverSocket;
    ClientReadableFactory clientReaderFactory;
    ClientWriteableFactory clientWriterFactory;
    Router router;

    public ListenerAndResponder(ServerSocketInterface serverSocket, ClientReadableFactory clientReaderFactory, ClientWriteableFactory clientWriterFactory, Router router) throws IOException {
        this.serverSocket = serverSocket;
        this.clientWriterFactory = clientWriterFactory;
        this.clientReaderFactory = clientReaderFactory;
        this.router = router;
    }

    public Socket startServer() throws IOException {
        int port = 5000;
        serverSocket.bind(port);
        System.err.println("Started server on port " + port);
        return keepListening();
    }

    public Socket keepListening() throws IOException {
        Socket clientSocket = serverSocket.accept();
        System.err.println("Connected to client.");
        return clientSocket;
    }

    public void readClientInput(Socket clientSocket) throws IOException {
        System.err.println("Starting to read");
        ClientReadable bufferedReader = clientReaderFactory.makeReader(clientSocket);
        HTTPRequest httpRequest;
        httpRequest = readHeaderAndBodyAndReturnHttpRequest(bufferedReader);
        if (httpRequest == null) {
            StatusCodes statusCodes = StatusCodes.BAD_REQUEST;
            String responseText = statusCodes.httpResponse;
            printServerResponse(responseText, clientSocket);
        }
        if (!httpRequest.equals("")) {
            HTTPResponse httpResponse = router.getResponse(httpRequest);
            String responseString = httpResponse.toString();
            printServerResponse(responseString, clientSocket);
        }

    }

    private void printServerResponse(String httpResponse, Socket clientSocket) throws IOException {
        ClientWriteable printer = clientWriterFactory.makePrinter(clientSocket);
        System.out.println(httpResponse);
        printer.print(httpResponse);
        printer.close();
    }

    private HTTPRequest readHeaderAndBodyAndReturnHttpRequest(ClientReadable bufferedReader)  {

        HTTPRequestBuilder requestBuilder = new HTTPRequestBuilder();
        requestBuilder = readHeaders(bufferedReader, requestBuilder);
        int bodyLength = requestBuilder.getContentLengthInt();
        StringBuilder body = readBody(bufferedReader, bodyLength);
        return returnHttpRequest(body, requestBuilder);

    }

    private HTTPRequestBuilder readHeaders(ClientReadable bufferedReader, HTTPRequestBuilder requestBuilder) {

        String character;
        StringBuilder headers = new StringBuilder();

        try {
            while ((character = bufferedReader.read()) != null) {

                headers.append(character);
                if (checkIfReachedEndOfHeaders(headers)) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] headersArray = headers.toString().split(CRLF);
        for (String header: headersArray) {
            requestBuilder = discernHeaderTypeAndAddToRequestBuilder(header, requestBuilder);
        }
        return requestBuilder;

    }

    private StringBuilder readBody(ClientReadable bufferedReader, int bodyLength) {
        String character;
        StringBuilder body = new StringBuilder();
        if (bodyLength == 0) {
            return body;
        }
        try {
            while ((character = bufferedReader.read()) != null) {
                body.append(character);
                if (body.length() == bodyLength) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    private HTTPRequest returnHttpRequest(StringBuilder body, HTTPRequestBuilder requestBuilder) {
        if (body.length() != 0) {
            requestBuilder.setBody(body.toString());
        }
        return requestBuilder.build();
    }

    private HTTPRequestBuilder discernHeaderTypeAndAddToRequestBuilder(String line, HTTPRequestBuilder HTTPRequestBuilder) {
        if (line.contains(": ")) {
            HTTPRequestBuilder.buildHeaderLine(line);
        } else {
            HTTPRequestBuilder.buildRequestLine(line);
        }
        return HTTPRequestBuilder;
    }

    private boolean checkIfReachedEndOfHeaders(StringBuilder requestScannedInSoFar) {
        return requestScannedInSoFar.toString().contains(CRLF + CRLF);
    }

}
