package com.jjf.test;

public class Spike {
	int a = 2;
	String b = "2";
	static char[] ch ={'2','2'};
	
	public static void main(String[] args) {
		Spike spike = new Spike();
		delete(spike.a);
		delete(spike.b);
		delete(ch);
		System.out.println(spike.a+":"+spike.b+":"+spike.ch[1]);
		
	}
	
	public static void delete(int a){
		a++;
	}
	
	public static void delete(String a){
		a = "1";
	}
	
	public static void delete(char[] a){
		a[1] = '1';
	}
}
