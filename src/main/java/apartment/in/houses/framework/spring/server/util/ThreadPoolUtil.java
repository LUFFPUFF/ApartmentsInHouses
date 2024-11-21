package apartment.in.houses.framework.spring.server.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolUtil {

    private static ExecutorService threadPool;

    private ThreadPoolUtil() {
    }

    public static synchronized void initialize(int poolSize) {
        threadPool = Executors.newFixedThreadPool(poolSize);
    }

    public static ExecutorService getThreadPool() {
        if (threadPool == null) {
            throw new IllegalStateException("Thread pool not initialized. Call initialize() first.");
        }
        return threadPool;
    }

    public static void shutdown() {
        if (threadPool != null) {
            threadPool.shutdown();
        }
    }


}
