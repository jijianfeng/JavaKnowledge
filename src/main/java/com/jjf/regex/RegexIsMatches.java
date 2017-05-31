package com.jjf.regex;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Created by jjf_lenovo on 2017/5/31.
 */

/**
 *  字符与正则表达式是否匹配
 */
public class RegexIsMatches {
    /**
     * 练习匹配 + * ?
     */
    @Test
    public void test1(){
        //其实拆开来看 比较容易理解 runo o+ b
//        String pattern = "runoo+b";//"runoo+.*"//runoo+ //+ 号代表前面的字符必须至少出现一次（1次或多次）
//        String pattern = "runoo*b";  //* 号代表字符可以不出现,也可以出现一次或者多次（0次、或1次、或多次）。
        String pattern = "runo(o)?b"; //?号代表前面的字符最多只可以出现一次（0次、或1次）。
        System.out.println(isMatches(pattern,"runob"));
        System.out.println(isMatches(pattern,"runoob"));
        System.out.println(isMatches(pattern,"runoooooob"));
    }

    /**
     * 练习字符
     */
    @Test
    public void test2(){
        // 分词 i   \\s+  am  \\s  .*
        // \s 匹配任何空白字符 .* 表示任意
//        String pattern = "i\\s+am\\s.*";
        String pattern = "i(\\s)+am(\\s).*";
        System.out.println(isMatches(pattern,"i am boss"));
        System.out.println(isMatches(pattern,"i            am boss"));
        System.out.println(isMatches(pattern,"I  am boss"));
        System.out.println(isMatches(pattern,"iam boss"));
    }

    /**
     * 练习限定符
     */
    @Test
    public void test3(){
//        String pattern = "B(o){2}b";
        String pattern = "B(o){1,3}b";
        System.out.println(isMatches(pattern,"Bob"));
        System.out.println(isMatches(pattern,"Boob"));
        System.out.println(isMatches(pattern,"Booob"));
        System.out.println(isMatches(pattern,"Boooob"));
    }

    public static boolean isMatches(String pattern,String content){
        return Pattern.matches(pattern, content);
    }
}
