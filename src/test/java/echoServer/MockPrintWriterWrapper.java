package echoServer;

import echoServer.outputManagement.ClientWriterInterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MockPrintWriterWrapper implements ClientWriterInterface {
    String printlnWasCalledWith = new String();
    public PrintWriter printer;

    public MockPrintWriterWrapper() {
        this.printer = printer;
    }

    @Override
    public void println(String message) throws IOException {
        printlnWasCalledWith = message;
    }
}
