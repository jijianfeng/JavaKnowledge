package com.jjf.test;

import java.util.Date;

/**
 * java»ù´¡
 * 
 * @author jjf_lenovo
 * 
 */
public class Test{

	private void test() {
		System.out.println(super.getClass().getName());
	}

	public static void main(String[] args) {
		 ((Test)null).test();
	}	
}
