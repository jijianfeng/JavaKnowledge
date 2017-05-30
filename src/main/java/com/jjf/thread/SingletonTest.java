package com.jjf.thread;

public class SingletonTest {
	public static void main(String args[]) throws InterruptedException{
		Thread thread1 = new Thread(new SingLetonThread());
		Thread thread2 = new Thread(new SingLetonThread());
		thread1.start();
		thread2.start();
	}
}

class SingLetonThread implements Runnable{
	@Override
	public void run() {
		Singleton singleton =Singleton.getInstance();
		System.out.println(singleton.toString());
	}
}
