package com.demo.concurrencyinpractice.problem.f_thread_pool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 在单线程 Executor 中任务发生死锁（不要这么做）
 */
public class ThreadDeadlock {
    ExecutorService exec  = Executors.newSingleThreadExecutor();

    public class RenderPageTask implements Callable<String> {
        @Override
        public String call() throws Exception{
            Future<String> header,footer;
            header = exec.submit(new LoadFileTask("header.html"));
            footer = exec.submit(new LoadFileTask("footer.html"));
            String page = readerBody();
            //将发生死锁--由于任务在等待子任务的结果
            return header.get() + page + footer.get();
        }

        private String readerBody() {
            return null;
        }
    }

    public class LoadFileTask implements Callable<String> {
        public LoadFileTask(String s) {
        }

        @Override
        public String call() throws Exception {
            return null;
        }
    }
}
