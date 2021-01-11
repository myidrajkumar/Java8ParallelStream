/**
 * 
 */
package com.rajkumar.jmh;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
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
public class T01ProbablePrime {
    
    @Param({ "64", "128" })
    private static int BIT_LENGTH = 5; // NOSONAR
    
    @Param({ "10", "100" })
    private static int N_ITERATIONS = 10; // NOSONAR
    
    @Benchmark
    public List<BigInteger> sumOfNPrimes() {
        List<BigInteger> intList = new ArrayList<>();
        for (int i = 0; i < N_ITERATIONS; i++) {
            BigInteger identifiedPrime = getProbablePrime();
            intList.add(identifiedPrime);
        }
        return intList;
    }
    
    @Benchmark
    public List<BigInteger> sumOfNPrimesWithInitialSize() {
        List<BigInteger> intList = new ArrayList<>(N_ITERATIONS);
        for (int i = 0; i < N_ITERATIONS; i++) {
            BigInteger identifiedPrime = getProbablePrime();
            intList.add(identifiedPrime);
        }
        return intList;
    }
    
    @Benchmark
    public List<BigInteger> sumOfNPrimesWithStream() {
        return IntStream.range(0, N_ITERATIONS).mapToObj(eachNum -> getProbablePrime()).collect(Collectors.toList());
    }
    
    @Benchmark
    public List<BigInteger> sumOfNPrimesWithStreamGenerate() {
        return Stream.generate(this::getProbablePrime).limit(N_ITERATIONS).collect(Collectors.toList());
    }
    
    @Benchmark
    public List<BigInteger> sumOfNPrimesWithStreamGenerateParallel() {
        return Stream.generate(this::getProbablePrime).parallel().limit(N_ITERATIONS).collect(Collectors.toList());
    }
    
    @Benchmark
    public List<BigInteger> sumOfNPrimesWithStreamParallel() {
        return IntStream.range(0, N_ITERATIONS).parallel().mapToObj(eachNum -> getProbablePrime())
                .collect(Collectors.toList());
    }
    
    @Benchmark
    public List<BigInteger> sumOfNPrimesWithStreamUnorderedParallel() {
        return IntStream.range(0, N_ITERATIONS).unordered().parallel().mapToObj(eachNum -> getProbablePrime())
                .collect(Collectors.toList());
    }
    
    /**
     * @return
     */
    private BigInteger getProbablePrime() {
        return BigInteger.probablePrime(BIT_LENGTH, ThreadLocalRandom.current());
    }
    
    public static void main(String[] args) {
        Options options = new OptionsBuilder().include(T01ProbablePrime.class.getName()).build();
        new Runner(options);
    }
}
