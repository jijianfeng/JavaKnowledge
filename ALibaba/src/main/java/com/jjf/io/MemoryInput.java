package com.jjf.io;

import java.io.StringReader;

/**
 * Created by jjf_lenovo on 2017/5/23.
 */
//2、从内存中读取。 read返回的是int类型的数据，所以在输出语句中用char做了强类型转换。该程序将一个一个的输出字符。
public class MemoryInput {
    public static void main(String[] args) throws Exception {
        StringReader in = new StringReader(
                InputStreamTest.read("C:\\Users\\jjf_lenovo\\Desktop\\M.txt"));
        int c;
        while ((c = in.read()) != -1) {
            System.out.print((char) c);
        }
        in.close();
    }
}
