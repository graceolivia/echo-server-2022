package httpServer;

import httpServer.outputManagement.ClientWriteable;

import java.io.IOException;
import java.io.PrintWriter;

public class MockPrintWriterWrapper implements ClientWriteable {
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
