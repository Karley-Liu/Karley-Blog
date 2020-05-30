package com.lin.karley.Netty;

import com.lin.karley.Netty.WebSocket.MyTextWebSocketFrameHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.stereotype.Component;

import java.util.logging.Handler;

/**
 * @Description Description
 * @Author Karley LIn
 * @Date Created in 2020/5/30
 */
//@Component
public class NettyServer {

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast(new NettyServerHandler());

                    //因为给予http协议，使用http编码和解码器
                    pipeline.addLast(new HttpServerCodec());
                    //以块方式写，添加chunkedWeiter
                    pipeline.addLast(new ChunkedWriteHandler());
                    /**
                     *
                     */
                    pipeline.addLast(new HttpObjectAggregator(8192));
                    pipeline.addLast(new WebSocketServerProtocolHandler("/"));
                    pipeline.addLast(new MyTextWebSocketFrameHandler());
                }
            });
            System.out.println("服务器 is ready...");
            ChannelFuture channelFuture = bootstrap.bind(8081).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
