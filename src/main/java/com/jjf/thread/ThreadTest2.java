package com.jjf.thread;

public class ThreadTest2 extends Thread {

	private String lock;

	public ThreadTest2(String lock) {
		this.lock = lock;
	}

	public void run() {
		synchronized (lock) {
			for (int i = 1; i < 50; i++) {
				System.out.println(Thread.currentThread().getName()+":" + i);   
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		String lock = "lock";
		for (int i = 1; i < 20; i++) {
			new ThreadTest2(lock).start();
			Thread.sleep(1);
		}
	}
}