package com.jjf.thread;

public class ThreadTest3 extends Thread {
	public static void main(String[] args) throws Exception {
		for (int i = 1; i < 50; i++) {
			new Out3().start();
//			Thread.sleep(1);
		}
	}
}

class Out3 extends Thread {
	@Override
	public void run() {
		abc();
	}

	public static synchronized void abc() {
		for (int i = 1; i < 20; i++) {
			System.out.println(Thread.currentThread().getName()+":" + i);
		}
	}
}
