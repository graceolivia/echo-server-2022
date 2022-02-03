package echoServer;
import echoServer.socketManagement.ServerSocketInterface;
import java.io.IOException;
import java.net.Socket;

public class MockServerSocketWrapper implements ServerSocketInterface {
    Socket clientSocket;
    public boolean acceptHasBeenCalled = false;
    public int bindHasBeenCalledWith;
    public boolean closeHasBeenCalled = false;

    public MockServerSocketWrapper(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
    }

    public Socket accept() {
        acceptHasBeenCalled = true;
        return clientSocket;
    }

    public void bind(int port) {
        bindHasBeenCalledWith = port;
    }

    public void close() {
        closeHasBeenCalled = true;
    }
}
