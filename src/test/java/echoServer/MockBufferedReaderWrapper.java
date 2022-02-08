package echoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class MockBufferedReaderWrapper implements ClientReadable {

    private String[] inputArray;
    private String[] reversedInputArray;
    private Stack<String> inputStack;
    public String input;
    public boolean readLineWasCalled = false;

    public MockBufferedReaderWrapper(String input) {
        this.input = input;
        this.inputArray = input.split("\r\n");
        this.reversedInputArray = reverseArray(inputArray);
        //inputStack = toStack(reversedInputArray);
    }

    public String readLine() throws IOException {
        readLineWasCalled = true;
        if ( inputStack.size() > 0) {
            return inputStack.pop();
        }
        return null;
    }

    private String[] reverseArray(String[] inputArray){
        String[] newArray = new String[];
        int j = 0;
        for (int i = (inputArray.length - 1); i >= 0; i--) {
            newArray[j] = inputArray[i];
            j += 1;
        }
        return newArray;
    }

    private Stack<String> toStack(String[] inputArray){
        Stack<String> inputStack = new Stack<String>();
        inputStack.addAll(List.of(inputArray));
        return inputStack;
    }


}
