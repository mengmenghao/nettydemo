package com.lmh.netty.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        // 向管道加入处理器

        // 得到管道
        ChannelPipeline pipeline = channel.pipeline();

        // 加入一个netty 提供的httpserverCodec codec=>[coder - decoder] 编解码器
        // HttpServerCodec 说明
        // 1、HttpServerCodec时netty提供的处理http的编解码器
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());

        // 2、增加一个自定义的编解码器
        pipeline.addLast("MyTestHttpServerHandler",new TestHttpServerHandler());
    }
}
