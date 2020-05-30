package com.lin.karley.Netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;


/**
 * @Description Description
 * @Author Karley LIn
 * @Date Created in 2020/5/30
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
//        super.channelRead(ctx,msg);
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是："+buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址："+ ctx.channel().remoteAddress());
    }

    //数据读取完毕
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        //数据读入缓存并刷新
        //一般将我们对发送的数据编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,Client",CharsetUtil.UTF_8));
    }

    //处理异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception{
        ctx.channel().close();
    }
}
