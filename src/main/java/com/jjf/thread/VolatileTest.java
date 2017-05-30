package com.jjf.thread;

public class VolatileTest {
	public static void main(String[] args) throws Exception {
		final StopTester stopTester = new StopTester();
		new Thread(){
			@Override
			public void run(){
				stopTester.doWork();
			}
		}.start();
		Thread.sleep(5000);
		stopTester.shutdown();
		System.out.println("main GGG");
	}
}

class StopTester  {
	boolean shutdownRequested;

	public void shutdown() {
		shutdownRequested = true;
		System.out.println("Thread GG");
	}

	public void doWork()  {
		while(!shutdownRequested){
			//如理一个简单的任务
			System.out.println(System.currentTimeMillis());
		}
	}
}


