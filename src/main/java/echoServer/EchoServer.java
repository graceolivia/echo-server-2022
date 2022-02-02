package echoServer;


import echoServer.outputManagement.ClientWriteable;
import echoServer.outputManagement.ClientWriterFactory;

import echoServer.socketManagement.ServerSocketInterface;
import echoServer.socketManagement.ServerSocketWrapper;

import java.io.*;
import java.net.Socket;

public class EchoServer {

    public static void main(String[] args) throws IOException {
        ServerSocketInterface serverSocket = new ServerSocketWrapper();
        ClientWriteable clientWriterFactory = new ClientWriterFactory();
        ClientReadable clientReaderFactory = new ClientReaderFactory();
        Echoable echoer = new Echoer(serverSocket, clientReaderFactory, clientWriterFactory);
        Socket clientSocket = echoer.startServer();

        boolean should_loop_continue = true;
        try {
            while (should_loop_continue == true) {
                should_loop_continue = echoer.readClientInput(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeSockets(clientSocket, serverSocket);
    }

    public static void closeSockets(Socket clientSocket, ServerSocketInterface serverSocket) throws IOException {
        System.err.println("Closing connection.");
        clientSocket.close();
        serverSocket.close();
    };
}
