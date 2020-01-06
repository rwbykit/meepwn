package rwbykit.meepwn.core.service.socket;

class SocketConfig {

    private int availableProcessors = Runtime.getRuntime().availableProcessors();

    /**
     * 可连接队列，服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，
     * 多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
     */
    private int backlog = 1024;

    /**
     * 表示是否允许重复使用本地地址和端口
     */
    private boolean reuseAddr = false;

    /**
     * 用于设置TCP连接，当设置该选项以后，连接会测试链接的状态，这个选项用于可能长时间没有数据交流的连接。
     * 当设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文
     * 默认的心跳间隔是7200s即2小时
     */
    private boolean keepAlive = false;

    /**
     * 接收缓冲区大小
     */
    private int sendBufferSize = 1024;

    /**
     * 发送缓冲区大小
     */
    private int receiveBufferSize = 1024;

    /**
     * 连接线程数
     */
    private int connThreadNum = availableProcessors;

    /**
     * 工作线程数
     */
    private int workThreadNum = availableProcessors;

    /**
     * 读超时时间
     */
    private long readTimeOut = 10L;

    /**
     * 写超时时间
     */
    private long writeTimeOut = 10L;

    /**
     * 总超时时间
     */
    private long allTimeOut = 30L;

    public int getAvailableProcessors() {
        return availableProcessors;
    }

    public SocketConfig setAvailableProcessors(int availableProcessors) {
        this.availableProcessors = availableProcessors;
        return this;
    }

    public int getBacklog() {
        return backlog;
    }

    public SocketConfig setBacklog(int backlog) {
        this.backlog = backlog;
        return this;
    }

    public boolean isReuseAddr() {
        return reuseAddr;
    }

    public SocketConfig setReuseAddr(boolean reuseAddr) {
        this.reuseAddr = reuseAddr;
        return this;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public SocketConfig setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
        return this;
    }

    public int getSendBufferSize() {
        return sendBufferSize;
    }

    public SocketConfig setSendBufferSize(int sendBufferSize) {
        this.sendBufferSize = sendBufferSize;
        return this;
    }

    public int getReceiveBufferSize() {
        return receiveBufferSize;
    }

    public SocketConfig setReceiveBufferSize(int receiveBufferSize) {
        this.receiveBufferSize = receiveBufferSize;
        return this;
    }

    public int getConnThreadNum() {
        return connThreadNum;
    }

    public SocketConfig setConnThreadNum(int connThreadNum) {
        this.connThreadNum = connThreadNum;
        return this;
    }

    public int getWorkThreadNum() {
        return workThreadNum;
    }

    public SocketConfig setWorkThreadNum(int workThreadNum) {
        this.workThreadNum = workThreadNum;
        return this;
    }

    public long getReadTimeOut() {
        return readTimeOut;
    }

    public SocketConfig setReadTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    public long getWriteTimeOut() {
        return writeTimeOut;
    }

    public SocketConfig setWriteTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return this;
    }

    public long getAllTimeOut() {
        return allTimeOut;
    }

    public SocketConfig setAllTimeOut(long allTimeOut) {
        this.allTimeOut = allTimeOut;
        return this;
    }
}
