package echoServer;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.*;

public class EchoServer {
    Listenable serverSocketWrapper;
    public EchoServer(echoServer.Listenable serverSocketWrapper) {
        this.serverSocketWrapper = serverSocketWrapper;
    }
//    public static void main(String args[]) {
//
//    }

    public void run() throws IOException {
        ServerSocketWrapper serverSocket = new ServerSocketWrapper();
        int port = 8080;
        serverSocket.bind(port);
        System.err.println("Started server on port " + port);


        while (true) {
            Socket clientSocket = serverSocket.serverSocket.accept();
            System.err.println("Connected to client.");
            InputStream in = clientSocket.getInputStream();
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter printer = new PrintWriter(clientSocket.getOutputStream(),true);
            String message;
            while((message = bufferReader.readLine()) != null) {
                if (!message.equals("byebye")) {
                    System.out.println("Echo :"+message);
                    printer.println(message);

                }
                else {
                    System.out.println("Byebye: "+message);
                    break;
                }

            }
            System.err.println("Closing connection.");
            clientSocket.close();
        }

    }
}
