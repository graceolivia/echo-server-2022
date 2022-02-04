package echoServer;

import echoServer.HTTP.HTTPResponse;
import echoServer.HTTP.StatusCode;
import echoServer.outputManagement.ClientWriteableFactory;
import echoServer.outputManagement.ClientWriteable;
import echoServer.socketManagement.ServerSocketInterface;

import java.io.IOException;
import java.net.Socket;

public class Echoer implements Echoable {
    ServerSocketInterface serverSocket;
    ClientReadableFactory clientReaderFactory;
    ClientWriteableFactory clientWriterFactory;

    public Echoer(ServerSocketInterface serverSocket, ClientReadableFactory clientReaderFactory, ClientWriteableFactory clientWriterFactory) throws IOException {
        this.serverSocket = serverSocket;
        this.clientWriterFactory = clientWriterFactory;
        this.clientReaderFactory = clientReaderFactory;
    }

    public Socket startServer() throws IOException {
        int port = 5000;
        serverSocket.bind(port);
        System.err.println("Started server on port " + port);
        Socket clientSocket = serverSocket.accept();
        System.err.println("Connected to client.");
        return clientSocket;
    }

    public boolean readClientInput(Socket clientSocket) throws IOException {

        ClientReadable bufferedReader = clientReaderFactory.makeReader(clientSocket);
        ClientWriteable printer = clientWriterFactory.makePrinter(clientSocket);
        String message;
        message = bufferedReader.readLine();
        System.out.println(message);
        StatusCode statusCode = StatusCode.PAGE_NOT_FOUND;
        HTTPResponse response = new HTTPResponse(statusCode);
        String responseText = response.toString(statusCode);
        return interpretClientMessage(responseText, printer);



//
//        while((message = bufferedReader.readLine()) != null) {
//            System.out.println(message);
//            StatusCode statusCode = StatusCode.PAGE_NOT_FOUND;
//            HTTPResponse response = new HTTPResponse(statusCode);
//            String responseText = response.toString(statusCode);
//            return interpretClientMessage(responseText, printer);
//        }
//        return false;
    }

    private boolean interpretClientMessage(String message, ClientWriteable printer) throws IOException {
        if (!message.equals("Connection: close")) {
            System.out.println(message);
            printer.println(message);
            return true;
        }
        else {
            System.out.println("Shutdown code received: "+message);
            return false;
        }
    }
}
