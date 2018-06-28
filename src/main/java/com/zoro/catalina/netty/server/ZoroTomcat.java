package com.zoro.catalina.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * Demo class
 *
 * @author dubber
 * @date 2018/6/29
 */
public class ZoroTomcat {

    public static void main(String[] args) {
        try {
            new ZoroTomcat().start(8080);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(int port) throws Exception {

        // 使用netty 的主从模型
        // Boss线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // worker线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            //Netty服务
            ServerBootstrap server = new ServerBootstrap();

            server.group(bossGroup, workerGroup)
                    // 主线程处理类
                    .channel(NioServerSocketChannel.class)
                    // 子线程处理类，Handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel client) throws Exception {
                            // 无锁话串行编程

                            // 3 业务逻辑链路，编码器，编码输出
                            client.pipeline().addLast(new HttpResponseEncoder());
                            // 2 解码器，解码网上操作
                            client.pipeline().addLast(new HttpRequestDecoder());

                            // 1 业务逻辑处理，自己实现；介绍请求，往上操作
                            client.pipeline().addLast(new ZoroTomcatHandler());

                        }
                    })
                    //配置信息
                    //主线程的配置信息
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 子线程配置
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = server.bind(port).sync();
            System.out.println("Zoro tomcat 已经启动！！");
            f.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
