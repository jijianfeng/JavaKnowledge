package com.jjf.string;

public class ParamTransfer {
	public void chgValue(StringBuffer param) {
		StringBuffer sb = new StringBuffer("Hi ");
		param = sb;
		param.append("World!");
		System.out.println(param);
	}

	public static void main(String[] args) {
		ParamTransfer pt = new ParamTransfer();
		StringBuffer value = new StringBuffer("Hello ");
		pt.chgValue(value);
		System.out.println(value);
	}
}