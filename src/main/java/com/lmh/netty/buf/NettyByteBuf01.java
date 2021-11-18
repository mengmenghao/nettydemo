package com.lmh.netty.buf;

import com.sun.org.apache.xalan.internal.xsltc.dom.SortingIterator;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import sun.applet.Main;

public class NettyByteBuf01 {
    public static void main(String[] args) {
        // 创建一个bytebuf
        // 说明
        // 1、创建对象，该对象包含一个数组arr，是一个byte【10】
        // 2、在netty的buffer中，不需要使用flip进行反转
        // 底层维护了一个readerinex 和 writerindex 和 capacity,将buffer分成三个区域
        // 0---readerindex 已经读取的区域
        // readerindex --- writerindex 可读的区域
        // writerindex -- capacity可写的区域
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        System.out.println("capacity="+buffer.capacity());
        // 输出
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.getByte(i));
        }
    }
}
