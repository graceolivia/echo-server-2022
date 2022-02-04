package echoServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
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
        String line;
        String message = null;
        try {
            while ((line = bufferedReader.readLine()) != null)
            {
                String[] tokens = line.split("\\s");
                message = Arrays.toString(tokens);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

}
