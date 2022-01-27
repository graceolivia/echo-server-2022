package echoServer;
import echoServer.outputManagement.ClientWriteable;
import echoServer.socketManagement.Listenable;

import java.io.*;
import java.net.Socket;

public class Echoer implements Echoable {
    Listenable listener;
    ClientReadable clientReaderFactory;
    ClientWriteable clientWriterFactory;

    public Echoer(Listenable listener, ClientReadable clientReaderFactory, ClientWriteable clientWriterFactory) throws IOException {
        this.listener = listener;
        this.clientWriterFactory = clientWriterFactory;
        this.clientReaderFactory = clientReaderFactory;
    }

    public Socket startServer() throws IOException {
        int port = 8080;
        listener.bind(port);
        System.err.println("Started server on port " + port);
        Socket clientSocket = listener.accept();
        System.err.println("Connected to client.");
        return clientSocket;
    }

    public boolean readClientInput() throws IOException {
        String message;
        while((message = bufferedReader.readLine()) != null) {
            return interpretClientMessage(message, printer);
        }
        return false;
    }

    private boolean interpretClientMessage(String message, PrintWriter printer) throws IOException {
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
