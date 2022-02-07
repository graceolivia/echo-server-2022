package echoServer;
import java.io.BufferedReader;
import java.io.IOException;

public class BufferedReaderWrapper implements ClientReadable {

    public BufferedReader bufferedReader;

    public BufferedReaderWrapper(BufferedReader bufferedReader) throws IOException {
        this.bufferedReader = bufferedReader;
    }

    public String readLine() throws IOException {
        return bufferedReader.readLine();
    }

}
