package httpServer;
import httpServer.http.HTTPMethods;
import httpServer.outputManagement.ClientWriteableFactory;
import httpServer.outputManagement.ClientWriterFactory;

import httpServer.routes.Router;
import httpServer.socketManagement.ServerSocketInterface;
import httpServer.socketManagement.ServerSocketWrapper;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {

    public static void main(String[] args) throws IOException {
        Router router = new Router(getRoutes());
        ServerSocketInterface serverSocket = new ServerSocketWrapper();
        ClientWriteableFactory clientWriterFactory = new ClientWriterFactory();
        ClientReadableFactory clientReaderFactory = new ClientReaderFactory();
        ListenAndRespondable echoer = new ListenerAndResponder(serverSocket, clientReaderFactory, clientWriterFactory, router);
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
        routes.put("/head_request", new HTTPMethods[] {HTTPMethods.HEAD, HTTPMethods.OPTIONS});
        return routes;
    }
}
