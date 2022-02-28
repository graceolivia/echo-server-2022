package httpServer;

import httpServer.outputManagement.ClientWriteable;

import java.io.IOException;
import java.io.PrintWriter;

public class MockPrintWriterWrapper implements ClientWriteable {
    String printWasCalledWith = new String();
    boolean closeWasCalled = false;
    public PrintWriter printer;

    public MockPrintWriterWrapper() {
        this.printer = printer;
    }

    @Override
    public void print(String message) throws IOException {
        printWasCalledWith = message;
    }

    public void close() throws IOException {
        closeWasCalled = true;
    }
}
