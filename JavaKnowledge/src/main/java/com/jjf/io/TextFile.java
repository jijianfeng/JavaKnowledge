package com.jjf.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jjf_lenovo on 2017/5/23.
 */
public class TextFile extends ArrayList<String> {
    private static final long serialVersionUID = -1942855619975438512L;

    // Read a file as a String
    public static String read(String filename) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File(
                    filename).getAbsoluteFile()));
            String s;
            try {
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            } finally {
                in.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    // Write a single file in one method call
    public static void write(String fileName, String text) {
        try {
            PrintWriter out = new PrintWriter(
                    new File(fileName).getAbsoluteFile());
            try {
                out.print(text);
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Read a file,spilt by any regular expression
    public TextFile(String fileName, String splitter) {
        super(Arrays.asList(read(fileName).split(splitter)));
        if (get(0).equals(""))
            remove(0);
    }

    // Normally read by lines
    public TextFile(String fileName) {
        this(fileName, "\n");
    }

    public void write(String fileName) {
        try {
            PrintWriter out = new PrintWriter(
                    new File(fileName).getAbsoluteFile());
            try {
                for (String item : this)
                    out.println(item);
            } finally {
                out.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // test,I have generated a file named data.d at the root
    public static void main(String[] args) {

        /* read() test */
        System.out.println(read("C:\\Users\\jjf_lenovo\\Desktop\\M.txt")); // testing is OK!

        /* write() test */
        write("C:/out.txt", "helloworld\negg"); // testing is OK!

        /* constractor test */
        TextFile tf = new TextFile("C:\\Users\\jjf_lenovo\\Desktop\\M.txt"); // testing is OK!

    }
}
