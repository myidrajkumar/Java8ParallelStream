/**
 * 
 */
package com.rajkumar.jmh;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
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
public class T05FindFirstSet {
    
    @Param("1000000")
    private static int N_1MILLION; // NOSONAR
    
    private Random random = new Random();
    private static final int MAX_RANDOM = 100_00_00_000;
    
    private Set<Integer> intsN100;
    private Set<Integer> intsN150;
    private int limit;
    
    @Setup
    public void setUp() {
        intsN100 = IntStream.range(0, N_1MILLION).mapToObj(i -> random.nextInt(MAX_RANDOM)).collect(Collectors.toSet());
        intsN100.add(200_00_00_000);
        
        Set<Integer> intsN50 = IntStream.range(0, N_1MILLION / 2).mapToObj(i -> random.nextInt(MAX_RANDOM))
                .collect(Collectors.toSet());
        
        intsN150 = new HashSet<>();
        intsN150.addAll(intsN100);
        intsN150.add(200_00_00_000);
        intsN150.addAll(intsN50);
        
        limit = intsN150.size() + 1;
    }
    
    @Benchmark
    public int findFirstOn100NoParallel() {
        return intsN100.stream().filter(i -> i > MAX_RANDOM).findFirst().get(); // NOSONAR
    }
    
    @Benchmark
    public int findFirstOn100Parallel() {
        return intsN100.stream().filter(i -> i > MAX_RANDOM).parallel().findFirst().get(); // NOSONAR
    }
    
    @Benchmark
    public int findAnyOn100NoParallel() {
        return intsN100.stream().filter(i -> i > MAX_RANDOM).findAny().get(); // NOSONAR
    }
    
    @Benchmark
    public int findAnyOn100Parallel() {
        return intsN100.stream().filter(i -> i > MAX_RANDOM).parallel().findAny().get(); // NOSONAR
    }
    
    @Benchmark
    public int findFirstOn150NoParallel() {
        return intsN150.stream().filter(i -> i > MAX_RANDOM).findFirst().get(); // NOSONAR
    }
    
    @Benchmark
    public int findFirstOn150Parallel() {
        return intsN150.stream().filter(i -> i > MAX_RANDOM).parallel().findFirst().get(); // NOSONAR
    }
    
    @Benchmark
    public int findAnyOn150NoParallel() {
        return intsN150.stream().filter(i -> i > MAX_RANDOM).findAny().get(); // NOSONAR
    }
    
    @Benchmark
    public int findAnyOn150Parallel() {
        return intsN150.stream().filter(i -> i > MAX_RANDOM).parallel().findAny().get(); // NOSONAR
    }
    
    @Benchmark
    public int findFirstWithLimitOn150NoParallel() {
        return intsN150.stream().filter(i -> i > MAX_RANDOM).limit(limit).findFirst().get(); // NOSONAR
    }
    
    @Benchmark
    public int findFirstWithLimitOn150Parallel() {
        return intsN150.stream().filter(i -> i > MAX_RANDOM).limit(limit).parallel().findFirst().get(); // NOSONAR
    }
    
    @Benchmark
    public int findAnyWithLimitOn150NoParallel() {
        return intsN150.stream().filter(i -> i > MAX_RANDOM).limit(limit).findAny().get(); // NOSONAR
    }
    
    @Benchmark
    public int findAnyWithLimitOn150Parallel() {
        return intsN150.stream().filter(i -> i > MAX_RANDOM).limit(limit).parallel().findAny().get(); // NOSONAR
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        Options options = new OptionsBuilder().include(T05FindFirstSet.class.getName()).build();
        new Runner(options);
    }
    
}
