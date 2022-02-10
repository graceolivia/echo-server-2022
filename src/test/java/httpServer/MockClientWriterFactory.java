package httpServer;

import httpServer.outputManagement.ClientWriteableFactory;
import httpServer.outputManagement.ClientWriteable;

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
