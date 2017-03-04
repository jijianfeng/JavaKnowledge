package com.jjf.thread;

public class YieldTest implements Runnable {
	@Override
	public void run() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 5; i++) {
			System.out.println(Thread.currentThread().getName() + ": " + i);
			Thread.yield();
		}
	}

	public static void main(String[] args) {
		YieldTest runn = new YieldTest();
		Thread t1 = new Thread(runn, "FirstThread");
		Thread t2 = new Thread(runn, "SecondThread");
		t1.start();
		t2.start();

	}
}