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
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null)
            {
                sb.append(line);
                if (line.equals("")) { break; }
            }
            bufferedReader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

}
