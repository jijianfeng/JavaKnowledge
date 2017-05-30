package com.jjf.load;

public class ClinitClassLoad {
	public static void main(String args[]){
		LoadClass loadClass = new LoadClass();
		Thread t1 = new Thread(loadClass);
		Thread t2 = new Thread(loadClass);
		t1.start();
		t2.start();
	}
}  

class LoadClass implements Runnable{
	@Override
	public void run() {
		System.out.println("线程"+Thread.currentThread().getName()+"开始");
		DeadLoopClass DeadLoopClass = new DeadLoopClass();
		System.out.println("线程"+Thread.currentThread().getName()+"结束");
	}
}

class DeadLoopClass{
	static {
		System.out.println(Thread.currentThread().getName()+"cinit");// <clinit>()方法只运行一次
	}
}