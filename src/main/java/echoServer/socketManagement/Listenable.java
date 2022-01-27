package echoServer.socketManagement;
import java.io.IOException;
import java.net.Socket;
public interface Listenable {

    public Socket accept() throws IOException;

    public void bind() throws IOException;

    public void close() throws IOException;


}

