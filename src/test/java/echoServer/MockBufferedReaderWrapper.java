package echoServer;

public class MockBufferedReaderWrapper implements ClientReaderInterface {
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
}
