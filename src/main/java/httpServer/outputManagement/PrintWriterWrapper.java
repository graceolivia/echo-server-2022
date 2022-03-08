package httpServer.outputManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class PrintWriterWrapper implements ClientWriteable {

    public Socket clientSocket;
    public PrintWriter printer;

    public PrintWriterWrapper(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.printer = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public void print(String message) throws IOException {
        printer.print(message);
        printer.flush();
    }

    public void close() throws IOException {
        printer.close();
    }
}
