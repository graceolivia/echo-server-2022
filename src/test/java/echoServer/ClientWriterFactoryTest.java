package echoServer;
import echoServer.outputManagement.ClientWriteable;
import echoServer.outputManagement.ClientWriterFactory;
import echoServer.outputManagement.ClientWriterInterface;
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
        ClientWriteable clientWriterFactory = new ClientWriterFactory();
        ClientWriterInterface printer = clientWriterFactory.makePrinter(socket);
        assertInstanceOf(ClientWriterInterface.class, printer);
    }
}
