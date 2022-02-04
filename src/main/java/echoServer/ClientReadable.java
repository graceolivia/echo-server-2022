package echoServer;
import java.io.IOException;

public interface ClientReadable {

    public String readLine() throws IOException;
    public String readAllLines() throws IOException;
}
