package echoServer;
import echoServer.outputManagement.ClientWriteable;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class EchoerTest {
    // a mock for ClientSocket
    // a mock for ClientWriter
    @Test
    void testReadClientInputCallsBufferedReaderReadLine() {
        Socket socket = new Socket();
        MockListener mockServerSocket = new MockListener(socket);
        // etc
        Echoer echoer = new Echoer(mockServerSocket, ..., ...);
        Socket clientSocket = echoer.startServer();
        assertEquals(socket, clientSocket);
      // Echoable echoer = new Echoer(bufferedReader, printer);
    }

    @Test
    void testReadClientInputCallsPrintWriterPrintLn() {}
}
