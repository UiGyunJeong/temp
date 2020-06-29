package com.uig.string;

import org.openjdk.jmh.annotations.Benchmark;

public class StringFormatTest {

    @Benchmark
    public String testStringFormat() throws InterruptedException {
        return String.format("abc %s", "d");
    }

    @Benchmark
    public String testStringPlus() throws InterruptedException {
        return "abc " + "d";
    }
}
