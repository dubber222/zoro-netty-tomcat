package com.zoro.catalina.http;

import io.netty.channel.ChannelHandlerContext;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.io.UnsupportedEncodingException;

/**
 * Demo class
 *
 * @author dubber
 * @date 2018/6/29
 */
public class ZoroResponse {
    private ChannelHandlerContext ctx;
    private HttpRequest msg;

    public ZoroResponse(ChannelHandlerContext ctx, HttpRequest msg) {
        this.ctx = ctx;
        this.msg = msg;
    }

    public void write(String out) throws UnsupportedEncodingException {
        try {
            System.out.println(out);
            if(out == null){
                System.out.println("out is null!!!");
                return;
            }
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.OK
                    , Unpooled.wrappedBuffer(out.getBytes("UTF-8")));

            response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/json");
            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
            response.headers().set(HttpHeaders.Names.EXPIRES, 0);
            if (HttpHeaders.isKeepAlive(msg)) {
                response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            }
            ctx.write(response);
        } finally {
            ctx.flush();
        }
    }
}
