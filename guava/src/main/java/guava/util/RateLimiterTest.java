package guava.util;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 限流工具类
 * 用于限制对一些物理资源或逻辑资源的访问速率。
 * 与 Semaphore 相比，Semaphore 限制了并发访问的数量而不是使用速率。
 *
 */
@Slf4j
public class RateLimiterTest {
    /**
     * 每秒生成两个许可证
     * 情景：
     * 5个线程执行10个任务，当时一秒内最多只能同时执行3.0个任务。
     */
    @Test
    public void createTest() throws InterruptedException {
        final RateLimiter rateLimiter = RateLimiter.create(3.0);
        ExecutorService service = Executors.newFixedThreadPool(5);
        List<Runnable> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tasks.add(new PrintTask(i));
        }
        submitTasks(tasks,service,rateLimiter);
        Thread.sleep(1000*20);
    }

    void submitTasks(List<Runnable> tasks, Executor executor,RateLimiter rateLimiter){
        for (Runnable task : tasks) {
            rateLimiter.acquire(); //也许需要等待
            executor.execute(task);
        }
    }


    class PrintTask implements Runnable{

        private Integer count ;

        public PrintTask(Integer count) {
            this.count = count;
        }

        @Override
        public void run() {
            log.debug("{}--{}线程在{}执行",this.count,Thread.currentThread().getName(),new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            try {
                Thread.sleep(3* 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("{}--{}线程在{}退出",this.count,Thread.currentThread(),new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        }
    }
}
//15:45:12.418 [pool-1-thread-1] DEBUG guava.util.RateLimiterTest - 0--pool-1-thread-1线程在2019-06-24 15:45:12执行
//15:45:12.681 [pool-1-thread-2] DEBUG guava.util.RateLimiterTest - 1--pool-1-thread-2线程在2019-06-24 15:45:12执行
//15:45:13.010 [pool-1-thread-3] DEBUG guava.util.RateLimiterTest - 2--pool-1-thread-3线程在2019-06-24 15:45:13执行
//15:45:13.342 [pool-1-thread-4] DEBUG guava.util.RateLimiterTest - 3--pool-1-thread-4线程在2019-06-24 15:45:13执行
//15:45:13.677 [pool-1-thread-5] DEBUG guava.util.RateLimiterTest - 4--pool-1-thread-5线程在2019-06-24 15:45:13执行
//15:45:15.422 [pool-1-thread-1] DEBUG guava.util.RateLimiterTest - 0--Thread[pool-1-thread-1,5,main]线程在2019-06-24 15:45:15退出
//15:45:15.422 [pool-1-thread-1] DEBUG guava.util.RateLimiterTest - 5--pool-1-thread-1线程在2019-06-24 15:45:15执行
//15:45:15.682 [pool-1-thread-2] DEBUG guava.util.RateLimiterTest - 1--Thread[pool-1-thread-2,5,main]线程在2019-06-24 15:45:15退出
//15:45:15.682 [pool-1-thread-2] DEBUG guava.util.RateLimiterTest - 6--pool-1-thread-2线程在2019-06-24 15:45:15执行
//15:45:16.011 [pool-1-thread-3] DEBUG guava.util.RateLimiterTest - 2--Thread[pool-1-thread-3,5,main]线程在2019-06-24 15:45:16退出
//15:45:16.011 [pool-1-thread-3] DEBUG guava.util.RateLimiterTest - 7--pool-1-thread-3线程在2019-06-24 15:45:16执行
//15:45:16.343 [pool-1-thread-4] DEBUG guava.util.RateLimiterTest - 3--Thread[pool-1-thread-4,5,main]线程在2019-06-24 15:45:16退出
//15:45:16.343 [pool-1-thread-4] DEBUG guava.util.RateLimiterTest - 8--pool-1-thread-4线程在2019-06-24 15:45:16执行
//15:45:16.678 [pool-1-thread-5] DEBUG guava.util.RateLimiterTest - 4--Thread[pool-1-thread-5,5,main]线程在2019-06-24 15:45:16退出
//15:45:16.678 [pool-1-thread-5] DEBUG guava.util.RateLimiterTest - 9--pool-1-thread-5线程在2019-06-24 15:45:16执行
//15:45:18.422 [pool-1-thread-1] DEBUG guava.util.RateLimiterTest - 5--Thread[pool-1-thread-1,5,main]线程在2019-06-24 15:45:18退出
//15:45:18.682 [pool-1-thread-2] DEBUG guava.util.RateLimiterTest - 6--Thread[pool-1-thread-2,5,main]线程在2019-06-24 15:45:18退出
//15:45:19.011 [pool-1-thread-3] DEBUG guava.util.RateLimiterTest - 7--Thread[pool-1-thread-3,5,main]线程在2019-06-24 15:45:19退出
//15:45:19.343 [pool-1-thread-4] DEBUG guava.util.RateLimiterTest - 8--Thread[pool-1-thread-4,5,main]线程在2019-06-24 15:45:19退出
//15:45:19.678 [pool-1-thread-5] DEBUG guava.util.RateLimiterTest - 9--Thread[pool-1-thread-5,5,main]线程在2019-06-24 15:45:19退出