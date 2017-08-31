package com.jjf.io;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringReader;
/**
 * Created by jjf_lenovo on 2017/5/23.
 */
//×Ö·ûÁ÷
public class BasicFileOutput {
    static String file = "C:/M.txt";

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new StringReader(
                InputStreamTest.read("C:\\Users\\jjf_lenovo\\Desktop\\M.txt")));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
                file)));
        String s;
        while ((s = in.readLine()) != null) {
            out.println(s);
        }
        out.close();
        System.out.println(InputStreamTest.read(file));
    }
}
