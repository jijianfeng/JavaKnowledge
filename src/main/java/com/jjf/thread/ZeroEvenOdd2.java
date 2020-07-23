package com.jjf.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * 打印零与奇偶数（lock解法）
 *
 * @author IronMan 2020/06/25
 */
public class ZeroEvenOdd2 {
    private int n;
    private Lock zero = new ReentrantLock();
    private Lock even = new ReentrantLock();
    private Lock odd = new ReentrantLock();

    public ZeroEvenOdd2(int n) {
        this.n = n;
        even.lock();
        odd.lock();
    }

    public void zero(IntConsumer printNumber) {
        try {
            for (int i = 0; i < n; i++) {
                zero.lock();
                printNumber.accept(0);
                if (i % 2 == 0) {
                    odd.unlock();
                } else {
                    even.lock();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void even(IntConsumer printNumber) {
        try {
            for (int i = 2; i <= n; i += 2) {
                even.lock();
                printNumber.accept(i);
                zero.unlock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void odd(IntConsumer printNumber) {
        try {
            for (int i = 1; i <= n; i += 2) {
                odd.lock();
                printNumber.accept(i);
                zero.unlock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ZeroEvenOdd2 zeroEvenOdd = new ZeroEvenOdd2(10);
        new Run(zeroEvenOdd, (zeo) -> zeo.even(System.out::print)).start();

        new Run(zeroEvenOdd, (zeo) -> zeo.zero(System.out::print)).start();

        new Run(zeroEvenOdd, (zeo) -> zeo.odd(System.out::print)).start();
    }

    static class Run extends Thread {

        private Consumer<ZeroEvenOdd2> consumer;
        private ZeroEvenOdd2 zeroEvenOdd;

        public Run(ZeroEvenOdd2 zeroEvenOdd, Consumer<ZeroEvenOdd2> consumer) {
            this.consumer = consumer;
            this.zeroEvenOdd = zeroEvenOdd;
        }

        @Override
        public void run() {
            consumer.accept(zeroEvenOdd);
        }
    }
}
