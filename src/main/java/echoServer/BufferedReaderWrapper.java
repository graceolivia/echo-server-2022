package echoServer;
import java.io.BufferedReader;
import java.io.IOException;

public class BufferedReaderWrapper implements ClientReadable {

    public BufferedReader bufferedReader;

    public BufferedReaderWrapper(BufferedReader bufferedReader) throws IOException {
        this.bufferedReader = bufferedReader;
    }

    public String readAllLines() throws IOException {
        String CRLF = "\r\n";
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line);
                stringBuilder.append(CRLF);
                if (line.equals("")) { break; }
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return stringBuilder.toString();
        }
    }

}
