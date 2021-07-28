package simple;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 心跳检测，配置IdleStateHandler，会监听userEventTrigger事件
 * @author zhangbaisen
 * @createTime 2021/5/27 14:41
 */
public class HeartChannelHandler extends ChannelInboundHandlerAdapter {
    private int lossConnectCount = 0;
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("已经5秒未收到客户端的消息了！");
        if(evt instanceof IdleStateEvent){
            IdleStateEvent e = (IdleStateEvent) evt;
            if(e.state() == IdleState.READER_IDLE){
                lossConnectCount++;
                if(lossConnectCount > 2){
                    System.out.println("超过2次，关闭这个不活跃通道");
                    ctx.channel().close();
                }
            }
        }else {
            super.userEventTriggered(ctx,evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        lossConnectCount = 0;
        System.out.println("client says: "+msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
