package com.lmh.netty.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer());// 自定义一个初始化对象

            ChannelFuture localhost = bootstrap.connect("127.0.0.1", 52240).sync();
            localhost.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
