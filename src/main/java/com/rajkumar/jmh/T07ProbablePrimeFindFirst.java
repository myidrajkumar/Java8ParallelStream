/**
 * 
 */
package com.rajkumar.jmh;

import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
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
public class T07ProbablePrimeFindFirst {
    
    @Param("64")
    private static int BIT_LENGTH = 5; // NOSONAR
    
    @Param("1000")
    private static int N_ITERATIONS = 10; // NOSONAR
    
    @Benchmark
    public Optional<BigInteger> generatePrimesFindFirst() {
        return IntStream.range(0, N_ITERATIONS).mapToObj(eachNum -> getProbablePrime())
                .filter(prime -> prime.toString().startsWith("1")).findFirst();
    }
    
    @Benchmark
    public Optional<BigInteger> generatePrimesFindAny() {
        return IntStream.range(0, N_ITERATIONS).mapToObj(eachNum -> getProbablePrime())
                .filter(prime -> prime.toString().startsWith("1")).findAny();
    }
    
    /**
     * @return
     */
    private BigInteger getProbablePrime() {
        return BigInteger.probablePrime(BIT_LENGTH, ThreadLocalRandom.current());
    }
    
    public static void main(String[] args) {
        Options options = new OptionsBuilder().include(T07ProbablePrimeFindFirst.class.getName()).build();
        new Runner(options);
    }
}
