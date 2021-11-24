package com.lmh.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("服务器的IP" + ctx.channel().remoteAddress());
        System.out.println("收到服务器的消息=" + msg);
    }

    // 重写 channelActive 发送数据
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler 发送数据");
        // 分析
        // 1、“asdasdasd”是16字节
        // 2、该处理器的前一个handler是 MyLongToByteEncoder
        // 3、MyLongToByteEncoder 父类 MessageToByteEncoder 判断当前msg 是不是应该处理的类型，如果是就出来，如果不是就跳过encoder
//        ctx.writeAndFlush(Unpooled.copiedBuffer("asdasdasd", CharsetUtil.UTF_8));
        ctx.writeAndFlush(123456L); // 发送的是一个long
    }
}
