package com.jjf.thread;

/**
 * Created by jjf_lenovo on 2017/5/21.
 */
public class VolatileTest2 extends Thread {
    private static boolean flag = false;
    public void run() {
        while (!flag){
//            System.out.println("gg");
        };
    }
    public static void main(String[] args) throws Exception {
        new VolatileTest2().start();
        Thread.sleep(2000);
        flag = true;
    }
}