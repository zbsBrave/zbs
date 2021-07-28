package simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangbaisen
 * @createTime 2021/5/27 14:29
 */
public class NettyServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //设定IdleStateHandler心跳检测每五秒进行一次读检测，如果五秒内ChannelRead()方法未被调用则触发一次userEventTrigger()方法
                            socketChannel.pipeline().addLast(new IdleStateHandler(5,0,0, TimeUnit.SECONDS));
                            //自定义心跳检测处理器，会监听IdleStateHandler触发的userEventTrigger事件
                            socketChannel.pipeline().addLast(new HeartChannelHandler());
                            
                            //以下为http相关handler
                            //1、HttpServerCodec 是 netty 提供的处理http的编解码器
                            socketChannel.pipeline().addLast("MyHttpServerCodec", new HttpServerCodec());
                        }
                    });
            ChannelFuture future = bootstrap.bind(9000).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
