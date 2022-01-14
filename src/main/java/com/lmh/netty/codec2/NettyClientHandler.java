//package com.lmh.netty.codec2;
//
//import com.lmh.netty.codec.StudentPOJO;
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import io.netty.util.CharsetUtil;
//
//import java.util.Random;
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
//        // 随机发送Student 或者 Worker 对象
//        int random = new Random().nextInt(3);
//        MyDataInfo.MyMessage message = null;
//        if (0 == random) { // 发送Student对象
//            message = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.StudentType).setStudent(MyDataInfo.Student.newBuilder().setId(5).setName("lmh").build()).build();
//        } else {
//            message = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.WorkerType).setWorker(MyDataInfo.Worker.newBuilder().setAge(15).setName("lmh").build()).build();
//        }
//
//        ctx.writeAndFlush(message);
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
