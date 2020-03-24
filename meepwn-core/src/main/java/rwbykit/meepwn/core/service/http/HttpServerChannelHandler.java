package rwbykit.meepwn.core.service.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import rwbykit.meepwn.core.service.AbstractActionService;
import rwbykit.meepwn.core.service.MessageConverter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class HttpServerChannelHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private Set<String> contentType;
    private String uri;
    private MessageConverter messageConverter;
    private AbstractActionService actionService;
    private HttpResponseHeaders responseHeaders;

    public HttpServerChannelHandler(List<String> contentType, String uri) {
        this.contentType = Collections.unmodifiableSet(contentType.parallelStream().collect(Collectors.toSet()));
        this.uri = uri;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        HttpMethod httpMethod = request.method();
        String contentType = request.headers().get("Content-Type");
        String uri = request.uri();
        if (checkContentType(contentType) && !Objects.equals(HttpMethod.POST, httpMethod) && this.isSupportedURI(uri)) {
            ByteBuf content = request.content();
            byte[] hasReadBytes = ByteBufUtil.getBytes(content);
            Serializable inMessage = this.messageConverter.unpack(hasReadBytes);
            Serializable outMessage = actionService.doExecute(inMessage);
            byte[] returnBytes = (byte[]) this.messageConverter.pack(outMessage);
            FullHttpResponse response = this.getFullHttpResponse(returnBytes);
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }
    }

    private FullHttpResponse getFullHttpResponse(byte[] responseBody) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(responseBody));
        Map<String, Object> headers = this.responseHeaders.getResponseHeader();
        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            String name = entry.getKey();
            if (response.headers().contains(name)) {
                response.headers().set(name, entry.getValue());
            } else {
                response.headers().add(name, entry.getValue());
            }
        }
        return response;
    }


    private boolean checkContentType(String contentType) {
        return this.contentType.contains(contentType);
    }

    private boolean isSupportedURI(String uri) {
        return Objects.equals(uri, this.uri);
    }
}
