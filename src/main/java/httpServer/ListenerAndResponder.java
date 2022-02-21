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
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static httpServer.http.Constants.crlf;

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
        httpRequest = readAllLinesAndReturnHttpRequest(bufferedReader);
        if (httpRequest == null) {
            StatusCodes statusCodes = StatusCodes.BAD_REQUEST;
            String responseText = statusCodes.httpResponse;
            printServerResponse(responseText, clientSocket);
        }
        if (!httpRequest.equals("")) {
            HTTPResponse httpResponse = router.getResponse(httpRequest);
            String responseString = httpResponse.toString();
            System.err.println(responseString.length());
            printServerResponse(responseString, clientSocket);
        }

    }

    private void printServerResponse(String httpResponse, Socket clientSocket) throws IOException {
        ClientWriteable printer = clientWriterFactory.makePrinter(clientSocket);
        System.out.println(httpResponse);
        printer.print(httpResponse);
        printer.close();
    }

    private HTTPRequest readAllLinesAndReturnHttpRequest(ClientReadable bufferedReader)  {

        String character;
        RequestBuilder requestBuilder = new RequestBuilder();
        StringBuilder headers = new StringBuilder();
        StringBuilder body = new StringBuilder();

        try {
            while ((character = bufferedReader.read()) != null) {

                headers.append(character);
                if (checkIfReachedEndOfHeaders(headers)) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return requestBuilder.build();
        }

        if (checkIfHeadersIndicateARequestBody(headers)) {
            int bodyLength = getBodyLength(headers);
            try {
                while ((character = bufferedReader.read()) != null) {
                    if (java.util.Objects.equals(bodyLength, 0)) {
                        break;
                    }
                    body.append(character);
                    if (body.length() == bodyLength) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return requestBuilder.build();
            }
        }
        return turnStringBuilderIntoHttpRequest(headers, body, requestBuilder);
    }

    private HTTPRequest turnStringBuilderIntoHttpRequest(StringBuilder headers, StringBuilder body, RequestBuilder requestBuilder) {
        String[] headersArray = headers.toString().split(crlf);
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
        return requestScannedInSoFar.toString().contains(crlf + crlf);
    }

    private boolean checkIfHeadersIndicateARequestBody(StringBuilder headers) {
        String headersAsString = headers.toString();
        if (headersAsString.contains("Content-Length:")) {
            if (getBodyLength(headers) == 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    private int getBodyLength(StringBuilder headers) {
        int indexOfContentLengthHeader = headers.indexOf("Content-Length: ");
        int indexOfContentLength = indexOfContentLengthHeader + 16;
        String trimmedHeaders = headers.substring(indexOfContentLength);
        String lengthOfHeaders = trimmedHeaders.split(crlf)[0];
        return Integer.parseInt(lengthOfHeaders);
    }

}
