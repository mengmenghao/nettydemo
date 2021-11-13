package com.lmh.nio.groupchat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @program: hntc-mes
 * @description:
 * @author: LiaoMengHao
 * @create: 2021/11/13 15:30
 **/
public class GroupChatClient {
    // 定义相关属性
    private final String HOST = "127.0.0.1";// 服务器IP
    private final int PORT = 6667;// 服务器端口
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;

    // 完成初始化操作
    public GroupChatClient() throws IOException {
        selector = Selector.open();
        // 连接服务器
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST,PORT));
        socketChannel.configureBlocking(false);
        // 将channel注册到selector
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 得到username
        userName = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(userName + "is ok ....");


    }

    // 向服务器发送消息
    public void sendInfo(String info) {
        info = userName + "说" + info;

        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // 从服务器端回复的消息
    public void readInfo() {
        try {
            int readChannels = selector.select();
            if (readChannels > 0) {//即可以用的通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        // 得到相关的通道
                        SocketChannel sc = (SocketChannel) key.channel();
                        // 得到一个buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        // 读取
                        sc.read(buffer);
                        // 把读取到的缓冲区数据转成字符串
                        java.lang.String msg = new java.lang.String(buffer.array());
                        System.out.println(msg.trim());

                    }
                }
                iterator.remove(); // 删除当前的SelectionKey，防止重复操作
            } else {
//                System.out.println("没有可用的通道....");

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        // 启动客户端
        GroupChatClient chatClient = new GroupChatClient();
        // 启动一个线程
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    chatClient.readInfo();
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        // 发送数据给服务器端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            chatClient.sendInfo(s);
        }
    }
}
