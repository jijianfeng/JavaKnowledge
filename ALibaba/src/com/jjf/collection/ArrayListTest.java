package com.jjf.collection;

import java.util.ArrayList;
import java.util.List;

public class ArrayListTest {
	public static void main(String args[]){
		final int N = 10000000;
		Object obj = new Object();
		
		//没用调用ensureCapacity()方法初始化ArrayList对象
		ArrayList list = new ArrayList();
		long startTime = System.currentTimeMillis();
		for(int i=0;i<N;i++){
			list.add(obj);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("没有调用ensureCapacity()方法所用时间：" + (endTime - startTime) + "ms");
		
		//没用调用ensureCapacity()方法初始化ArrayList对象数量
		list = new ArrayList(N);
		startTime = System.currentTimeMillis();
		for(int i=0;i<N;i++){
			list.add(obj);
		}
		endTime = System.currentTimeMillis();
		System.out.println("没有调用ensureCapacity()方法但初始化设置数量方法所用时间：" + (endTime - startTime) + "ms");
		
		//调用ensureCapacity()方法初始化ArrayList对象数量
		list = new ArrayList(N);
		list.ensureCapacity(N);
		startTime = System.currentTimeMillis();
		for(int i=0;i<N;i++){
			list.add(obj);
		}
		endTime = System.currentTimeMillis();
		System.out.println("有调用ensureCapacity()方法，且设置了初始化数量所用时间：" + (endTime - startTime) + "ms");
		
		//调用ensureCapacity()方法初始化ArrayList对象
		list = new ArrayList();
		startTime = System.currentTimeMillis();
		list.ensureCapacity(N);//预先设置list的大小
		for(int i=0;i<N;i++){
			list.add(obj);
		}
		endTime = System.currentTimeMillis();
		System.out.println("调用ensureCapacity()方法所用时间：" + (endTime - startTime) + "ms");
	}
}
