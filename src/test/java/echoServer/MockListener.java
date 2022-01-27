package echoServer;

import echoServer.socketManagement.Listenable;

import java.io.IOException;
import java.net.Socket;

public class MockListener implements Listenable {

    boolean closeHasBeenCalled = false;
    boolean startServerHasBeenCalled = false;
    @Override
    public void close() throws IOException {
        closeHasBeenCalled = true;
    }

    @Override
    public Socket startServer() throws IOException {
        startServerHasBeenCalled = true;
        return new Socket();
    }
}
