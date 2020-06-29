package com.uig.completablefuture;

import com.uig.remote.CallClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ApplyScene {

    public void doAllScenes() throws ExecutionException, InterruptedException {
        System.out.println("Test Start");

        System.out.println("Create Seller 0 - 1000");
        List<Integer> sellers =  IntStream.range(0, 1000).boxed().collect(toList());

        int interval = 50;
        System.out.println("Select limit is " + interval);

        System.out.println("Use fixedThreadPool : size 10");
        Executor executor = Executors.newFixedThreadPool(10);

        CompletableFuture[] futures = CompletableFuture.supplyAsync( // TODO 코드가 보기 어려우니 예쁘게 분리해보기
                () -> {
                    CallClient client = new CallClient();
                    client.createTempTable(sellers);
                    return sellers;
                }, executor)
                .thenApply( // TODO 코드가 보기 어려우니 예쁘게 분리해보기
                        future -> IntStream.range(0, sellers.size()-1)
                                .filter(x -> x % interval == 0)
                                .boxed() // TODO boxed가 필요한 곳이 어디인지 완벽하게 알아내자.
                                .map(from -> CompletableFuture.supplyAsync(
                                        () -> {
                                            CallClient client = new CallClient();
                                            return client.selectLimit(from, interval-1);}, executor))
                                .toArray(size -> new CompletableFuture[size])
                ).get();

        CompletableFuture.allOf(futures).join();

        System.out.println("Test End");
    }

    public void doSelectScene(int interval) {
        List<Integer> sellers = IntStream.range(0, 1000).boxed().collect(Collectors.toList());

        System.out.println("Select Start");
        //select multiple times using LIMIT clause.

        Executor executor = Executors.newFixedThreadPool(10);

        CompletableFuture[] futures = IntStream.range(0, sellers.size() - 1)
                .filter(x -> x % interval == 0)
                .boxed()
                .map(from -> CompletableFuture.supplyAsync(
                        () -> {
                            CallClient client = new CallClient();
                            return client.selectLimit(from, interval);
                        }, executor))
                .toArray(size -> new CompletableFuture[size]);

        CompletableFuture.allOf(futures).join();
        System.out.println("Select End");
    }
}
