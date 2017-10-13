package com.jjf.thread;

import org.junit.*;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by jijianfeng on 2017/10/10.
 */
public class ThreadPoolExecutorTest {

  public static ThreadFactory
      threadFactory =
      new CustomizableThreadFactory("task-pool-test");

  public static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 10,
                                                                       0L, TimeUnit.MILLISECONDS,
                                                                       new ArrayBlockingQueue<Runnable>(
                                                                           20),
                                                                       threadFactory);

  @org.junit.Test
  public void testQueue() {
    for(;;){
      threadPool.execute(new LowAction());
      System.out.println("+1");
    }
  }

  class LowAction implements Runnable {

    @Override
    public void run() {
      try {
        Thread.sleep(500000000);
        System.out.println("ok");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }

}
