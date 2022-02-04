package echoServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class BufferedReaderWrapper implements ClientReadable {


    public BufferedReader bufferedReader;

    public BufferedReaderWrapper(BufferedReader bufferedReader) throws IOException {
        this.bufferedReader = bufferedReader;
    }

    @Override
    public String readLine() throws IOException {
        return bufferedReader.readLine();
    }

    @Override
    public String readAllLines() throws IOException {
        return bufferedReader.lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

}
