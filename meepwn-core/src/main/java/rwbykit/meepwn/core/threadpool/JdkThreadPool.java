package rwbykit.meepwn.core.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JdkThreadPool implements ThreadPool {
    
    private final static Logger logger = LoggerFactory.getLogger(JdkThreadPool.class);

    private String threadPoolName = "DEFAULT";

    private int corePoolSize = DEF_CORE_POOL_SIZE;

    private int maxPoolSize = DEF_MAX_POOL_SIZE;

    private int queueSize = DEF_WAIT_QUEUE_SIZE;

    private ExecutorService executorService;

    private BlockingQueue<Runnable> waitQueue;

    @Override
    public void start() {
        try {
            waitQueue = new LinkedBlockingQueue<Runnable>(queueSize);
            executorService = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 200, TimeUnit.MILLISECONDS, waitQueue, new ThreadPoolExecutor.AbortPolicy());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public Future<?> add(Callable<?> task) throws Exception {
        assertExecutorService();
        return executorService.submit(task);
    }

    @Override
    public Collection<Future<?>> add(Collection<Callable<?>> tasks) throws Exception {
        assertExecutorService();
        if (Objects.nonNull(tasks)) {
            Collection<Future<?>> results = new ArrayList<Future<?>>(tasks.size());
            for (Callable<?> task : tasks) {
                results.add(executorService.submit(task));
            }
            return results;
        }
        return null;
    }

    @Override
    public void destroy() {
        if (Objects.nonNull(executorService)) {
            this.executorService.shutdown();
            this.waitQueue.clear();
        }
    }

    @Override
    public int getCurrWaitTask() {
        return waitQueue.size();
    }

    @Override
    public String getTreadPoolName() {
        return this.threadPoolName;
    }

    @Override
    public boolean addRunnable(Runnable task) throws Exception {
        assertExecutorService();
        executorService.execute(task);
        return true;
    }

    @Override
    public boolean addRunnables(Collection<Runnable> tasks) throws Exception {
        assertExecutorService();
        if (Objects.nonNull(tasks)) {
            for (Runnable task : tasks) {
                executorService.execute(task);
            }
            return true;
        }
        return false;
    }

    private void assertExecutorService() throws Exception {
        if (Objects.isNull(this.executorService)) {
            throw new NullPointerException("线程池尚未初始化!");
        }
    }

    public String getThreadPoolName() {
        return threadPoolName;
    }

    public void setThreadPoolName(String threadPoolName) {
        this.threadPoolName = threadPoolName;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }


}
