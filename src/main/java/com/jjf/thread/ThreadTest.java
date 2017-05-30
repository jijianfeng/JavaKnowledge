package com.jjf.thread;
public class ThreadTest extends Thread {
	public static void main(String[] args) throws Exception {
		for (int i = 1; i < 10; i++) {
			new Out().start();
			Thread.sleep(1);
		}
	}
}

class Out extends Thread {
	@Override
	public synchronized void run() {
		for (int i = 1; i < 10; i++) {
			System.out.println(Thread.currentThread().getName() + ":" + i);
		}
	}
}