//package com.lmh.netty.codec;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import io.netty.util.CharsetUtil;
//
///**
// * @program: hntc-mes
// * @description:
// * @author: LiaoMengHao
// * @create: 2021/11/15 10:31
// **/
//public class NettyClientHandler extends ChannelInboundHandlerAdapter {
//    // 当通达就绪就会触发
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
////        System.out.println("client" + ctx);
////        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,server:", CharsetUtil.UTF_8));
//
//        //发送一个Student 对象到服务器
//        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(4).setName("聊梦幻 测试").build();
//        ctx.writeAndFlush(student);
//    }
//
//    // 当通道有读取事件时触发
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("服务器回复的消息:" + buf.toString(CharsetUtil.UTF_8));
//        System.out.println("服务器地址：" + ctx.channel().remoteAddress());
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        cause.printStackTrace();
//        ctx.close();
//    }
//}
