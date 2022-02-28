package httpServer;

import httpServer.http.request.HTTPRequest;
import httpServer.http.request.RequestBuilder;
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

        RequestBuilder requestBuilder = new RequestBuilder();
        StringBuilder headers = readHeaders(bufferedReader);
        int bodyLength = getBodyLength(headers);
        StringBuilder body = readBody(bufferedReader, bodyLength);
        return returnHttpRequest(headers, body, requestBuilder);
    }

    private StringBuilder readHeaders(ClientReadable bufferedReader) {

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

        return headers;

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

    private HTTPRequest returnHttpRequest(StringBuilder headers, StringBuilder body, RequestBuilder requestBuilder) {
        String[] headersArray = headers.toString().split(CRLF);
        for (String header: headersArray) {
            requestBuilder = discernHeaderTypeAndAddToRequestBuilder(header, requestBuilder);
        }
        if (body.length() != 0) {
            requestBuilder.setBody(body.toString());
        }
        return requestBuilder.build();
    }

    private RequestBuilder discernHeaderTypeAndAddToRequestBuilder(String line, RequestBuilder requestBuilder) {
        if (!line.contains(": ")) {
            requestBuilder.buildRequestLine(line);
        } else {
            requestBuilder.buildHeaderLine(line);

        }
        return requestBuilder;
    }

    private boolean checkIfReachedEndOfHeaders(StringBuilder requestScannedInSoFar) {
        return requestScannedInSoFar.toString().contains(CRLF + CRLF);
    }

    private int getBodyLength(StringBuilder headers) {
        int indexOfContentLengthHeader = headers.indexOf("Content-Length: ");
        if (indexOfContentLengthHeader == -1) {
            return 0;
        }
        int indexOfContentLength = indexOfContentLengthHeader + 16;
        String trimmedHeaders = headers.substring(indexOfContentLength);
        String lengthOfHeaders = trimmedHeaders.split(CRLF)[0];
        return Integer.parseInt(lengthOfHeaders);
    }

}
