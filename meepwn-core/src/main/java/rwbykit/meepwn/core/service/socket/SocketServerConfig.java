package rwbykit.meepwn.core.service.socket;

public class SocketServerConfig extends SocketConfig {

    private int port = 8082;

    public SocketServerConfig setPort(int port) {
        this.port = port;
        return this;
    }

    public int getPort() {
        return this.port;
    }

    @Override
    public SocketServerConfig setAvailableProcessors(int availableProcessors) {
        super.setAvailableProcessors(availableProcessors);
        return this;
    }

    @Override
    public SocketServerConfig setBacklog(int backlog) {
        super.setBacklog(backlog);
        return this;
    }

    @Override
    public SocketServerConfig setReuseAddr(boolean reuseAddr) {
        super.setReuseAddr(reuseAddr);
        return this;
    }

    @Override
    public SocketServerConfig setKeepAlive(boolean keepAlive) {
        super.setKeepAlive(keepAlive);
        return this;
    }

    @Override
    public SocketServerConfig setSendBufferSize(int sendBufferSize) {
        super.setSendBufferSize(sendBufferSize);
        return this;
    }

    @Override
    public SocketServerConfig setReceiveBufferSize(int receiveBufferSize) {
        super.setReceiveBufferSize(receiveBufferSize);
        return this;
    }

    @Override
    public SocketServerConfig setConnThreadNum(int connThreadNum) {
        super.setConnThreadNum(connThreadNum);
        return this;
    }

    @Override
    public SocketServerConfig setWorkThreadNum(int workThreadNum) {
        super.setWorkThreadNum(workThreadNum);
        return this;
    }
}
