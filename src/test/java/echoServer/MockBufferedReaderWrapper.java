package echoServer;

import java.io.BufferedReader;
import java.io.IOException;

public class MockBufferedReaderWrapper implements ClientReadable {
    private String input;
    public boolean readLineWasCalled = false;

    public MockBufferedReaderWrapper(String input) {
        this.input = input;
    }

    @Override
    public String readLine() {
        readLineWasCalled = true;
        return input;
    }

    @Override
    public String readAllLines() throws IOException {
        return null;
    }
}
