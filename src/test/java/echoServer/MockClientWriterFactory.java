package echoServer;

import echoServer.outputManagement.ClientWriteable;
import echoServer.outputManagement.ClientWriterInterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MockClientWriterFactory implements ClientWriteable {
    
    public static Socket socket;
    private final ClientWriterInterface printer;
    boolean printWriterWasCalled = false;

    public MockClientWriterFactory(ClientWriterInterface printer) {
        this.printer = printer;
    }

    @Override
    public ClientWriterInterface makePrinter(Socket clientSocket) throws IOException {
        boolean printWriterWasCalled = true;
        return printer;
    }

}
