package com.jjf.io;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jjf_lenovo on 2017/5/23.
 */
//1、缓冲输入文件。这段代码是从磁盘读入InputStreamTest.java文件，然后转换成字符串。输出就是将源文件原样输出。
public class InputStreamTest  {
    public static void main(String[] args) throws Exception {
        System.out.println(read("C:\\Users\\jjf_lenovo\\Desktop\\M.txt"));
    }

    public static String read(String filename) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String s;
        StringBuffer sb = new StringBuffer();
        while ((s = br.readLine()) != null) {
            sb.append(s + "\n");
        }
        br.close();
        return sb.toString();
    }
}
