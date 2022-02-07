package echoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class MockBufferedReaderWrapper<inputArray> implements ClientReadable {
    private String input;
    public boolean readLineWasCalled = false;
    private String[] inputArray = input.split("\r\n");
    Stack<String> inputStack = toStack(inputArray);

    public MockBufferedReaderWrapper(String input) {
        this.input = input;
    }




    public String readLine() throws IOException {
        readLineWasCalled = true;
        return inputStack.pop();
    }

    private Stack<String> toStack(String[] inputArray){
        Stack<String> inputStack = new Stack<String>();
        inputStack.addAll(List.of(inputArray));
        return inputStack;
    }
}
