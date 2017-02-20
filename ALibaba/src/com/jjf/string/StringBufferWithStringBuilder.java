package com.jjf.string;

import org.junit.Test;

public class StringBufferWithStringBuilder {

    public void testString() {
        long start = System.currentTimeMillis();
        String str = "";
        for (int i = 0; i < 20000; i++) {
            str = str + i + ",";
        }
        System.out.println(System.currentTimeMillis() - start);
//        System.out.println(str.length());
    }

    public void testStringBuffer() {
        long start = System.currentTimeMillis();

        StringBuffer sbuf = new StringBuffer();
        for (int i = 0; i < 200000; i++) {
            sbuf.append(i + ",");
        }
        System.out.println(System.currentTimeMillis() - start);
//        System.out.println(sbuf.length());
    }

    public void testStringBulider() {
        long start = System.currentTimeMillis();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 200000; i++) {
            builder.append(i + ",");
        }
        System.out.println(System.currentTimeMillis() - start);
//        System.out.println(builder.length());
    }

    @Test
    public void test(){
        testString();
        testStringBuffer();
        testStringBulider();
    }
    
}