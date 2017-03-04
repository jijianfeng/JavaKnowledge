package com.jjf.collection;

import java.util.Enumeration;
import java.util.Vector;

import javax.xml.stream.events.EndElement;

public class VectorTest {
	public static void main(String args[]){
		Vector ve = new Vector();
		ve.add(1);
		ve.add(2);
		ve.add(3);
		ve.add(4);
		ve.add(5);
		ve.add(6);
		ve.add(3, 7);
		Enumeration ems = ve.elements();
		while(ems.hasMoreElements()){
			System.out.println(ems.nextElement().toString());
		}
	}
}
