package httpServer;
import httpServer.outputManagement.ClientWriteableFactory;
import httpServer.outputManagement.ClientWriterFactory;
import httpServer.outputManagement.ClientWriteable;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ClientWriterFactoryTest {

    @Test
    void testMakePrinterReturnsAPrintWriter() throws IOException {
        ServerSocket server = new ServerSocket(8080);
        Socket socket = new Socket("localhost", 8080);
        ClientWriteableFactory clientWriterFactory = new ClientWriterFactory();
        ClientWriteable printer = clientWriterFactory.makePrinter(socket);
        assertInstanceOf(ClientWriteable.class, printer);
    }
}
