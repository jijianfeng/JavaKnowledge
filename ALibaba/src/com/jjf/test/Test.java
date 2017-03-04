package com.jjf.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 模拟Ca多线程下的解密方式
 * 
 * @author jjf_lenovo
 * 
 */
public class Test{
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InterruptedException {
		Long start =  System.currentTimeMillis();
		for(int i=1;i<100;i++){
			Thread thread = new Thread(new Decode());
			thread.start();
		}
		System.out.println("时间："+(System.currentTimeMillis()-start));
//		Constructor con = Ca.class.getDeclaredConstructor();
//		con.setAccessible(true);
//		Ca ca = (Ca)con.newInstance();
//		ca.doSomething();
		
//		ArrayList<Integer> list = new ArrayList<Integer>();
//        Method method = list.getClass().getMethod("add", Object.class);
//        method.invoke(list, "Java反射机制实例。");
//        System.out.println(list.get(0));
	}	
}

class Decode implements Runnable{
	public void run(){
		Ca ca = Ca.getCa();
		try {
			ca.doSomething();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

