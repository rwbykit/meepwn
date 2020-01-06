package rwbykit.meepwn.core.threadpool;

import rwbykit.meepwn.core.Service;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface ThreadPool extends Service {

    /**
     * 默认核心线程池大小
     */
    public final static int DEF_CORE_POOL_SIZE = 10;

    /**
     * 默认最大线程池大小
     */
    public final static int DEF_MAX_POOL_SIZE = 20;

    /**
     * 默认待执行队列大小
     */
    public final static int DEF_WAIT_QUEUE_SIZE = 50;

    /**
     * 添加任务
     * @param task
     * @return
     * @throws Exception
     */
    public Future<?> add(Callable<?> task) throws Exception;

    /**
     * 批量添加任务
     * @param tasks
     * @return
     * @throws Exception
     */
    public Collection<Future<?>> add(Collection<Callable<?>> tasks) throws Exception;

    /**
     * 获得当前等待任务数量
     * @return
     */
    public int getCurrWaitTask();

    /**
     * 获得线程池名称
     * @return
     */
    public String getTreadPoolName();

    /**
     * 添加任务
     * @param task
     * @return
     * @throws Exception
     */
    public boolean addRunnable(Runnable task) throws Exception;

    /**
     * 批量添加任务
     * @param tasks
     * @return
     * @throws Exception
     */
    public boolean addRunnables(Collection<Runnable> tasks) throws Exception;


}
