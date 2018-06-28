package com.zoro.catalina.http;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

/**
 * Demo class
 *
 * @author dubber
 * @date 2018/6/29
 */
public class ZoroRequest {
    private ChannelHandlerContext ctx;
    private HttpRequest msg;

    public ZoroRequest(ChannelHandlerContext ctx, HttpRequest msg) {
        this.ctx = ctx;
        this.msg = msg;
    }

    public String getUri(){
        return msg.getUri();
    }

    public String getMethod(){
        return msg.getMethod().name();
    }

    public Map<String, List<String>> getParameters(){
        QueryStringDecoder decoder = new QueryStringDecoder(getUri());
        return decoder.parameters();
    }

    public String getParameter(String name){
        Map<String, List<String>> params =  getParameters();
        List<String> param = params.get(name);
        if(param != null){
            return param.get(0);
        }
        return null;
    }
}
