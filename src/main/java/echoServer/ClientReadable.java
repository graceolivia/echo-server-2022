package echoServer;
import java.io.IOException;

public interface ClientReadable {

    public String readAllLines() throws IOException;
}
