package echoServer;

import echoServer.outputManagement.ClientWriteable;
import echoServer.outputManagement.ClientWriterInterface;
import echoServer.socketManagement.ServerSocketInterface;

import java.io.*;
import java.net.Socket;

public class Echoer implements Echoable {
    ServerSocketInterface serverSocket;
    ClientReadable clientReaderFactory;
    ClientWriteable clientWriterFactory;

    public Echoer(ServerSocketInterface serverSocket, ClientReadable clientReaderFactory, ClientWriteable clientWriterFactory) throws IOException {
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

        ClientReaderInterface bufferedReader = clientReaderFactory.makeReader(clientSocket);
        ClientWriterInterface printer = clientWriterFactory.makePrinter(clientSocket);
        String message;

        while((message = bufferedReader.readLine()) != null) {
            return interpretClientMessage(message, printer);
        }
        return false;
    }

    private boolean interpretClientMessage(String message, ClientWriterInterface printer) throws IOException {
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
