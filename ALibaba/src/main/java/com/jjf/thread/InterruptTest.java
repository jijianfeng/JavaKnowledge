package com.jjf.thread;

/**
 * Created by jjf_lenovo on 2017/5/21.
 */
public class InterruptTest {
    public static void main(String[] args) throws InterruptedException {
        MyThread t = new MyThread("MyThread");
        t.start();
        Thread.sleep(100);// 睡眠100毫秒
        t.interrupt();//  设置中断标记，不会中断进程
    }
}
class MyThread extends Thread {
    int i = 0;

    public MyThread(String name) {
        super(name);
    }

    public void run() {
        while (!isInterrupted()) {// 死循环，等待被中断
            System.out.println(getName() + getId() + "执行了" + ++i + "次");
        }
    }
}