package rwbykit.meepwn.core.service.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


class ProxySocketChannelHandler extends ChannelInboundHandlerAdapter {

    private AbstractChannelInboundHandler handler;

    public ProxySocketChannelHandler(AbstractChannelInboundHandler handler) {
        this.handler = handler;
        this.handler.init();
    }
    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {
        this.handler.channelActive(context);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        this.handler.channelReadComplete(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.handler.channelRead(ctx, msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        this.handler.exceptionCaught(ctx, cause);
    }
}
