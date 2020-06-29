package com.uig.benchmark;

import com.uig.string.StringFormatTest;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BenchmarkTest {

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(StringFormatTest.class.getSimpleName())
                .forks(1)
                .jvmArgs("-Xms2G", "-Xmx2G")
                .build();

        new Runner(opt).run();
    }

}
