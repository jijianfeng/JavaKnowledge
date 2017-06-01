package com.jjf.io;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by jjf_lenovo on 2017/6/1.
 */
public class NIOTest {

    /**
     * 写
     * @throws IOException
     */
    @Test
    public void ChannelAndBufferTest()throws IOException{
        FileOutputStream fout = new FileOutputStream( "D:\\test\\File.txt" );
        FileChannel fc = fout.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate( 1024 );
        byte[] message = "1234567890".getBytes();
        for (int i=0; i<message.length; ++i) {
            buffer.put( message[i] );
        }
        buffer.flip();
        fc.write(buffer);
    }

    /**
     * 读写
     * @throws IOException
     */
    @Test
    public void testCopy() throws IOException {
        FileInputStream fin = new FileInputStream( "D:\\test\\File.txt" );
        FileOutputStream fout = new FileOutputStream( "D:\\test\\out.txt" );
        FileChannel fcin = fin.getChannel();
        FileChannel fcout = fout.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate( 1024 );
        while (true) {
            buffer.clear();
            int r = fcin.read( buffer );
            if (r==-1) {
                break;
            }
            buffer.flip();
            fcout.write( buffer );
        }
    }

    /**
     * 测试缓冲区分片
     */
    @Test
    public void testSlice(){
        ByteBuffer buffer = ByteBuffer.allocate( 1024 );
        for (int i=0; i<buffer.capacity(); ++i) {
            buffer.put( (byte)i );
        }
        //分片
        buffer.position( 3 );
        buffer.limit( 7 );
        ByteBuffer slice = buffer.slice();
        //修改分片中的内容
        for (int i=0; i<slice.capacity(); ++i) {
            byte b = slice.get( i );
            b *= 11;
            slice.put( i, b );
        }
        buffer.position( 0 );
        buffer.limit( buffer.capacity() );
        while (buffer.remaining()>0) {
            System.out.println( buffer.get() );
        }
    }
}
