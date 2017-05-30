package com.jjf.swordoffer;

/**
 * 请实现一个函数，将一个字符串中的空格替换成“%20”。
 * 例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
 * @author jjf_lenovo
 * 时间限制：1秒 空间限制：32768K
 * 2017年2月24日19:04:01
 * 运行时间：36ms 占用内存：636k
 */
public class Test2 {
	public static void main(String args[]){
		System.out.println(replaceSpace2(new StringBuffer("We Are Happy")));
	}
	public static String replaceSpace(StringBuffer str) {
    	return str.toString().replace(" ", "%20");//java简单  运行时间：36ms 占用内存：636k
    }
	
	public static String replaceSpace2(StringBuffer str) {
    	char chs[] = str.toString().toCharArray(); //其他语言思想  运行时间：运行时间：31ms 占用内存：503k
    	StringBuilder sb = new StringBuilder();
    	for(char ch :chs){
    		if(ch==' '){
//    			sb.append("%20");
				sb.append('%');
				sb.append('2');
				sb.append('0');
    		}
    		else{
    			sb.append(ch);
    		}
    	}
    	return sb.toString();
    }
}
