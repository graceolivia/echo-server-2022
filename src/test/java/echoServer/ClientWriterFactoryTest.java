package echoServer;
import echoServer.outputManagement.ClientWriteable;
import echoServer.outputManagement.ClientWriterFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ClientWriterFactoryTest {

    @Test
    void testMakePrinterReturnsAPrintWriter() throws IOException {
        ServerSocket server = new ServerSocket(8080);
        Socket socket = new Socket("localhost", 8080);
        ClientWriteable clientWriterFactory = new ClientWriterFactory();
        PrintWriter printer = clientWriterFactory.makePrinter(socket);
        assertInstanceOf(PrintWriter.class, printer);
    }
}
