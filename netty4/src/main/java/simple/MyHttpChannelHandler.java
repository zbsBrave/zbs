package simple;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;

/**
 * 自定义处理http的channel handler
 * @author zhangbaisen
 * @createTime 2021/5/27 14:54
 */
public class MyHttpChannelHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        
    }
}
