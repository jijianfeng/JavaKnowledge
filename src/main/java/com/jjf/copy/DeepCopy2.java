package com.jjf.copy;

import java.io.*;

//Serialization is time-consuming
class Professor2 implements Serializable {
	/**
   * 
   */
	private static final long serialVersionUID = 1L;
	String name;
	int age;

	Professor2(String name, int age) {
		this.name = name;
		this.age = age;
	}
}

class Student2 implements Serializable {
	/**
   * 
   */
	private static final long serialVersionUID = 1L;
	String name;// 常量对象。
	int age;
	Professor2 p;// 学生1和学生2的引用值都是一样的。

	Student2(String name, int age, Professor2 p) {
		this.name = name;
		this.age = age;
		this.p = p;
	}

	public Object deepClone() throws IOException, OptionalDataException,
			ClassNotFoundException {
		// 将对象写到流里
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(this);
		// 从流里读出来
		ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
		ObjectInputStream oi = new ObjectInputStream(bi);
		return (oi.readObject());
	}

}

public class DeepCopy2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws OptionalDataException,
			IOException, ClassNotFoundException {
		long t1 = System.currentTimeMillis();
		Professor2 p = new Professor2("wangwu", 50);
		Student2 s1 = new Student2("zhangsan", 18, p);
		Student2 s2 = (Student2) s1.deepClone();
		s2.p.name = "lisi";
		s2.p.age = 30;
		System.out.println("name=" + s1.p.name + "," + "age=" + s1.p.age); // 学生1的教授不改变。
//		long t2 = System.currentTimeMillis();
//		System.out.println(t2 - t1);
	}

}
