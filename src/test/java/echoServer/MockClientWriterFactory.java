package echoServer;

import echoServer.outputManagement.ClientWriteable;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MockClientWriterFactory implements ClientWriteable {

    @Override
    public PrintWriter makePrinter(Socket clientSocket) throws IOException {
        PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(),true);
        return printWriter;
    }

}
