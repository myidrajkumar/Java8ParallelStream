/**
 * 
 */
package com.rajkumar.nonjmh;

import java.util.stream.IntStream;

/**
 * @author Rajkumar. S
 *
 */
public class AssocuativeIssueInReduction {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        int sum = IntStream.range(0, 10).sum();
        System.out.println("Normal OK sum: " + sum); // NOSONAR
        
        int sumParallel = IntStream.range(0, 10).parallel().sum();
        System.out.println("Normal Associative OK Parallel sum: " + sumParallel); // NOSONAR
        
        System.out.println("Expected: 570 but below will produce incorrect due to non-associativity"); // NOSONAR
        
        int reduceSumSquares = IntStream.range(0, 10).reduce(0, (i1, i2) -> (i1 * i1) + (i2 * i2));
        System.out.println("Normal Non-Associative Problematic reduce squares sum: " + reduceSumSquares);  // NOSONAR
        
        int reduceSumSquaresParallel = IntStream.range(0, 10).parallel().reduce(0, (i1, i2) -> (i1 * i1) + (i2 * i2));
        System.out
                .println("Normal Non-Associative Problematic reduce squares parallel sum: " + reduceSumSquaresParallel);  // NOSONAR
    }
    
}
