package com.jjf.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by jjf_lenovo on 2017/5/23.
 */
public class BinaryFile {
    public static byte[] read(File file) throws IOException {
        BufferedInputStream bf = new BufferedInputStream(new FileInputStream(
                file));
        try {
            byte[] data = new byte[bf.available()];
            bf.read(data);
            return data;
        } finally {
            bf.close();
        }
    }

    /* the param is the path of a file */
    public static byte[] read(String file) throws IOException {
        return read(new File(file).getAbsoluteFile());
    }
}
