package echoServer;
import java.io.BufferedReader;
import java.io.IOException;

public interface ClientReadable {

    public String readLine() throws IOException;
    String readAllLines() throws IOException;
}
