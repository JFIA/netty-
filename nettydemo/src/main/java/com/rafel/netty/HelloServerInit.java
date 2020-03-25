package com.rafel.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/***
 * 初始化器，channel注册后，会执行里面的初始化方法
 */
public class HelloServerInit extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel socketChannel) throws Exception {

        // 通过socketChannel获得管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        // HttpServerCodec是netty的助手类，可以理解为拦截器
        // 请求到服务端，需要做解码，响应到客户端做编码
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());

        // 自定义助手类，返回HelloNetty
        pipeline.addLast("CustomHandler", new CustomHandler());

    }
}
