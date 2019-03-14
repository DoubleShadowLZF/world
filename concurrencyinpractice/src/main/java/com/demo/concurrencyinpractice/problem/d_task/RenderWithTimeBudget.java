package com.demo.concurrencyinpractice.problem.d_task;

import java.util.concurrent.*;

/**
 * 在指定时间内获取广告信息
 * <p>
 *     Future.get 的一种典型应用。在它生成的页面中包括响应用户请求的内容以及从广告服务器上获得的广告。
 *     它将获取广告的任务提交给 Executor，然后计算剩余的文本页面内容，最后等待广告信息，直到超出指定的时间。
 *     如果get超时，那么取消广告获取任务，并转而使用默认的广告信息。
 * </p>
 */
public class RenderWithTimeBudget {

    private static final Ad DEFAULT_AD = new Ad();
    private static final long TIME_BUDGET = 1000;
    private static final ExecutorService exec = Executors.newCachedThreadPool();

    Page renderPageWithAd() throws InterruptedException{
        long endNanos = System.nanoTime() + TIME_BUDGET;
        Future<Ad> f = exec.submit(new FetchAdTask());
        //在等待广告的同时显示页面
        Page page = renderPageBody();
        Ad ad ;
        try{
            //只等待指定的时间长度
            long timeLeft = endNanos - System.nanoTime();
            ad = f.get(timeLeft, TimeUnit.NANOSECONDS);
        }catch (ExecutionException e) {
            ad = DEFAULT_AD;
        } catch (TimeoutException e) {
            ad= DEFAULT_AD;
            f.cancel(true);
        }
        page.setAd(ad);
        return page;
    }

    Page renderPageBody() { return new Page(); }


    static class Ad {
    }

    static class Page {
        public void setAd(Ad ad) { }
    }

    static class FetchAdTask implements Callable<Ad> {
        public Ad call() {
            return new Ad();
        }
    }
}
