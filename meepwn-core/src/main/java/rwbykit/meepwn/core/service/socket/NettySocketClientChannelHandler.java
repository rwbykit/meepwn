package rwbykit.meepwn.core.service.socket;

import java.io.Serializable;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import rwbykit.meepwn.core.service.DataConverter;
import rwbykit.meepwn.core.service.MessageConverter;

/**
 * 
 * netty socket client
 * 
 * @author Cytus_
 * @since 2018年5月26日 上午11:12:08
 * @version 1.0
 *
 */
public class NettySocketClientChannelHandler extends AbstractNettyClientChannelHandler {
	
	private static Logger logger = LoggerFactory.getLogger(NettySocketClientChannelHandler.class);

	public NettySocketClientChannelHandler(DataConverter dataConverter, MessageConverter messageConverter) {
		super(dataConverter, messageConverter);
	}

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("connect success!");
        super.channelActive(ctx);
        while (true) {
        	if (this.isWrite()) {
		        byte[] writeBytes = (byte[]) this.getMessageConverter().pack(this.getOutMessage());
		        ByteBuffer byteBuf = this.getDataConverter().write(writeBytes);
		        byte[] bytes = new byte[byteBuf.position()];
		        byteBuf.flip();
		        byteBuf.get(bytes);
		        ctx.writeAndFlush(Unpooled.copiedBuffer(bytes));
		        break;
        	} else {
		        try {
		        	Thread.sleep(200);
		        } catch (Exception e) {}
        	}
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    	if (this.isReadComplete()) {
			Serializable inMessage = this.getMessageConverter().unpack(this.getHasReadBytes());
			this.setInMessage(inMessage);
			this.setRead(true);
    	}
    }
    

    /**
	 * 异常处理
	 */
    @Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("server occur exception!", cause);
		this.setRead(true);
	    ctx.close(); // 关闭发生异常的连接
	}

}
