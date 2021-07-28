package zbs.gettingStart;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty服务器，需要 NettyServerHandler
 * @author zhangbaisen
 * @createTime 2021/4/1 9:51
 */
public class NettyServer {
    /** 端口 */
    private int port;
    public NettyServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        //1, NioEventLoopGroup是处理I / O操作的多线程事件循环。NettyEventLoopGroup为不同类型的传输提供了各种实现
        //在此示例中，我们正在实现服务器端应用程序，因此NioEventLoopGroup将使用两个应用程序。第一个通常称为“老板”，接受传入的连接。第二个通常称为“工人”，一旦老板接受连接并将注册的连接注册给工人，便处理已接受连接的流量
        //使用多少线程以及如何将它们映射到创建的Channels取决于EventLoopGroup实现，甚至可以通过构造函数进行配置
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //2, ServerBootstrap是设置服务器的帮助程序类。您也可以Channel直接使用来设置服务器。但是这是一个繁琐的过程，在大多数情况下您无需这样做
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    
                    //3, 指定使用NioServerSocketChannel用于实例化新类Channel以接受传入连接的类
                    .channel(NioServerSocketChannel.class) 
                    
                    //4, 接收一个新channel。The handler specified here will always be evaluated by a newly accepted Channel
                    //ChannelInitializer是一个特殊的handler ，用于帮助用户配置新的Channel
                    //通过添加some handlers such as nettyServerHandler 来配置channel的ChannelPipeline，以实现网络应用程序
                    //随着程序变得更复杂，你可以向pipeline中添加更多的handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    })
                    
                    //5, 设置特定于Channel实现的参数。我们正在编写一个TCP / IP服务器，因此我们可以设置套接字选项，例如tcpNoDelay和keepAlive
                    .option(ChannelOption.SO_BACKLOG, 128)
                    
                    //6, 你有没有注意到option()和childOption()？ option()用于NioServerSocketChannel接受传入的连接。
                    // childOption() is for the Channels accepted by the parent ServerChannel, which is NioSocketChannel in this case.
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        System.out.println(args);
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        // 启动服务。启动后可以通过  telnet localhost 8080  来测试
        new NettyServer(port).run();
    }

}
