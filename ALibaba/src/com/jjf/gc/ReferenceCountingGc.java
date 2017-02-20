package com.jjf.gc;

/**
 * 2017年2月18日14:41:27
 * @author jjf_lenovo
 * jvm参数(打印GC日志): -XX:+PrintGCDetails
 */
public class ReferenceCountingGc {
	public Object instance = null;
//	private static final int _1MB = 1024*1024;
//	private byte[] bigSize = new byte[2 * _1MB];
	
	public static void testGC(){
		ReferenceCountingGc objA = new ReferenceCountingGc();
		ReferenceCountingGc objB = new ReferenceCountingGc();
		objA.instance = objB;
		objB.instance = objA;
		objA = null;
		objB = null;
		//假设在这方式GC,objA 和 objB 是否能被回收
		System.gc();
	}
	
	public static void main(String args[]){
		testGC();
	}
}
