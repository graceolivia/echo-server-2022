package echoServer;

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
        int port = 8080;
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

        while((message = bufferedReader.readLine()) != null) {
            return interpretClientMessage(message, printer);
        }
        return false;
    }

    private boolean interpretClientMessage(String message, ClientWriteable printer) throws IOException {
        if (!message.equals("byebye")) {
            System.out.println("Echo: "+message);
            printer.println("Echo: "+message);
            return true;
        }
        else {
            System.out.println("Shutdown code received: "+message);
            return false;
        }
    }
}
