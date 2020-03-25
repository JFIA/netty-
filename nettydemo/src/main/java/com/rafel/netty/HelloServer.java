package com.rafel.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/***
 * 实现客户端发送请求，服务器返回hello
 */
public class HelloServer {

    public static void main(String[] args) throws InterruptedException {

        // 定义一对线程组
        // 主线程组,用于客户端连接，但是不做任何处理，和老板一样，不做事
        EventLoopGroup fatherGroup = new NioEventLoopGroup();
        // 从线程组，父线程组会将任务丢给他，负责处理任务
        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {

            // netty服务器的创建，ServerBootstrap是启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 创建主从线程组，设置nio双向通道，设置子处理器，处理child线程组
            serverBootstrap.group(fatherGroup, childGroup).childHandler(new HelloServerInit()).channel(NioServerSocketChannel.class);

            // 启动server绑定端口8088，方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
            // 监听关闭的channel，方式为同步
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 关闭主从线程
            fatherGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }

    }
}
