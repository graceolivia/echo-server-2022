package echoServer;

import echoServer.outputManagement.ClientWriteableFactory;
import echoServer.outputManagement.ClientWriteable;

import java.io.IOException;
import java.net.Socket;

public class MockClientWriterFactory implements ClientWriteableFactory {
    
    public static Socket socket;
    private final ClientWriteable printer;
    boolean printWriterWasCalled = false;

    public MockClientWriterFactory(ClientWriteable printer) {
        this.printer = printer;
    }

    @Override
    public ClientWriteable makePrinter(Socket clientSocket) throws IOException {
        boolean printWriterWasCalled = true;
        return printer;
    }

}
