package rwbykit.meepwn.core.service.socket;

public class SocketClientConfig extends SocketConfig {

    private int port;
    private String host;

    public int getPort() {
        return port;
    }

    public SocketClientConfig setPort(int port) {
        this.port = port;
        return this;
    }

    public String getHost() {
        return host;
    }

    public SocketClientConfig setHost(String host) {
        this.host = host;
        return this;
    }

    @Override
    public SocketClientConfig setAvailableProcessors(int availableProcessors) {
        super.setAvailableProcessors(availableProcessors);
        return this;
    }

    @Override
    public SocketClientConfig setBacklog(int backlog) {
        super.setBacklog(backlog);
        return this;
    }

    @Override
    public SocketClientConfig setReuseAddr(boolean reuseAddr) {
        super.setReuseAddr(reuseAddr);
        return this;
    }

    @Override
    public SocketClientConfig setKeepAlive(boolean keepAlive) {
        super.setKeepAlive(keepAlive);
        return this;
    }

    @Override
    public SocketClientConfig setSendBufferSize(int sendBufferSize) {
        super.setSendBufferSize(sendBufferSize);
        return this;
    }

    @Override
    public SocketClientConfig setReceiveBufferSize(int receiveBufferSize) {
        super.setReceiveBufferSize(receiveBufferSize);
        return this;
    }

    @Override
    public SocketClientConfig setConnThreadNum(int connThreadNum) {
        super.setConnThreadNum(connThreadNum);
        return this;
    }

    @Override
    public SocketClientConfig setWorkThreadNum(int workThreadNum) {
        super.setWorkThreadNum(workThreadNum);
        return this;
    }
    
}
