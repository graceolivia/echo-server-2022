package echoServer;
import java.io.BufferedReader;
import java.io.IOException;

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
        String CRLF = "\r\n";
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = bufferedReader.readLine()) != null)
            {
                sb.append(line);
                sb.append(CRLF);
                if (line.equals("")) { break; }
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return sb.toString();
        }
    }

}
