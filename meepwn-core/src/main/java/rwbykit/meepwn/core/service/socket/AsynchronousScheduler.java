package rwbykit.meepwn.core.service.socket;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import rwbykit.meepwn.core.threadpool.ThreadPool;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class AsynchronousScheduler {

    private static ThreadPool threadPool;

    public static void doSchedule(ChannelHandlerContext context, NettySocketServerChannelHandler handler) {

        Callable<?> callable = handler.getActionService();
        try {
            Future<?> future = threadPool.add(callable);
            Serializable outMessage = (Serializable) future.get();
            byte[] writeBytes = (byte[]) handler.getMessageConverter().pack(outMessage);
            ByteBuffer byteBuf = handler.getDataConverter().write(writeBytes);
            context.writeAndFlush(Unpooled.copiedBuffer(byteBuf.array()));
        } catch (Exception e) {

        }

    }

    public static void setThreadPool(ThreadPool threadPool) {
        AsynchronousScheduler.threadPool = threadPool;
    }

}
