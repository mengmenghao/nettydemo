package com.lmh.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

//MappedByteBuffer可让文件直接在内存（堆外内存）修改，操着系统不需要拷贝一次
public class MappedByteBufferTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile rw = new RandomAccessFile("2.txt", "rw");
        //获取通道
        FileChannel channel = rw.getChannel();

        /**
         * 参数1：FileChannel.MapMode.READ_WRITE 使用的读写模式
         * 参数2：0:可以直接修改的起始位置
         * 参数3：5：是映射到内存的大小(不是索引位置 )，即将1.txt的多少个字节映射到内存
         * 可以直接修改的范围是0-5
         * 实际类型 DirectByteBuffer
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(0, (byte) 'H');
        map.put(3, (byte) '9');
        rw.close();
    }
}
