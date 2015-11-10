package com.sailorgeoffrey.reactor.demo;

import reactor.Environment;
import reactor.rx.broadcast.Broadcaster;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ReactorHelloWorld {
    public static void main(String... args) throws InterruptedException {

        Environment.initialize();

        final Broadcaster<String> sink = Broadcaster.create(Environment.get());
        final CountDownLatch latch = new CountDownLatch(1);

        sink.dispatchOn(Environment.cachedDispatcher())
                .map(String::toUpperCase)
                .consume(s -> {
                    System.out.printf("s=%s%n", s);
                    latch.countDown();
                });

        sink.onNext("Hello World!");

        latch.await(10, TimeUnit.SECONDS);
    }
}
