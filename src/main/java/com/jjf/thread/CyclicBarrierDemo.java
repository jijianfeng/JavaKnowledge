package com.jjf.thread;

import java.util.concurrent.CyclicBarrier;

/**
 * @author IronMan 2020/07/26
 */
public class CyclicBarrierDemo {
    static class TaskThread extends Thread {

        CyclicBarrier barrier;

        public TaskThread(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(getName() + " 到达栅栏 A");
                barrier.await();
                System.out.println(getName() + " 冲破栅栏 A");

//                Thread.sleep(2000);
//                System.out.println(getName() + " 到达栅栏 B");
//                barrier.await();
//                System.out.println(getName() + " 冲破栅栏 B");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int threadNum = 3;
        CyclicBarrier barrier = new CyclicBarrier(threadNum,
                () -> System.out.println("所有线程OK"));

        for (int i = 0; i < 5; i++) {
            new TaskThread(barrier).start();
        }
    }
}
