package echoServer;

public class MockBufferedReaderWrapper implements ClientReaderInterface {
    private String input;
    public boolean readlineWasCalled = false;

//    MockBufferedReaderWrapper should have a constructor/initializer
//    that takes some String
//    parameter and sets it to an input String attribute.
//    The implementation of readline in MockBufferedReaderWrapper
//    should set readlineWasCalled to true and returns that input attribute

    public MockBufferedReaderWrapper(String input) {
        this.input = input;
    }


    @Override
    public String readLine() {
        readlineWasCalled = true;
        return input;
    }
}
