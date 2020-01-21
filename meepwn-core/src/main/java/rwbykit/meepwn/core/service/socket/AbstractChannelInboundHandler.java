package rwbykit.meepwn.core.service.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import rwbykit.meepwn.core.service.DataConverter;
import rwbykit.meepwn.core.service.MessageConverter;

import java.nio.ByteBuffer;
import java.util.Objects;

class AbstractChannelInboundHandler extends ChannelInboundHandlerAdapter {

    /** 数据转换 */
    private DataConverter dataConverter;

    /** 数据打解包 */
    private MessageConverter messageConverter;

    /** 已读字节数 */
    ByteBuf hasReadBytes;

    /** 是否读取结束 */
    private boolean readComplete = false;

    public AbstractChannelInboundHandler(DataConverter dataConverter, MessageConverter messageConverter) {
        this.dataConverter = dataConverter;
        this.messageConverter = messageConverter;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        if (Objects.isNull(hasReadBytes)) {
            hasReadBytes = Unpooled.buffer(buf.readableBytes());
        }
        hasReadBytes.writeBytes(buf);
        ByteBuffer byteBuffer = ByteBuffer.allocate(hasReadBytes.readableBytes());
        byteBuffer.put(ByteBufUtil.getBytes(hasReadBytes));
        byte[] bytes = dataConverter.read(byteBuffer);
        if (Objects.nonNull(bytes)) {
            hasReadBytes.clear();
            hasReadBytes.writeBytes(bytes, 0, bytes.length);
            readComplete = true;
        }
        buf.release();
    }

    void init() {
        if (Objects.nonNull(hasReadBytes)) {
            hasReadBytes.release();
            hasReadBytes = null;
        }
        this.readComplete = false;
    }

    public DataConverter getDataConverter() {
        return dataConverter;
    }

    public MessageConverter getMessageConverter() {
        return messageConverter;
    }

    public boolean isReadComplete() {
        return readComplete;
    }

    protected void setReadComplete(boolean readComplete) {
        this.readComplete = readComplete;
    }

    protected byte[] getHasReadBytes() {
        return ByteBufUtil.getBytes(hasReadBytes);
    }

}
