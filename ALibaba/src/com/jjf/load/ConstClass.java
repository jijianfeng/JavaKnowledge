package com.jjf.load;

public class ConstClass {
	static{
		i = 0;
//		System.out.println(i);
		System.out.println("ConstClass init!");
	}
	static int i =1;
	public static final String HELLOWORLD = "hello word";
	public static void main(String args[]){
		NotInitialization.Out();
	}
}
class NotInitialization{
	public static  void Out(){
		System.out.println(ConstClass.HELLOWORLD);
	}
}