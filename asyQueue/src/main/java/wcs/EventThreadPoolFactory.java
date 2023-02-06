package wcs;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author liuzongshuai
 * @date 2022/12/22 10:24
 */
@Slf4j
public class EventThreadPoolFactory {


    private static final int DEFAULT_CORE_SIZE = 0;
    private static final int DEFAULT_MAX_SIZE = 2;
    private static final int DEFAULT_TIMEOUT = 1;
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.HOURS;
    private static final int DEFAULT_QUEUE_SIZE = 1;
    private static final BlockingQueue<Runnable> DEFAULT_WORK_QUEUE = new ArrayBlockingQueue<>(DEFAULT_QUEUE_SIZE);

    public static Executor buildDefaultExecutor(String identifier) {
        return new ThreadPoolExecutor(DEFAULT_CORE_SIZE,
                DEFAULT_MAX_SIZE,
                DEFAULT_TIMEOUT,
                DEFAULT_TIME_UNIT,
                DEFAULT_WORK_QUEUE,
                new ThreadFactoryBuilder().setNameFormat(String.format("%s-", identifier)).build(),
                new RejectedExecutionHandler(){
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        log.error("线程池[ {} ]等待队列已满，正在执行阻塞等待", executor.toString());
                        if (!executor.isShutdown()) {
                            try {
                                executor.getQueue().put(r);
                            } catch (Exception e) {
                                log.error("阻塞策略异常", e);
                            }
                        }
                    }
                });
    }
}
