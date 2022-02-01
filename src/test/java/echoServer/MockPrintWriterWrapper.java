package echoServer;

import echoServer.outputManagement.ClientWriterInterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MockPrintWriterWrapper implements ClientWriterInterface {
    String printlnWasCalledWith = new String();

    @Override
    public void println(String message) throws IOException {
        printlnWasCalledWith = message;
    }
}
