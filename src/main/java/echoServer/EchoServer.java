package echoServer;

import echoServer.outputManagement.ClientWriteable;
import echoServer.outputManagement.ClientWriterFactory;
import echoServer.socketManagement.Listenable;
import echoServer.socketManagement.Listener;

import java.io.*;
import java.net.Socket;

public class EchoServer {

    public static void main(String[] args) throws IOException {
        Listenable serverSocket = new Listener();
        ClientWriteable clientWriterFactory = new ClientWriterFactory();
        ClientReadable clientReaderFactory = new ClientReaderFactory();
        Echoable echoer = new Echoer(serverSocket, clientReaderFactory, clientWriterFactory);
        Socket clientSocket = echoer.startServer();


//        PrintWriter clientWriter = clientWriterFactory.makePrinter(clientSocket);
//

//        BufferedReader clientReader = clientReaderFactory.makeReader(clientSocket);


        boolean should_loop_continue = true;
        try {
            while (should_loop_continue == true) {
                should_loop_continue = echoer.readClientInput();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeSockets(clientSocket, serverSocket);
    }

    public static void closeSockets(Socket clientSocket, Listenable serverSocket) throws IOException {
        System.err.println("Closing connection.");
        clientSocket.close();
        serverSocket.close();
    };
}
