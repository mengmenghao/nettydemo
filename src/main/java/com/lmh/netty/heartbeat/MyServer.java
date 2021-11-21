package com.lmh.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        // 创建连个线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) //在bossGroup增加一个日志处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 得到pipeline
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 加入一个netty提供的IdleStateHandler
                            /**
                             * 说明
                             * 1、IdleStateHandler 是netty提供的处理空闲状态的处理器
                             * 2、long readerIdleTime ：有多长时间没有读，就会发送一个心跳检测包，检测是否还是连接的状态
                             * 3、long writerIdleTime ：表示有多长时间没有写，就会发送一个心跳检测包，检测是否还是连接的状态
                             * 4、long allIdleTime ： 表示多长时间既没有读也没有写，就会发送一个心跳检测包，检测是否还是连接的状态
                             *
                             * 5、文档说明
                             *  Triggers an {@link IdleStateEvent} when a {@link Channel} has not performed
                             *  read, write, or both operation for a while.
                             *
                             * 6、当 IdleStateEvent 触发后，就会传递给管道的下一个handler去处理
                             * 通过调用（触发）下一个handler的userEventTriggered，在该方法中去处理 IdleStateEvent（读空闲，写空闲，读写空闲）
                             */
                            pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
                            // 加入一个对空闲检查进一步处理的handler（自定义）
                            pipeline.addLast(new MyServerHandler());
                        }
                    });
            // 启动服务器
            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
