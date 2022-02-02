package echoServer.outputManagement;
import java.io.IOException;

public interface ClientWriterInterface {

    public void println(String message) throws IOException;

}