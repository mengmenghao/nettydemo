package com.lmh.netty.simple;

/**
 * @program: hntc-mes
 * @description:
 * @author: LiaoMengHao
 * @create: 2021/11/15 9:25
 **/

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * @author LMH
 * @description 1、自定义一个Handler需要继续netty规定好的某个HandlerAdapter
 * 2、这时我们自定义一个Handler，才能称之为一个Handler
 * @date 2021/11/15 9:26
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    // 读取数据实际（这里我们可以读取客户端发送的消息）
    // 1、ChannelHandlerContext ctx：上下对象，含有管道pipeline，通道channel，地址
    // 2、Object msg：就是客户端发送的数据 默认Object
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 比如这里有一个非常耗时的业务-》异步执行-》提交该channel对应的NIOEventLoop的taskQueue中

        // 解决方案1 用户程序自定义的普通任务
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端 哈哈2", CharsetUtil.UTF_8));
            }
        });

        // 用户自定义定时任务-》 该任务提交到scheduleTaskQueue中
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端 哈哈3", CharsetUtil.UTF_8));
            }
        }, 5, TimeUnit.SECONDS);
//        Thread.sleep(1000 * 10);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端 哈哈2", CharsetUtil.UTF_8));
        System.out.println("go on ....");
//        System.out.println("server ctx=" + ctx);
//
//        // 将msg转成一个bytbuf
//        // bytebuf是Netty提供的，不是NIO的ByteBuffer
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("客户端发送消息是：" + buf.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
    }

    // 数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //writeAndFlush 是write + flush
        // 将数据写入到缓存并刷新
        // 一般来讲，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端~", CharsetUtil.UTF_8));
    }

    // 处理异常，一般需要关闭通道

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
