package echoServer;

import echoServer.socketManagement.Listenable;

import java.io.IOException;
import java.net.Socket;

public class MockListener implements Listenable {

    boolean closeHasBeenCalled = false;
    boolean startServerHasBeenCalled = false;

    public MockListener(Socket socket) {
    }

    @Override
    public Socket accept() throws IOException {
        return new Socket();
    }

    @Override
    public void bind(int port) throws IOException {

    }

    @Override
    public void close() throws IOException {
        closeHasBeenCalled = true;
    }

}
