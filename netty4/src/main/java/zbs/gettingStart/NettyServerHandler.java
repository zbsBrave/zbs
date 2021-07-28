package zbs.gettingStart;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * 1，继承 ChannelInboundHandler，它提供了可以覆盖的各种事件处理程序方法
 * 2，在channelRead()这里重写事件处理程序方法。每当从客户端接收到新数据时，就使用接收到的消息来调用此方法。在此示例中，接收到的消息的类型为ByteBuf
 *      注意：一定要释放ByteBuf，通常在 finally里 使用ReferenceCountUtil.release(msg)来释放。
 * 3，抛出异常时会执行exceptionCaught()，可以在关闭连接之前发送带有错误代码的响应消息
 * @author zhangbaisen
 * @createTime 2021/4/1 10:00
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //1，读数据
//        ByteBuf in = (ByteBuf) msg;
//        try {
//            // Do something with msg
//            while (in.isReadable()) {
//                System.out.print((char) in.readByte());
//                System.out.flush();
//            }
//        } finally {
//            // ((ByteBuf) msg).release()
//            ReferenceCountUtil.release(msg);
//        }
        
        //2，写数据
        //ChannelHandlerContext对象提供各种操作，使您能够触发各种I/O事件和操作。在这里，我们调用write（Object）来逐字写入接收到的消息。
        // 请注意，我们没有像上面的读数据示例中那样释放接收到的消息。这是因为当它被写到电线上时，Netty会为你释放它
        ctx.write(msg);
        //ctx.write不会将消息写到wire上，它在内部缓冲，需要ctx.flush。或者，直接使用ctx.writeAndFlush
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭连接之前发送带有错误代码的响应消息

        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
