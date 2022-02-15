package httpServer;

import httpServer.http.HTTPRequest;
//import httpServer.http.RequestParser;
import httpServer.routes.Router;
import httpServer.http.StatusCodes;
import httpServer.outputManagement.ClientWriteableFactory;
import httpServer.outputManagement.ClientWriteable;
import httpServer.socketManagement.ServerSocketInterface;

import java.io.IOException;
import java.net.Socket;
import java.util.AbstractMap;

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
        httpRequest = readAllLines(bufferedReader);
        if (httpRequest == null) {
            StatusCodes statusCodes = StatusCodes.BAD_REQUEST;
            String responseText = statusCodes.httpResponse;
            printServerResponse(responseText, clientSocket);
        }
        if (!httpRequest.equals("")) {
            // HTTPRequest request = RequestParser.parse(httpRequest);
            // System.out.println(httpRequest);
            String httpResponse = router.getResponse(httpRequest);
            printServerResponse(httpResponse, clientSocket);
        }

    }

    private void printServerResponse(String message, Socket clientSocket) throws IOException {
            ClientWriteable printer = clientWriterFactory.makePrinter(clientSocket);
            System.out.println(message);
            printer.println(message);

    }

    private HTTPRequest readAllLines(ClientReadable bufferedReader)  {
        HTTPRequest httpRequest = new HTTPRequest(null, null, null, null, null);
        String character;
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder currentLine = new StringBuilder();
        boolean readingInBody = false;
        int contentLength = 0;

        try {
            while ((character = bufferedReader.read()) != null)
            {

                //getting headers
                stringBuilder.append(character);
                // Get a current line going
                currentLine.append(character);
                if (!readingInBody) {
                  boolean haveWeReachedTheEndOfTheLine = reachedEndOfLine(currentLine);
                  if (haveWeReachedTheEndOfTheLine) {
                      String stringCurrentLine = currentLine.toString();
                      httpRequest = discernHeader(stringCurrentLine, httpRequest);
                      currentLine.setLength(0);
                  }
                  else {
                      continue;
                  }

                }
                if (reachedEndOfHeaders(stringBuilder)) {
                    //System.out.println("4");
                    System.out.println("reached end of headers");
                    // get the content length
                    contentLength = httpRequest.getContentLength();
                    // switch to readingInBody
                    readingInBody = true;


            }
                // check if body has been read in and if the content length of the body has been reached
                if (readingInBody = true && contentLength == currentLine.length()) {
                   // System.out.println("1");
                    break;
                }
                // if body is ready to be added to HTTP request
                else if (readingInBody = true && reachedEndOfLine(currentLine)) {
                   // System.out.println("2");
                    httpRequest = requestBodyAssembler(currentLine, httpRequest);
                }
                // Check if line contains the CLDR (thus reaching end of line)
                // and also make sure it isn't the double CLDR that indicates the beginning of the body
//                else if (reachedEndOfLine(currentLine) && !reachedEndOfHeaders(stringBuilder)) {
//                    //System.out.println("3");
//                    String stringCurrentLine = currentLine.toString();
//                    httpRequest = discernHeader(stringCurrentLine, httpRequest);
//                }
                // if end of headers has been reached

               // System.out.println(stringBuilder.toString());
               // System.out.println(detectBodyLength(stringBuilder));
            }


            return httpRequest;
        } catch (IOException e) {
            e.printStackTrace();
            return httpRequest;
        }
    }


    private HTTPRequest discernHeader(String line, HTTPRequest httpRequest) {
    // this should only take in headers, not blank strings or body
        if (line.contains(": ")) {
            System.out.println("FIRSTLINE");
            addHeaderToObject(line, httpRequest);
        } else {
            System.out.println("SECONDLINE");
            String[] requestLine = line.split(" ");
            httpRequest.method = requestLine[0];
            httpRequest.resource = requestLine[1];
            httpRequest.httpVersionNumber = requestLine[2];
        }
        return httpRequest;
    }

    private boolean detectBodyLength(StringBuilder stringBuilder){
        String string = stringBuilder.toString();
        boolean containsContentLength = string.contains("Content-Length: ");
        return containsContentLength;
    }

    private HTTPRequest addHeaderToObject(String header, HTTPRequest httpRequest) {
        String[] headerComponents = header.split(":");
        httpRequest.headers.put(headerComponents[0], headerComponents[1]);
        System.out.println(headerComponents[0] + headerComponents[1]);
        return httpRequest;
    }

    private boolean reachedEndOfLine(StringBuilder currentLine) {
        return currentLine.toString().contains(CRLF);
    }

    private boolean reachedEndOfHeaders(StringBuilder requestScannedInSoFar) {
        return requestScannedInSoFar.toString().contains(CRLF + CRLF);
    }

    private HTTPRequest requestBodyAssembler(StringBuilder contentStringBuilder, HTTPRequest httpRequest) {
        httpRequest.body = contentStringBuilder.toString();
        return httpRequest;
    }


}
