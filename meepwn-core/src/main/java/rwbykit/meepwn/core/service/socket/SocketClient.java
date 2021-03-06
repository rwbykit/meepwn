package rwbykit.meepwn.core.service.socket;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SocketClient implements SocketClientService {

    private final static Logger logger = LoggerFactory.getLogger(SocketClient.class);

    SocketClientConfig config;

    SocketChannel socketChannel;
    Bootstrap bootstrap;
    private AbstractClientChannelHandler handler;
    EventLoopGroup group = new NioEventLoopGroup();
    private boolean isConnect = false;

    public SocketClient(AbstractClientChannelHandler handler, SocketClientConfig config) {
        this.handler = handler;
        this.config = config;
    }

    @Override
    public Serializable sendAndReceive(Serializable sendMessage) throws Exception {
        if (!isConnect) {
            throw new Exception("host: " + config.getHost() + " port: " + config.getPort() + "connect server failure!");
        }

        handler.setOutMessage(sendMessage);
        handler.setWrite(true);
        Serializable rtnMessage = null;
        while (true) {
            if (handler.isRead()) {
                rtnMessage = handler.getInMessage();
                handler.setRead(false);
                break;
            } else {
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        this.destroy();
        return rtnMessage;

    }

    @Override
    public void start() {
        try {
            bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class)
                    .group(group)
                    .option(ChannelOption.SO_KEEPALIVE, config.isKeepAlive())
                    .option(ChannelOption.SO_BACKLOG, config.getBacklog())
                    .option(ChannelOption.SO_REUSEADDR, config.isReuseAddr())
                    .option(ChannelOption.SO_SNDBUF, config.getSendBufferSize())
                    .option(ChannelOption.SO_RCVBUF, config.getReceiveBufferSize())
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel sh) throws Exception {
                            sh.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                            sh.pipeline().addLast(new IdleStateHandler(config.getReadTimeOut(),
                                    config.getWriteTimeOut(), config.getWriteTimeOut(), TimeUnit.SECONDS));
                            sh.pipeline().addLast(new ProxySocketChannelHandler(handler));
                        }
                    });

            ChannelFuture future = bootstrap.connect(config.getHost(), config.getPort()).sync();
            if (future.isSuccess()) {
                socketChannel = (SocketChannel)future.channel();
                isConnect = true;
                logger.info("host: "+ config.getHost() +" port: "+ config.getPort() +"connect server success!");
            } else{
                logger.info("host: "+ config.getHost() +" port: "+ config.getPort() +"connect server failure!");
                isConnect = false;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally{

        }
    }

    @Override
    public void destroy() {
        if (Objects.nonNull(socketChannel) && socketChannel.isOpen()) {
            socketChannel.disconnect();
            socketChannel.close();
        }
        try {
            group.shutdownGracefully().sync();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
