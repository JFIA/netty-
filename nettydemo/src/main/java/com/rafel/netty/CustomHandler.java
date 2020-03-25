package com.rafel.netty;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/***
 * 自定义助手类
 */

// SimpleChannelInboundHandler：对于请求来讲相当于：入境
public class CustomHandler extends SimpleChannelInboundHandler<HttpObject> {

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {

        // 获得管道
        Channel channel = channelHandlerContext.channel();

        if (httpObject instanceof HttpRequest) {
            System.out.println(channel.remoteAddress());

            // 定义发送的数据
            ByteBuf content=Unpooled.copiedBuffer("HelloNetty", CharsetUtil.UTF_8);

            // 构建一个httpResponse
            FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);

            // 为响应增加数据类型和长度
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            // 把响应刷到客户端
            channelHandlerContext.writeAndFlush(response);
        }

    }
}
