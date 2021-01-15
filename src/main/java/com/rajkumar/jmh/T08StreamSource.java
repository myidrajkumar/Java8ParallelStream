/**
 * 
 */
package com.rajkumar.jmh;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
public class T08StreamSource {
    
    @Param("10000000")
    private int N_SIZE; // NOSONAR
    
    private Random random = new Random(314L);
    
    private Set<String> lineSet;
    private List<String> lineList;
    private Set<Integer> intSet;
    private List<Integer> intList;
    
    @Setup
    public void intsList() {
        intList = IntStream.range(0, N_SIZE).map(i -> random.nextInt()).boxed().collect(Collectors.toList());
    }
    
    @Setup
    public void intsSet() {
        intSet = IntStream.range(0, N_SIZE).map(i -> random.nextInt()).boxed().collect(Collectors.toSet());
    }
    
    @Setup
    public void readLines() throws URISyntaxException, IOException {
        try (Stream<String> lines = Files.lines(Paths.get("src", "main", "resources", "files", "words.txt"))) {
            this.lineSet = lines.collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.lineList = new ArrayList<>(lineSet);
    }
    
    @Benchmark
    public int processStringSet() {
        return lineSet.stream().map(String::toUpperCase).mapToInt(String::length).sum();
    }
    
    @Benchmark
    public int processStringList() {
        return lineList.stream().map(String::toUpperCase).mapToInt(String::length).sum();
    }
    
    @Benchmark
    public int processStringSetParallel() {
        return lineSet.stream().map(String::toUpperCase).mapToInt(String::length).parallel().sum();
    }
    
    @Benchmark
    public int processStringListParallel() {
        return lineList.stream().map(String::toUpperCase).mapToInt(String::length).parallel().sum();
    }
    
    @Benchmark
    public int processIntSet() {
        return intSet.stream().mapToInt(i -> i * 3).sum();
    }
    
    @Benchmark
    public int processIntList() {
        return intList.stream().mapToInt(i -> i * 3).sum();
    }
    
    @Benchmark
    public int processIntSetParallel() {
        return intSet.stream().mapToInt(i -> i * 3).parallel().sum();
    }
    
    @Benchmark
    public int processIntListParallel() {
        return intList.stream().mapToInt(i -> i * 3).parallel().sum();
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        Options options = new OptionsBuilder().include(T08StreamSource.class.getName()).build();
        new Runner(options);
    }
    
}
