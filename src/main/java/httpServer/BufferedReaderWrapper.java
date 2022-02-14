package httpServer;
import java.io.BufferedReader;
import java.io.IOException;

public class BufferedReaderWrapper implements ClientReadable {

    public BufferedReader bufferedReader;

    public BufferedReaderWrapper(BufferedReader bufferedReader) throws IOException {
        this.bufferedReader = bufferedReader;
    }

    public String read() throws IOException {
        return String.valueOf((char)bufferedReader.read());
    }

}
