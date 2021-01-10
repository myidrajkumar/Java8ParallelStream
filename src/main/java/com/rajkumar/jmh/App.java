package com.rajkumar.jmh;

import java.util.function.BinaryOperator;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        BinaryOperator<String> testBinary = (a, b) -> a + b;
        String result = testBinary.apply("Ekaa", "Rajkumar");
        System.out.println(result);
    }
}
