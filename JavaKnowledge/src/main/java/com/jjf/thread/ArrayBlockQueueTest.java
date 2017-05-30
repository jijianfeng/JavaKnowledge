package com.jjf.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by jjf_lenovo on 2017/5/22.
 */
public class ArrayBlockQueueTest {
    /** 装鸡蛋的盘子，大小为5 */
    private  BlockingQueue<Integer> eggs = new ArrayBlockingQueue<Integer>(5); //数组 不扩容，有上限，满掉后阻塞线程，直到被取出

    /** 放鸡蛋 */
    public void putEgg(Integer egg) {
        try {
            Thread.sleep(1000);
            eggs.put(egg);// 向盘子末尾放一个鸡蛋，如果盘子满了，当前线程阻塞
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 下面输出不准确，因为与put操作不是一个原子操作,但是看鸡蛋序号，肯定没问题
        System.out.println("放入鸡蛋"+egg);
    }

    /** 取鸡蛋 */
    public Integer getEgg() {
        Integer egg = null;
        try {
            egg = eggs.take();// 从盘子开始取一个鸡蛋，如果盘子空了，当前线程阻塞
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 下面输出不准确，因为与take操作不是一个原子操作,但是看鸡蛋序号，肯定没问题
        System.out.println("拿到鸡蛋"+egg);
        return egg;
    }

    /** 放鸡蛋线程 */
    static class AddThread extends Thread {
        private ArrayBlockQueueTest plate;
        private Integer egg ;

        public AddThread(ArrayBlockQueueTest plate,Integer egg) {
            this.plate = plate;
            this.egg = egg;
        }

        public void run() {
            plate.putEgg(egg);
        }
    }

    /** 取鸡蛋线程 */
    static class GetThread extends Thread {
        private ArrayBlockQueueTest plate;

        public GetThread(ArrayBlockQueueTest plate) {
            this.plate = plate;
        }

        public void run() {
            plate.getEgg();
        }
    }

    public static void main(String[] args) {
        ArrayBlockQueueTest plate = new ArrayBlockQueueTest();
        // 先启动10个放鸡蛋线程
        for(int i = 0; i < 9; i++) {
            new Thread(new AddThread(plate,i)).start();
        }
        // 再启动10个取鸡蛋线程
//        for(int i = 0; i < 10; i++) {
//            new Thread(new GetThread(plate)).start();
//        }
    }
}
