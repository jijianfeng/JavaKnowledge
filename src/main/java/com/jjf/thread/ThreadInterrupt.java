package com.jjf.thread;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jijianfeng on 2017/11/6.
 */
public class ThreadInterrupt {

  private List<Thread> list = new ArrayList();

  private volatile boolean state = true;

  @Test
  public void testInterrupt() throws InterruptedException {
    Thread threadA = new Thread(() -> {
      while (state) {
        System.out.println("jjjj");
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          System.out.println("be interrupted");
        }
      }
    });
//    list.add(threadA);
    threadA.start();
//
    Thread.sleep(2000);
//
//    list.forEach(thread->{
//      if(!thread.isInterrupted()){
//        System.out.println("start interrupt");
//        thread.interrupt();
//      }
//    });

    new Thread(() -> setStateFalse()).start();

    Thread.sleep(100000000);
  }

  private void setStateFalse() {
    this.state = false;
  }
}
