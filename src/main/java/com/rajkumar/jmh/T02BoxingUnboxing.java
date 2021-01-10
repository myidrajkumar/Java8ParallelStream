/**
 * 
 */
package com.rajkumar.jmh;

import java.util.concurrent.TimeUnit;
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
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class T02BoxingUnboxing {
    
    @Param("100000")
    private int N_SIZE; // NOSONAR
    
    private int[] arrayOfInts;
    private Integer[] arrayOfIntegers;
    
    @Setup
    public void createArrayOfInts() {
        arrayOfInts = new int[N_SIZE];
        for (int i = 0; i < N_SIZE; i++) {
            arrayOfInts[i] = 3 * i;
        }
    }
    
    @Setup
    public void createArrayOfIntegers() {
        arrayOfIntegers = new Integer[N_SIZE];
        for (int i = 0; i < N_SIZE; i++) {
            arrayOfIntegers[i] = 3 * i;
        }
    }
    
    @Benchmark
    public int calculateSumOfInts() {
        int sum = 0;
        for (int i = 0; i < arrayOfInts.length; i++) {
            sum += i * 7;
        }
        return sum;
    }
    
    @Benchmark
    public int calculateSumOfIntegers() {
        Integer sum = 0;
        for (int i = 0; i < arrayOfIntegers.length; i++) {
            sum += i * 7;
        }
        return sum;
    }
    
    @Benchmark
    public int calculateSumOfRange() {
        return IntStream.range(0, N_SIZE).map(i -> i * 3).map(i -> i * 7).sum();
    }
    
    @Benchmark
    public int calculateSumOfRangeBoxed() {
        return IntStream.range(0, N_SIZE).map(i -> i * 3).map(i -> i * 7).boxed().reduce(0, Integer::sum);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        Options options = new OptionsBuilder().include(T02BoxingUnboxing.class.getName()).build();
        new Runner(options);
    }
    
}
