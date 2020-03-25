package com.rafel.netty.websocket;

import com.sun.corba.se.spi.activation.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class WSServer {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup fatherGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(fatherGroup, childGroup).childHandler(new WSServerInit()).channel(NioServerSocketChannel.class);

            ChannelFuture channelFuture = serverBootstrap.bind(8089).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            fatherGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }

    }
}
