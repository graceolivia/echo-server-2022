package echoServer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;

public class ServerSocketWrapper implements Listenable {
    ServerSocket serverSocket = new ServerSocket();

    public ServerSocketWrapper() throws IOException {
    }

    public Socket accept() throws IOException {
        return serverSocket.accept();
    }

    public void bind(int port) throws IOException {
        InetAddress address=InetAddress.getByName("localhost");
        SocketAddress socketAddress=new InetSocketAddress(address, port);
        serverSocket.bind(socketAddress);
    }


    public void close() {

    }

}