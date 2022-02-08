package echoServer;
import echoServer.http.HTTPMethods;
import echoServer.outputManagement.ClientWriteableFactory;
import echoServer.outputManagement.ClientWriterFactory;

import echoServer.routes.Router;
import echoServer.socketManagement.ServerSocketInterface;
import echoServer.socketManagement.ServerSocketWrapper;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class EchoServer {

    public static void main(String[] args) throws IOException {
        Router router = new Router(getRoutes());
        ServerSocketInterface serverSocket = new ServerSocketWrapper();
        ClientWriteableFactory clientWriterFactory = new ClientWriterFactory();
        ClientReadableFactory clientReaderFactory = new ClientReaderFactory();
        Echoable echoer = new Echoer(serverSocket, clientReaderFactory, clientWriterFactory, router);
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

    public static Map<String, HTTPMethods[]> getRoutes() {
        Map<String, HTTPMethods[]> routes = new HashMap<>();

        routes.put("/simple_get", new HTTPMethods[] {HTTPMethods.GET});
        return routes;
    }
}
