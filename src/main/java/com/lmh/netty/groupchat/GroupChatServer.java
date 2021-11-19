package com.lmh.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class GroupChatServer {
    private int port; // 监听端口
    public GroupChatServer(int port) {
        this.port = port;
    }
    // 编写run方法处理客户端的请求
    public void run() throws InterruptedException {
        // 创建两个线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 获取到pipeline
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 向pipeline加入解码器
                            pipeline.addLast("decoder",new StringDecoder());

                            // 向pipeline加入编码器
                            pipeline.addLast("encoder",new StringEncoder());
                            // 加入自己的业务处理handler
                            pipeline.addLast(null);
                        }
                    });
            System.out.println("netty服务器启动");
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

            // 监听关闭事件
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }


    public static void main(String[] args) {
        try {
            new GroupChatServer(7000).run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
