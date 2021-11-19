package com.lmh.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class NettyByteBuf02 {
    public static void main(String[] args) {
        // 创建一个bytebuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world", StandardCharsets.UTF_8);

        // 使用相关的方法
        if (byteBuf.hasArray()) {//true
            byte[] content = byteBuf.array();

            // 将content转成字符串
            System.out.println(new String(content, StandardCharsets.UTF_8));

            System.out.println("byteBuf=" + byteBuf);

            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());

//            System.out.println(byteBuf.readByte());
            System.out.println(byteBuf.getByte(0));

            int len = byteBuf.readableBytes(); // 可读取的字节数
            System.out.println("len=" + len);

            for (int i = 0; i < len; i++) {
                System.out.println((char) byteBuf.getByte(i));
            }

            System.out.println(byteBuf.getCharSequence(0, 4, StandardCharsets.UTF_8));
            System.out.println(byteBuf.getCharSequence(4, 6, StandardCharsets.UTF_8));
        }
    }
}
