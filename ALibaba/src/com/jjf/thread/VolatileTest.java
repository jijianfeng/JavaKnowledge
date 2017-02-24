package com.jjf.thread;

public class VolatileTest {
	volatile static boolean on = true;
	public static void main(String args[]){
		test();
	}
	public static void test(){
		Thread  thread = new Thread(){
			@Override
			public void run(){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(false);
				on = false;
			}
		};
		thread.start();
		while(on){
			System.out.println("ggggggggg");
//			try {
////				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
}

