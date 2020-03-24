package rwbykit.meepwn.core.service.socket;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rwbykit.meepwn.core.threadpool.ThreadPool;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class AsynchronousScheduler {

    private final static Logger logger = LoggerFactory.getLogger(AsynchronousScheduler.class);

    private static ThreadPool threadPool;

    public static void doSchedule(ChannelHandlerContext context, SocketServerChannelHandler handler) {

        Callable<?> callable = handler.getActionService();
        try {
            Future<?> future = threadPool.add(callable);
            Serializable outMessage = (Serializable) future.get();
            byte[] writeBytes = (byte[]) handler.getMessageConverter().pack(outMessage);
            ByteBuffer byteBuf = handler.getDataConverter().write(writeBytes);
            context.writeAndFlush(Unpooled.copiedBuffer(byteBuf.array()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    public static void setThreadPool(ThreadPool threadPool) {
        AsynchronousScheduler.threadPool = threadPool;
    }

}
