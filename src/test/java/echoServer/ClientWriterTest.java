package echoServer;
import echoServer.outputManagement.ClientWriteable;
import echoServer.outputManagement.ClientWriterFactory;
import echoServer.socketManagement.Listenable;
import echoServer.socketManagement.Listener;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ClientWriterTest {

    @Test
    void testMakePrinterReturnsAPrintWriter() throws IOException {
//        ClientWriteable clientWriterFactory = new ClientWriterFactory();
//        Socket clientSocket = new Socket("localhost", 8080);
//        PrintWriter shouldBePrinter = clientWriterFactory.makePrinter(clientSocket);

    }
}
