package com.lmh.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

// Scattering : 将数据写入到buffer时，可以采用buffer数组，依次写入 【分散】
// Gathering: 从buffer读取数据时，可以采用buffer数组，依次读
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws IOException {
        // 使用ServerSocketChannel和SocketChannel网络
        ServerSocketChannel server = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        // 绑定端口到socket，并启动
        server.socket().bind(inetSocketAddress);

        // 创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        // 等待客户端连接（telnet）
        SocketChannel accept = server.accept();

        // 假定从客户端接受8个字节
        int messageLength = 8;
        // 循环读取
        while (true) {
            int byteRead = 0;
            while (byteRead < messageLength) {
                long read = accept.read(byteBuffers);
                byteRead += read; //累计读取字节数
                System.out.println("byteRead=" + byteRead);
                // 使用流打印,看看当前buffer的position和limit
                Arrays.asList(byteBuffers).stream().map(buffer -> "position=" + buffer.position() + ",limit=" + buffer.limit()).forEach(System.out::println);

            }

            // 将所有的buffer进行flip
            Arrays.asList(byteBuffers).forEach(Buffer::flip);

            // 将数据读出显示到客户端
            long byteWrite = 0;
            while (byteWrite < messageLength) {
                long write = accept.write(byteBuffers);
                byteWrite += write;
            }

            //将所有的buffer 进行clear
            Arrays.asList(byteBuffers).forEach(Buffer::clear);

            System.out.println("byteRead="+byteRead+" byteWrite=" + byteWrite + ",messageLength="+messageLength);
        }
    }
}
