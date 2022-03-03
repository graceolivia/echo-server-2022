package httpServer;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class MockBufferedReaderWrapper implements ClientReadable {

    private String[] inputArray;
    private Stack<Character> inputStack;
    public String input;
    public boolean readWasCalled = false;

    public MockBufferedReaderWrapper(String input) {
        this.input = input;
        this.inputArray = input.split("\r\n");
        inputStack = toStack();
    }

    public String read() {
        readWasCalled = true;
        if ( inputStack.size() > 0) {
            return String.valueOf((char) inputStack.pop());
        }
        return null;
    }

    private Stack<Character> toStack() {
        List<Character> charList = input.chars()
                .mapToObj(character -> (char) character)
                .collect(Collectors.toList());
        Stack<Character> charStack = new Stack<>();
        Collections.reverse(charList);
        charStack.addAll(charList);
        return charStack;
    }

}
