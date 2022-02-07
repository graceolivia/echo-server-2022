package echoServer;
import echoServer.outputManagement.ClientWriteableFactory;
import echoServer.outputManagement.ClientWriterFactory;

import echoServer.socketManagement.ServerSocketInterface;
import echoServer.socketManagement.ServerSocketWrapper;

import java.io.IOException;
import java.net.Socket;

public class EchoServer {

    public static void main(String[] args) throws IOException {
        ServerSocketInterface serverSocket = new ServerSocketWrapper();
        ClientWriteableFactory clientWriterFactory = new ClientWriterFactory();
        ClientReadableFactory clientReaderFactory = new ClientReaderFactory();
        Echoable echoer = new Echoer(serverSocket, clientReaderFactory, clientWriterFactory);
        Socket clientSocket = echoer.startServer();

        try {
           while (true) {
               echoer.readClientInput(clientSocket);
               clientSocket = echoer.keepListening();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeSockets(Socket clientSocket, ServerSocketInterface serverSocket) throws IOException {
        System.err.println("Closing connection.");
        clientSocket.close();
        serverSocket.close();
    };
}
