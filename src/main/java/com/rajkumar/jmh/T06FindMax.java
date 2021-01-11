/**
 * 
 */
package com.rajkumar.jmh;

import java.util.List;
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
@Fork(3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class T06FindMax {
    
    @Param("1000000")
    private static int N_1MILLION; // NOSONAR
    private static final int MAX_RANDOM = 100;
    private Random random = new Random();
    
    private List<Integer> intsN150;
    
    @Setup
    public void setUp() {
        intsN150 = IntStream.range(0, N_1MILLION + N_1MILLION / 2).mapToObj(i -> random.nextInt(MAX_RANDOM))
                .collect(Collectors.toList());
    }
    
    @Benchmark
    public int getMaxNoParallel() {
        return intsN150.stream().mapToInt(i -> i).max().getAsInt(); // NOSONAR
    }
    
    @Benchmark
    public int getMaxWithLimitNoParallel() {
        return intsN150.stream().mapToInt(i -> i).limit(N_1MILLION).max().getAsInt(); // NOSONAR
    }
    
    @Benchmark
    public int getMaxParallel() {
        return intsN150.stream().mapToInt(i -> i).parallel().max().getAsInt(); // NOSONAR
    }
    
    @Benchmark
    public int getMaxWithLimitParallel() {
        return intsN150.stream().mapToInt(i -> i).parallel().limit(N_1MILLION).max().getAsInt(); // NOSONAR
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        Options options = new OptionsBuilder().include(T06FindMax.class.getName()).build();
        new Runner(options);
    }
    
}
