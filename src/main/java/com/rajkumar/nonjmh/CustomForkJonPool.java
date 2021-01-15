/**
 * 
 */
package com.rajkumar.nonjmh;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

/**
 * @author Rajkumar. S
 *
 */
public class CustomForkJonPool {
    
    /**
     * @param args
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        
        ConcurrentHashMap<String, Long> threads = new ConcurrentHashMap<>();
        
        ForkJoinPool forkJoinPool = new ForkJoinPool(8);
        Callable<Integer> task = () -> IntStream.range(0, 100000).map(i -> i * 3).parallel()
                .peek(i -> threads.merge(Thread.currentThread().getName(), 1L, Long::sum)).sum(); // NOSONAR
        
        int withoutForkJoinSum = IntStream.range(0, 100000).map(i -> i * 3).parallel()
                .peek(i -> threads.merge(Thread.currentThread().getName(), 1L, Long::sum)).sum();// NOSONAR
        
        Integer sum = forkJoinPool.submit(task).get();
        System.out.println("Without ForkJoin Sum: " + withoutForkJoinSum);// NOSONAR
        System.out.println("With ForkJoin Sum: " + sum);// NOSONAR
        
        threads.forEach((key, value) -> System.out.println(key + " -> " + value)); // NOSONAR
        
        forkJoinPool.shutdown();
    }
    
}
