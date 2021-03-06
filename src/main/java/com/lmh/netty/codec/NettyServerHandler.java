//package com.lmh.netty.codec;
//
///**
// * @program: hntc-mes
// * @description:
// * @author: LiaoMengHao
// * @create: 2021/11/15 9:25
// **/
//
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.util.CharsetUtil;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * @author LMH
// * @description 1、自定义一个Handler需要继续netty规定好的某个HandlerAdapter
// * 2、这时我们自定义一个Handler，才能称之为一个Handler
// * @date 2021/11/15 9:26
// */
//public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student msg) throws Exception {
//        System.out.println("客户端发送的数据 id=" + msg.getId() + "名字=" + msg.getName());
//    }
//}
////public class NettyServerHandler extends ChannelInboundHandlerAdapter {
////     读取数据实际（这里我们可以读取客户端发送的消息）
////     1、ChannelHandlerContext ctx：上下对象，含有管道pipeline，通道channel，地址
////     2、Object msg：就是客户端发送的数据 默认Object
////    @Override
////    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
////
////        // 读取从客户端发送的StudentPOJO.Student
////        StudentPOJO.Student student = (StudentPOJO.Student) msg;
////        System.out.println("客户端发送的数据 id=" + student.getId() + "名字=" + student.getName());
////    }
////
////    // 数据读取完毕
////    @Override
////    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
////        //writeAndFlush 是write + flush
////        // 将数据写入到缓存并刷新
////        // 一般来讲，我们对这个发送的数据进行编码
////        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端~", CharsetUtil.UTF_8));
////    }
////
////    // 处理异常，一般需要关闭通道
////
////    @Override
////    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
////        ctx.channel().close();
////    }
////}
