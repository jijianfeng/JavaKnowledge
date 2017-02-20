package com.jjf.thread;

import java.util.HashMap;
import java.util.Map;

public class Test {
	
    public  static int count = 0;
    
    public static void inc(String lock) {
        //这里延迟1毫秒，使得结果明显
    	synchronized(lock){
	        try {
	            Thread.sleep(1);
	        }
	        catch (InterruptedException e) {
	        	
	        }
	        count++;
    	}
    	//这里每次运行的值都有可能不同,可能为1000
    	System.out.println("运行结果:Counter.count=" + Test.count);
    }
 
    public static void main(String[] args) {
    	Map<String, String> map = new HashMap<String, String>();
        //同时启动1000个线程，去进行i++计算，看看实际结果
    	final String lock = "lock";
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Test.inc(lock);
                }
            }).start();
        }
    }
}