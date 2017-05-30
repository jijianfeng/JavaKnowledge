package com.jjf.gc;

import java.util.ArrayList;
import java.util.List;
//-Xms512m -Xmx1024m -Xmn10m 堆 默认、最大、最小内存
//-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../logs.dump 内存溢出时生成快照
//-XX:+PrintGCDetails 打出GC日志
public class HeapDumpOnOutOfMemoryErroy {
	public static void main(String args[]){
		RuntimeConstantPoolOutOfMemory();
	}
	
	/**
	 * 堆溢出
	 */
	public static void HeapOutOfMemory(){
		int i=1;
		List list = new ArrayList();
		while(true){
			list.add(new String[10000]);
			System.out.println(i++);
		}
	}
	
	/**
	 * 栈溢出
	 */
	public static void StackOutOfMemory(){
		StackOutOfMemory();
	}
	
	/**
	 * 运行时常量池溢出
	 */
	public static void RuntimeConstantPoolOutOfMemory(){
		List list = new ArrayList();
		int i = 1;
		while(true){
			list.add(String.valueOf(i++).intern());
		}
	}
}
