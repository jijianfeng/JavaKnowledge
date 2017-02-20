package com.jjf.collection;

import java.util.HashMap;
import java.util.Map;

public class T<K> {
	private static Object[] obj;
	private static int length;
	
	public T(){
		obj = new Object[10];
	}
	
	public int add(int i,K k){
		obj[i] = k;
		return i;
	}
	
	public K get(int i){
		return (K)obj[i];
	}
	
	public static void main(String args[]){
		test.main(args);
	}
}


class test{
	public static void main(String args[]){
		T t = new T();
		Map map = new HashMap();
		map.put("hash", "123");
		t.add(1,map);
		System.out.println(t.get(1).toString());
		map.put("hash", "46");
		System.out.println(t.get(1).toString());
	}
}