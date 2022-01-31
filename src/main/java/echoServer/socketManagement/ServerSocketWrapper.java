package echoServer.socketManagement;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketWrapper implements ServerSocketInterface {
    public ServerSocket serverSocket = new ServerSocket();

    public ServerSocketWrapper() throws IOException {
    }

    public Socket accept() throws IOException {
        return serverSocket.accept();
    }

    public void bind(int port) throws IOException {
        serverSocket.bind(new InetSocketAddress(port));
    }

    public void close() throws IOException {
        serverSocket.close();
    }
}
