package com.jjf.thread;

/**
 * 打印零与奇偶数(信号量解法)
 *
 * @author IronMan 2020/07/24
 */

import java.util.concurrent.Semaphore;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class PrintEvenOdd {
    private int n;
    // 偶数
    private Semaphore even = new Semaphore(0);
    // 奇数
    private Semaphore odd = new Semaphore(1);

    public PrintEvenOdd(int n) {
        this.n = n;
    }

    public void even(IntConsumer printNumber) {
        try {
            for (int i = 2; i <= n; i += 2) {
                even.acquire();
                printNumber.accept(i);
                odd.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void odd(IntConsumer printNumber) {
        try {
            for (int i = 1; i <= n; i += 2) {
                odd.acquire();
                printNumber.accept(i);
                even.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PrintEvenOdd zeroEvenOdd = new PrintEvenOdd(100);
        new Run(zeroEvenOdd, (zeo) -> zeo.even(System.out::println)).start();

        new Run(zeroEvenOdd, (zeo) -> zeo.odd(System.out::println)).start();
    }

    static class Run extends Thread {

        private Consumer<PrintEvenOdd> consumer;
        private PrintEvenOdd zeroEvenOdd;

        public Run(PrintEvenOdd zeroEvenOdd, Consumer<PrintEvenOdd> consumer) {
            this.consumer = consumer;
            this.zeroEvenOdd = zeroEvenOdd;
        }

        @Override
        public void run() {
            consumer.accept(zeroEvenOdd);
        }
    }
}
