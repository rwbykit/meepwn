package rwbykit.meepwn.core.service.socket;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rwbykit.meepwn.core.service.AbstractActionService;
import rwbykit.meepwn.core.service.DataConverter;
import rwbykit.meepwn.core.service.MessageConverter;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class NettySocketServerChannelHandler extends AbstractNettyChannelInboundHandler {

    private final static Logger logger = LoggerFactory.getLogger(NettySocketServerChannelHandler.class);

    private AbstractActionService actionService;

    private boolean isAsyncSchedule = false;

    public NettySocketServerChannelHandler(DataConverter dataConverter, MessageConverter messageConverter, AbstractActionService actionService) {
        super(dataConverter, messageConverter);
        this.actionService = actionService;
    }

    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {
        logger.info("server channel active... ");
    }

    /**
     * 信息读取完成后处理
     */
    @Override
    @SuppressWarnings("unchecked")
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        if (this.isReadComplete()) {
            this.setReadComplete(false);
            Serializable inMessage = this.getMessageConverter().unpack(this.getHasReadBytes());
            Serializable outMessage = null;

            if (isAsyncSchedule) {
                AsynchronousScheduler.doSchedule(ctx, this);
            } else {
                outMessage = actionService.doExecute(inMessage);
                byte[] writeBytes = (byte[]) this.getMessageConverter().pack(outMessage);
                ByteBuffer byteBuf = this.getDataConverter().write(writeBytes);
                ctx.writeAndFlush(Unpooled.copiedBuffer(byteBuf.array()));
            }
        }
        super.channelReadComplete(ctx);
        ctx.flush();
    }

    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("server occur exception:" + cause.getMessage());
        ctx.close(); // 关闭发生异常的连接
    }

    public AbstractActionService getActionService() {
        return actionService;
    }

    public boolean isAsyncSchedule() {
        return isAsyncSchedule;
    }

    public void setAsyncSchedule(boolean asyncSchedule) {
        isAsyncSchedule = asyncSchedule;
    }
}
