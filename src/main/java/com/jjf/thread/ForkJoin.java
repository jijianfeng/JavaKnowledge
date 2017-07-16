package com.jjf.thread;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
/**
 * Created by jjf_lenovo on 2017/5/22.
 */
public class ForkJoin extends RecursiveTask<Integer> {

    private static final long serialVersionUID = -6196480027075657316L;

    private static final int THRESHOLD = 500000;

    private long[] array;

    private int low;

    private int high;

    public ForkJoin(long[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if (high - low <= THRESHOLD) {
            // 小于阈值则直接计算
            for (int i = low; i < high; i++) {
                sum += array[i];
            }
        } else {
            // 1. 一个大任务分割成两个子任务
            int mid = (low + high) >>> 1;
            ForkJoin left = new ForkJoin(array, low, mid);
            ForkJoin right = new ForkJoin(array, mid + 1, high);
            // 2. 分别计算
            left.fork();
            right.fork();
            // 3. 合并结果
            sum = left.join() + right.join();
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long[] array = genArray(1000);

        // 1. 创建任务
        ForkJoin ForkJoin = new ForkJoin(array, 0, array.length - 1);

        long begin = System.currentTimeMillis();

        // 2. 创建线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // 3. 提交任务到线程池
        forkJoinPool.submit(ForkJoin);

        // 4. 获取结果
        Integer result = ForkJoin.get();

        long end = System.currentTimeMillis();
        System.out.println(String.format("结果 %s 耗时 %sms", result, end - begin));
        long sum = 0;
        begin = System.currentTimeMillis();
        for(int i=0;i<array.length;i++){
            sum =  sum+array[i];
        }
        System.out.println(String.format("结果 %s 耗时 %sms", sum, System.currentTimeMillis() - begin));
    }

    private static long[] genArray(int size) {
        long[] array = new long[size];
        for (int i = 0; i < size; i++) {
            array[i] = new Random().nextLong();
            System.out.println(array[i]);
        }
        return array;
    }
}
