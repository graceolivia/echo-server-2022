package httpServer.outputManagement;
import java.io.IOException;

public interface ClientWriteable {

    public void println(String message) throws IOException;

}