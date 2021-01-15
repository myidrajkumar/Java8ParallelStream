/**
 * 
 */
package com.rajkumar.nonjmh;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Rajkumar. S
 *
 */
public class SomeHashManipulation {
    
    /**
     * @param args
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Set<String> linesSet = new HashSet<>();
        try (Stream<String> lines = Files.lines(Paths.get("src", "main", "resources", "files", "words.txt"))) {
            linesSet.addAll(lines.collect(Collectors.toSet()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> linesList = new ArrayList<>(linesSet);
        
        ConcurrentHashMap<String, Long> threads = new ConcurrentHashMap<>();
        
        ForkJoinPool forkJoinPool = new ForkJoinPool(8);
        
        Callable<Integer> task = () -> {
            System.out.println("Launching thread = " + Thread.currentThread().getName());// NOSONAR
            return linesSet.stream().mapToInt(String::length).parallel()
                    .peek(i -> threads.merge(Thread.currentThread().getName(), 1L, Long::sum)).sum(); // NOSONAR
        };
        
        ForkJoinTask<Integer> submit = forkJoinPool.submit(task);
        submit.get();
        
        threads.forEach((key, value) -> System.out.println(key + " -> " + value));// NOSONAR
        
        forkJoinPool.shutdown();
        
        Set<Integer> hashes = linesList.stream().map(SomeHashManipulation::hash).collect(Collectors.toSet());
        System.out.println("# hashes = " + hashes.size());// NOSONAR
        hashes.forEach(System.out::println);// NOSONAR
        
    }
    
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16); // NOSONAR
    }
    
}
