package com.jjf.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
//		 ((Test)null).test();
		Map map = new HashMap();
		System.out.println(map.getClass()
								.getClassLoader()
										.getSystemClassLoader()
											.getParent()
												.toString());
		System.out.println(System.getProperty("java.class.path"));
	}	
}
