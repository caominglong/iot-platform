package com.banmao.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author banmao
 * @version V1.0.0
 * @date 2022/8/12 下午9:04
 * @description
 */
public class MyNettyServer {

    private final int port;

    public MyNettyServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {

        if (args.length != 1) {
            System.out.println("Usage：" + MyNettyServer.class.getSimpleName() + "<port>");
        }
        int port = Integer.parseInt(args[0]);
        new MyNettyServer(port).start();
    }

    private void start() throws InterruptedException {
        final MyNettyInboundHandler serverHandler = new MyNettyInboundHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(serverHandler);
                        }
                    });
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}