package com.jjf.collection;

import java.util.ArrayList;  
import java.util.HashSet;  
import java.util.List;  
import java.util.Set;  
public class ArrayListAndHashSet {  
	int value;  
	
	ArrayListAndHashSet(int value) {  
		this.value = value;  
	}  
	public boolean equals(Object obj) {  
		if (obj instanceof ArrayListAndHashSet) {  
			ArrayListAndHashSet foo = (ArrayListAndHashSet) obj;  
			return this.value == foo.value;  
		} 
		else {  
			return false;  
		}  
	}  
	public static void main(String[] args) {  
		List list = new ArrayList();  
		Set set = new HashSet();  
		list.add(new ArrayListAndHashSet(1));  
		set.add(new ArrayListAndHashSet(1));  
		System.out.println(list.contains(new ArrayListAndHashSet(1)));   //结果一：true  
		System.out.println(set.contains(new ArrayListAndHashSet(1)));  //结果二 ：false  
		System.out.println((new ArrayListAndHashSet(1)).equals(new ArrayListAndHashSet(1)));     
	}  
}