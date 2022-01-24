package echoServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Listenable serverSocketWrapper = new ServerSocketWrapper();
        EchoServer server = new EchoServer(serverSocketWrapper);
        server.run();
    }

}
