/**
 * 
 */
package com.rajkumar.jmh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author Rajkumar. S
 *
 */
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(3)
@State(Scope.Benchmark)
public class T03PointerChase {
    
    @Param("100000")
    private int N_SIZE; // NOSONAR
    
    private ArrayList<Integer> arrayList = new ArrayList<>();
    private LinkedList<Integer> linkedList = new LinkedList<>();
    private LinkedList<Integer> shuffledLinkedList = new LinkedList<>();
    private LinkedList<Integer> scatteredLinkedList = new LinkedList<>();
    
    @Setup
    public void createArrayList() {
        arrayList = IntStream.range(0, N_SIZE).map(i -> i * 3).boxed().collect(Collectors.toCollection(ArrayList::new));
    }
    
    @Setup
    public void createLinkedList() {
        linkedList = IntStream.range(0, N_SIZE).map(i -> i * 3).boxed()
                .collect(Collectors.toCollection(LinkedList::new));
    }
    
    @Setup
    public void createShuffeledLinkedList() {
        shuffledLinkedList = IntStream.range(0, N_SIZE).map(i -> i * 3).boxed()
                .collect(Collectors.toCollection(LinkedList::new));
        Collections.shuffle(shuffledLinkedList, new Random(314159L));
    }
    
    @Setup
    public void createShatteredLinkedList() {
        scatteredLinkedList = new LinkedList<>();
        for (int i = 1; i < N_SIZE + 1; i++) {
            scatteredLinkedList.add(i * 3);
            
            for (int j = 0; j < 100; j++) {
                scatteredLinkedList.add(0);
            }
        }
        scatteredLinkedList.removeIf(i -> i == 0);
        Collections.shuffle(shuffledLinkedList, new Random(314159L));
    }
    
    @Benchmark
    public int calculateSumOfArrayList() {
        return arrayList.stream().mapToInt(i -> i).map(i -> i * 5).sum();
    }
    
    @Benchmark
    public int calculateSumOfLinkedList() {
        return linkedList.stream().mapToInt(i -> i).map(i -> i * 5).sum();
    }
    
    @Benchmark
    public int calculateSumOfShuffeledList() {
        return shuffledLinkedList.stream().mapToInt(i -> i).map(i -> i * 5).sum();
    }
    
    @Benchmark
    public int calculateSumOfScattteredList() {
        return scatteredLinkedList.stream().mapToInt(i -> i).map(i -> i * 5).sum();
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        Options options = new OptionsBuilder().include(T03PointerChase.class.getName()).build();
        new Runner(options);
    }
    
}
