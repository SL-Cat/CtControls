package cat.customize.ulite.file.ThreadPool;

public class ThreadPoolProxyFactory {
    static ThreadPoolProxy mNormalThreadPoolProxy;
    static ThreadPoolProxy mDownLoadThreadPoolProxy;

    /**
     * 返回普通线程池代理
     *
     * @return
     */
    public static ThreadPoolProxy createNormalThreadPoolProxy() {
        if (mNormalThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mNormalThreadPoolProxy == null) {
                    mNormalThreadPoolProxy = new ThreadPoolProxy(4, 4, 4000);
                }
            }
        }
        return mNormalThreadPoolProxy;
    }

    /**
     * 返回下载线程池代理
     *
     * @return
     */
    public static ThreadPoolProxy createDownLoadThreadPoolProxy() {
        if (mDownLoadThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mDownLoadThreadPoolProxy == null) {
                    mDownLoadThreadPoolProxy = new ThreadPoolProxy(3, 3, 3000);
                }
            }
        }
        return mDownLoadThreadPoolProxy;
    }
}
