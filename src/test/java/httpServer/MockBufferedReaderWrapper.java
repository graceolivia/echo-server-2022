package httpServer;

import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class MockBufferedReaderWrapper implements ClientReadable {

    private String[] inputArray;
    private Stack<String> inputStack;
    public String input;
    public boolean readLineWasCalled = false;

    public MockBufferedReaderWrapper(String input) {
        this.input = input;
        this.inputArray = input.split("\r\n");
        inputStack = to465f Stack(inputArray);
    }

    public String read() throws IOException {
        readLineWasCalled = true;
        if ( inputStack.size() > 0) {
            return inputStack.pop();
        }
        return null;
    }

    private Stack<String> toStack(String[] inputArray) {
        Stack<String> inputStack = new Stack<String>();
        inputStack.addAll(List.of(inputArray));
        return inputStack;
    }

}
