package com.lmh.netty.groupchat;

import com.sun.org.apache.bcel.internal.generic.NEW;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import javax.sound.midi.Soundbank;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    // 使用一个hasmap 管理
//    public static Map<String, Channel> channels = new HashMap<String,Channel>();
//    public static Map<User, Channel> channels2 = new HashMap<User,Channel>();
    // 定义一个channel组，管理所有channel
    // GlobalEventExecutor.INSTANCE 是全局的事件执行器，是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // handlerAdded 表示连接建立，一旦连接，第一个被执行
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 将该客户加入聊天的信息推送给其他在线的客户端
        /**
         * channelGroup.writeAndFlush会将channelGroup中所有的channnel遍历，并发送消息
         */
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + sdf.format(new Date()) + "加入聊天");
        channelGroup.add(channel);

//        channels.put("id100",channel);
//        channels2.put(new User(10,"123"),channel);
    }

    // 表示channel处于活动状态，提示xxx上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "上线了~");
    }

    // 表示channel 处于不活动状态，提示xxx离线了
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "离线了~");
    }

    // 断开连接，将xx客户离开信息推送给当前在线的客户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "离开了");
        System.out.println("channelGroup size" + channelGroup.size());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 获取到当前channel
        Channel channel = ctx.channel();

        // 遍历channelGroup，根据不同情况，回送不同的消息
        channelGroup.forEach(ch->{
            if (channel != ch){//不是当前的channel，转发消息
                ch.writeAndFlush("[客户]" + channel.remoteAddress() +" 发送消息" + msg);
            } else {
                ch.writeAndFlush("[自己]发送了消息" + msg +"\n");
            }
        });
    }

    // 发生异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 关闭
        ctx.close();
    }
}
