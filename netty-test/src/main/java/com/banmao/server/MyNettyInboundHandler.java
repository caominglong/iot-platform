package com.banmao.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Company: 杭州领图信息科技有限公司
 *
 * @description: Sharable表示一个ChannelHandler可以被多个Channel安全地共享
 * @author: banmao
 * @date: 2022/8/11 16:45
 */
@ChannelHandler.Sharable
public class MyNettyInboundHandler extends ChannelInboundHandlerAdapter {

    /**
     * 每条消息都会调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 将消息记录到控制台
        ByteBuf in = (ByteBuf) msg;
        System.out.println("System received：" + in.toString(CharsetUtil.UTF_8));
        // super.channelRead(ctx, msg);
        // 将接收到到消息写给发送者，而不冲刷出战消息
        ctx.write(in);
        System.out.println("开始中断线程");
        Thread.sleep(10000);
        System.out.println("这里也在");
    }

    /**
     * 最后一条消息时，通知完成
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 将未决消息冲刷到远程节点，并且关闭该channel
        System.out.println("channelReadComplete进来了" +  System.currentTimeMillis());
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        // super.channelReadComplete(ctx);
    }

    /**
     * 异常抛出时会被调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        // super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
