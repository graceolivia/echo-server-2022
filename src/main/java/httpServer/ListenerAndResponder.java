package httpServer;

import httpServer.http.HTTPRequest;

//import httpServer.http.RequestParser;

import httpServer.http.RequestBuilder;
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

        ClientReadable bufferedReader = clientReaderFactory.makeReader(clientSocket);


        HTTPRequest httpRequest;
        httpRequest = readAllLinesAndReturnHttpRequest(bufferedReader);
        if (httpRequest == null) {
            StatusCodes statusCodes = StatusCodes.BAD_REQUEST;
            String responseText = statusCodes.httpResponse;
            printServerResponse(responseText, clientSocket);
        }
        if (!httpRequest.equals("")) {

            //HTTPRequest request = RequestParser.parse(httpRequest);
            System.out.println(httpRequest);
            String httpResponse = router.getResponse(httpRequest);
            printServerResponse(httpResponse, clientSocket);
        }

    }

    private void printServerResponse(String message, Socket clientSocket) throws IOException {
            ClientWriteable printer = clientWriterFactory.makePrinter(clientSocket);
            System.out.println("sending this out");
            System.out.println(message);
            printer.println(message);

    }

    private HTTPRequest readAllLinesAndReturnHttpRequest(ClientReadable bufferedReader)  {

        String character;
        RequestBuilder requestBuilder = new RequestBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder currentLine = new StringBuilder();
        boolean readingInBody = false;
        boolean readEntireMessage = false;
        int contentLength = 0;

        try {
            while ((character = bufferedReader.read()) != null) {
                stringBuilder.append(character);
                currentLine.append(character);
                if (reachedEndOfHeaders(stringBuilder)) {
                    readingInBody = true;
                    if (!requestBuilder.doesRequestContainBody()) {
                        break;
                    }
                    if (currentLine.length() == requestBuilder.getContentLengthInt()) {
                        requestBuilder.setBody(currentLine.toString());
                        System.out.println(requestBuilder.toString());
                        break;
                    }
                }

              if (reachedEndOfLine(currentLine)) {
                  if (currentLine.toString() != CRLF + CRLF) {
                      String stringCurrentLine = currentLine.toString();
                      requestBuilder = discernLine(stringCurrentLine, requestBuilder, readingInBody);
                  }
                  currentLine.setLength(0);
              }

            }
            return requestBuilder.build();
        } catch (IOException e) {
            e.printStackTrace();
            return requestBuilder.build();
        }
    }


    private RequestBuilder discernLine(String line, RequestBuilder requestBuilder, boolean readingInBody) {
        if (!line.contains(": ") && readingInBody == false) {
            requestBuilder.buildRequestLine(line);
        } else if (readingInBody == false) {
            requestBuilder.buildHeaderLine(line);
        } else if (readingInBody == true) {
            requestBuilder.setBody(line);
        }
        return requestBuilder;
    }



    private boolean reachedEndOfLine(StringBuilder currentLine) {
        return currentLine.toString().contains(CRLF);
    }

    private boolean reachedEndOfHeaders(StringBuilder requestScannedInSoFar) {
        return requestScannedInSoFar.toString().contains(CRLF + CRLF);
    }




}
