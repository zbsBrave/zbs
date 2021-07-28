package zbs.gettingStart;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 本节中要实现的协议是时间协议。它与前面的示例不同，它发送一条消息，其中包含32位整数，不接收任何请求，并且一旦消息发送，就关闭连接。在本例中，您将学习如何构造和发送消息，以及在完成时关闭连接。
 * 因为我们将忽略任何接收到的数据，但要在建立连接后立即发送消息，因此这次无法使用channelRead（）方法。相反，我们应该重写channelActive（）方法。
 * @author zhangbaisen
 * @createTime 2021/4/1 13:49
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * the channelActive() method will be invoked when a connection is established and ready to generate traffic
     * channelActive()方法： 当连接建立并准备生成流量时调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //1，分配byteBuf用于发送消息
        //We are going to write a 32-bit integer, and therefore we need a ByteBuf whose capacity is at least 4 bytes
        //我们要写一个32位整数，因此我们需要一个ByteBuf，它的容量至少是4字节
        final ByteBuf time = ctx.alloc().buffer(4);
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        //2，不需要像Java中那样调用java.nio.ByteBuffer.flip()，因为netty已经优化了
        // 注意：ChannelHandlerContext.write() (and writeAndFlush()) 方法返回一个ChannelFuture
        // ChannelFuture表示尚未发生的I/O操作。这意味着，可能还没有执行任何请求的操作，因为Netty中的所有操作都是异步的
        // 以下代码可能会在发送消息之前关闭连接：
        //      Channel ch = ...;
        //      ch.writeAndFlush(message);
        //      ch.close();
        // 因此，您需要在ChannelFuture完成后调用close（）方法，该ChannelFuture由write（）方法返回
        // 请注意，close（）也可能不会立即关闭连接，它会返回ChannelFuture。
        final ChannelFuture f = ctx.writeAndFlush(time);
        
        //3，当write request 完成时，我们如何得到通知？ -> 添加ChannelFutureListener，
        // 或者，可以使用预定义的侦听器简化代码：
        //      f.addListener(ChannelFutureListener.CLOSE);
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        }); // (4)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
