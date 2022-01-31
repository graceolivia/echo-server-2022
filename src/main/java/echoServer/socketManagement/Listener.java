package echoServer.socketManagement;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;

public class Listener implements Listenable {
    public ServerSocketInterface serverSocket;

    public Listener(ServerSocketInterface serverSocket) throws IOException {
        this.serverSocket = serverSocket;
    }



    public Socket accept() throws IOException {
        return serverSocket.accept();
    }


    public void bind(int port) throws IOException {
        //InetAddress address = InetAddress.getByName("localhost");
        //SocketAddress socketAddress = new InetSocketAddress(address, port);
        serverSocket.bind(port);
    }


    public void close() throws IOException {
        serverSocket.close();
    }

}