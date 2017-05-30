package com.jjf.load;

public class ConstClassTest {
	public static void main(String args[]){
		//也就是说并没有加载ConstClass
		System.out.println(ConstClass.HELLOWORLD);
	}
}
