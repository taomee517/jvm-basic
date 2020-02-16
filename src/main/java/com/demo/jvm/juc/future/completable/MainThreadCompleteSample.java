package com.demo.jvm.juc.future.completable;

import java.util.concurrent.CompletableFuture;

public class MainThreadCompleteSample {
  public static void main(String[] args) throws InterruptedException {
    CompletableFuture<Integer> task = new CompletableFuture<>();

    task
      .thenApply(e -> e * 2)
      .thenApply(e -> e + 1)
      .thenAccept(System.out::println);

    task.complete(10);

  }
}