package com.lmh.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel03 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("src\\main\\resources\\1.txt");
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer allocate = ByteBuffer.allocate(512);
        while (true) {
            allocate.clear();
            int read = fileInputStreamChannel.read(allocate);
            if (read == -1) {
                break;
            }
            allocate.flip();
            fileOutputStreamChannel.write(allocate);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
