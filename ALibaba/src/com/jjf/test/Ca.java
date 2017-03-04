package com.jjf.test;

public class Ca{
	private static volatile Ca ca;
	private Ca(){
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			System.out.println("我初始化完了");
		}
	}
	
	public static Ca getCa(){
//		if(ca==null){
//			synchronized(Ca.class){
//				if(ca==null){
//					 ca = new Ca();
//				}
//			}
//		}
		return new Ca();
	}
	
	public synchronized void doSomething() throws InterruptedException{
		System.out.println("干活开始");
		Thread.sleep(200);
		System.out.println("干完活了");
	}

}