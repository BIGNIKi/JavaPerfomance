package me.champeau.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 3)
@Warmup(iterations = 3, time = 1)
@Threads(1)
public class IntParsersBenchmark {

    @Param({"123", "123456789", "1234+6789", "1234+", "Just a string"}) // параметры для теста
    public String str;

    // все замеры (тесты) ниже

    @Benchmark
    public void parseInt(Blackhole blackhole) {
        blackhole.consume(IntParsers.parseInt(str));
    }

    @Benchmark
    public void parseChars(Blackhole blackhole) {
        blackhole.consume(IntParsers.parseChars(str));
    }

    @Benchmark
    public void parseRegex(Blackhole blackhole) {
        blackhole.consume(IntParsers.parseRegex(str));
    }

    @Benchmark
    public void parseRegex2(Blackhole blackhole) {
        blackhole.consume(IntParsers.parseRegex2(str));
    }
}