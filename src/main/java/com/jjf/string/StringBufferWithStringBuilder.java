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

    @Test
    public void testCar(){
        int a=1,b=1,c=1,d=1,e=1,f=1,g=1,h=1,i=1,j=1,k=1;
        if(a%b!=(c+d)
                && ((e-f)==1)
                ||(g>h)
                ||(j>=k)
                ||(a<b)
                ||(b<=a)
                ||((a^b)==1)
                ||((a&b)==1)
                ||(a*b==1)
                ||(a/b==1)
                ){
            System.out.println(">>>");
        }
    }
    
}