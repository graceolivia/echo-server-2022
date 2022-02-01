package echoServer.outputManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public interface ClientWriterInterface {

    public void println(String message) throws IOException;

}