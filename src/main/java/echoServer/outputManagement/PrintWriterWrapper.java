package echoServer.outputManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class PrintWriterWrapper implements ClientWriterInterface {

    public Socket clientSocket;
    public PrintWriter printer;

    public PrintWriterWrapper(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.printer = new PrintWriter(clientSocket.getOutputStream(),true);
    }

    public void println(String message) throws IOException {
        printer.println(message);
    }
}
