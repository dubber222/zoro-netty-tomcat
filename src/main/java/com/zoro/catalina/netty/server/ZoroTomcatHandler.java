package com.zoro.catalina.netty.server;

import com.zoro.catalina.http.ZoroRequest;
import com.zoro.catalina.http.ZoroResponse;
import com.zoro.catalina.servlets.DemoServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

/**
 * Demo class
 *
 * @author dubber
 * @date 2018/6/29
 */
public class ZoroTomcatHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead!!");
        if (msg instanceof HttpRequest) {
            HttpRequest msg1 = (HttpRequest) msg;

            ZoroRequest request = new ZoroRequest(ctx,msg1);
            ZoroResponse response = new ZoroResponse(ctx,msg1);

            new DemoServlet().doPost(request,response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
