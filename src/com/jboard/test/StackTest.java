package com.jboard.test;

import java.util.Stack;

public class StackTest {
    public static void main(String[] args) {

        Stack<String> myStack =  new Stack<>();

        myStack.push("hello~");
        myStack.push("nice to meet you~");
        myStack.push("bye~");

        while(!myStack.empty()) {
            String myValue = myStack.pop();
            System.out.println(myValue);
        }

    }
}
