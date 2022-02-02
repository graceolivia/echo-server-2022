package echoServer;

import echoServer.outputManagement.ClientWriteableFactory;
import echoServer.outputManagement.ClientWriteable;

import java.io.IOException;
import java.net.Socket;

public class MockClientWriterFactory implements ClientWriteableFactory {

    private final ClientWriteable printer;

    public MockClientWriterFactory(ClientWriteable printer) {
        this.printer = printer;
    }

    @Override
    public ClientWriteable makePrinter(Socket clientSocket) throws IOException {
        return printer;
    }

}
