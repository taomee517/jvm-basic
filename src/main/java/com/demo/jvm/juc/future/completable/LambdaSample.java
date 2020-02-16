package com.demo.jvm.juc.future.completable;

import java.util.concurrent.CompletableFuture;

public class LambdaSample {
  public static void main(String[] args) throws InterruptedException {
    System.out.println("In start of main " + Thread.currentThread());

    startAsyncTask(); //this is non-blocking

    System.out.println("At the end of main " + Thread.currentThread());
    Thread.sleep(2000);
  }

  private static void startAsyncTask() {
    CompletableFuture.runAsync(() ->
        System.out.println("running a little task " + Thread.currentThread()));
  }
}