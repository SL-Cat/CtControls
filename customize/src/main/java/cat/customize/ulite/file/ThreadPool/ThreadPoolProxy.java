package cat.customize.ulite.file.ThreadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolProxy {
    ThreadPoolExecutor mExecutor;

    int  mCorePoolSize;
    int  mMaximumPoolSize;
    long mKeepAliveTime;

    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        mCorePoolSize = corePoolSize;
        mMaximumPoolSize = maximumPoolSize;
        mKeepAliveTime = keepAliveTime;
    }

    private void initThreadPoolExecutor() {
        if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {

            synchronized (ThreadPoolProxy.class) {
                int corePoolSize = mCorePoolSize;
                int maximumPoolSize = mMaximumPoolSize;
                long keepAliveTime = mKeepAliveTime;

                TimeUnit unit = TimeUnit.MILLISECONDS;
                BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
                ThreadFactory threadFactory = Executors.defaultThreadFactory();
                RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();

                if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
                    mExecutor = new ThreadPoolExecutor(
                            corePoolSize,//核心线程数
                            maximumPoolSize,//最大线程池书
                            keepAliveTime,//保持时间
                            unit, //保持时间单位
                            workQueue,//任务队列
                            threadFactory,//线程工厂
                            handler//异常捕获器
                    );
                }
            }
        }
    }
    /**
     1.提交任务和执行任务
        是否有返回值
            提交任务有返回值
            执行任务没有返回值
     2.Future是啥?
        1.得到任务执行之后的结果
        2.包含了一个get方法和cancle
        3.其中get方法,是一个阻塞的方法,会阻塞等待任务执行完成之后的结果,还可以try catch到任务执行过程中抛出的异常
     */


    /**
     * 提交任务
     */
    public Future<?> submit(Runnable task) {
        initThreadPoolExecutor();
        return mExecutor.submit(task);
    }

    /**
     * 执行任务
     */
    public void execute(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.execute(task);
    }

    /**
     * 移除任务
     */
    public void remove(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.remove(task);
    }
}
