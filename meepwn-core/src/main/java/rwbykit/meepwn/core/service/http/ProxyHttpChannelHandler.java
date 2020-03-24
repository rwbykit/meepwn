package rwbykit.meepwn.core.service.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;


class ProxyHttpChannelHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private HttpServerChannelHandler handler;

    public ProxyHttpChannelHandler(HttpServerChannelHandler handler) {
        this.handler = handler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        this.handler.channelRead0(ctx, request);
    }
}
