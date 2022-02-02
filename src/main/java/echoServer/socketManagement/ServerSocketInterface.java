package echoServer.socketManagement;

import java.io.IOException;
import java.net.Socket;

public interface ServerSocketInterface {

    Socket accept() throws IOException;

    void bind(int port) throws IOException;

    void close() throws IOException;

}
