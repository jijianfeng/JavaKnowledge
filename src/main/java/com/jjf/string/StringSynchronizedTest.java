package com.jjf.string;

/**
 * 测试String,StringBuilder,StringBuffer 三个是否线程安全
 * 2017年2月20日22:03:41
 * @author jjf_lenovo
 * Edit:2017年2月20日23:10:15
 */
public class StringSynchronizedTest {
	static String string = "String" ;
	public static void main(String args[]){
		//TODO-1.String是线程安全的(String是final类了，但是如何写个例子证明线程安全呢？？？下面的方法刚写的，写完想想是错的) 我对于引用的概念还是有点模糊
		for(int i = 0;i<10;i++){
			Thread thread = new Thread( new StringThread(string));
			thread.start();
		}
		
		//2TODO  StringBuilder是线程不安全，但是我只想看结果怎么办，用future
//		StringBuilder stringBuilder = new StringBuilder();
//		for(int i = 0;i<100000;i++){
//			Thread thread = new Thread( new StringBuilderThread(stringBuilder));
//			thread.start();
//		}
		
		//3.StringBuffer线程不安全
//		StringBuffer stringBuffer = new StringBuffer();
//		for(int i = 0;i<100000;i++){
//			Thread thread = new Thread( new StringBufferThread(stringBuffer));
//			thread.start();
//		}
	}
	
	public synchronized static void append(){
		string = string+"String";
	}
	
	public synchronized static String get(){
		return string;
	}
}

class StringThread implements Runnable{
	String str ;
	
	public StringThread(String str){
		this.str = str;
	}
	
	@Override
	public void run() {
		StringSynchronizedTest.append();
//		System.out.println(StringSynchronizedTest.get());
		System.out.println(StringSynchronizedTest.get().length()/(new String("String")).length());//结果为100000则是安全的
	}
}

class StringBuilderThread implements Runnable{
	StringBuilder str ;
	
	public StringBuilderThread(StringBuilder str){
		this.str = str;
	}
	
	@Override
	public void run() {
		str.append("StringBuilderThread");
		System.out.println(str.length()/(new String("StringBuilderThread")).length());//结果为100000则是安全的
	}
}

class StringBufferThread implements Runnable{
	StringBuffer str ;
	
	public StringBufferThread(StringBuffer str){
		this.str = str;
	}
	
	@Override
	public void run() {
		str.append("StringBufferThread");
		System.out.println(str.length()/(new String("StringBufferThread")).length());//结果为100000则是安全的
	}
}