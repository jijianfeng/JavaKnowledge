package com.jjf.thread;

/**
 * @author IronMan 2020/06/25
 */
public class SimpleThread extends Thread {

    private String text;

    SimpleThread(String text) {
        this.text = text;
    }

    @Override
    public void run() {
        System.out.println(text);
    }

    public static void main(String[] args) {
        new SimpleThread("小明").start();
        new SimpleThread("小红").start();
    }
}
