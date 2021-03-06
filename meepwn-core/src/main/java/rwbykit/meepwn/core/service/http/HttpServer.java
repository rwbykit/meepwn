package rwbykit.meepwn.core.service.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rwbykit.meepwn.core.service.socket.SocketServerConfig;

import java.util.concurrent.TimeUnit;

public class HttpServer implements HttpServerService {

    private final static Logger logger = LoggerFactory.getLogger(HttpServer.class);

    private HttpServerChannelHandler handler;
    private SocketServerConfig config;
    EventLoopGroup pGroup;
    EventLoopGroup cGroup;
    ChannelFuture channelFuture;

    public HttpServer(HttpServerChannelHandler handler, SocketServerConfig config) {
        this.handler = handler;
        this.config = config;
    }

    @Override
    public void start() {
        pGroup = new NioEventLoopGroup(config.getConnThreadNum());
        cGroup = new NioEventLoopGroup(config.getWorkThreadNum());
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(pGroup, cGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, this.config.getBacklog())
                .option(ChannelOption.SO_REUSEADDR, this.config.isReuseAddr())
                .option(ChannelOption.SO_RCVBUF, this.config.getReceiveBufferSize())
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new IdleStateHandler(config.getReadTimeOut(), config.getWriteTimeOut(),
                                config.getAllTimeOut(), TimeUnit.SECONDS));
                        socketChannel.pipeline().addLast(new HttpServerCodec());
                        socketChannel.pipeline().addLast("httpAggregator", new HttpObjectAggregator(5 * 1025));
                        socketChannel.pipeline().addLast(new ProxyHttpChannelHandler(handler));
                    }
                });
        try {
            //绑定端口, bind返回future(异步), 加上sync阻塞在获取连接处
            channelFuture = bootstrap.bind(this.config.getPort()).sync();
            logger.info("Socket server port:{} start success!", this.config.getPort());
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void destroy() {
        try {
            //等待关闭, 加上sync阻塞在关闭请求处
            channelFuture.channel().closeFuture().sync();
            pGroup.shutdownGracefully();
            cGroup.shutdownGracefully();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
