package com.jjf.io;

import java.io.*;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by jjf_lenovo on 2017/5/31.
 */

/**
 * 关于传统IO流与NIO"映射文件访问"的速度比较
 */
public abstract class MapMemeryBufferTest {
    private static int numOfInts = 4000000;

    private String name;

    public MapMemeryBufferTest(String name){
        this.name=name;
    }

    public void runTest(){
        System.out.print(name+" ");
        try {
            long start = System.currentTimeMillis();
            test();
            System.out.println(System.currentTimeMillis()-start);
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public abstract void test() throws IOException;

    private static MapMemeryBufferTest[] tests = {
            new MapMemeryBufferTest("Stream write"){
                @Override
                public void test() throws IOException {
                    DataOutputStream out = new DataOutputStream(
                            new BufferedOutputStream(
                                new FileOutputStream(new File("temp.tmp"))
                            )
                    );
                    for(int i=0;i<numOfInts;i++){
                        out.write(i);
                    }
                    out.close();
                }
            },
            new MapMemeryBufferTest("Mapped write"){
                @Override
                public void test() throws IOException {
                    FileChannel fc = new RandomAccessFile("temp.tmp","rw").getChannel();
                    IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE,0,fc.size()).asIntBuffer();
                    for(int i=0;i<numOfInts;i++){
                        ib.put(i);
                    }
                    fc.close();
                }
            },
            new MapMemeryBufferTest("Stream read"){
                public void test() throws IOException {
                    DataInputStream dis = new DataInputStream(
                            new BufferedInputStream(
                                    new FileInputStream(new File("temp.tmp"))
                            )
                    );
                    for(int i=0;i<numOfInts;i++){
                        dis.readInt();
                    }
                    dis.close();
                }
            },
    };

    public static void main(String[] args) throws Exception {
        for(MapMemeryBufferTest test:tests){
            test.runTest();
        }
    }
}
