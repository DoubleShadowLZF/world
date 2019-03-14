package com.demo.concurrencyinpractice.problem.d_task;

import java.util.*;
import java.util.concurrent.*;

/**
 * 在预定时间内请求旅游报价
 * <p>
 *     “预定时间”方法可以很容易地拓展到任意数量的任务上。
 *     考虑这样一个旅行预定门户网站：
 *     用户输入旅行的日期和其他要求，门户网站获取并显示来自多条航线、旅店或汽车租赁公司的报价。
 *     在获取不同公司报价的过程中，可能会调用Web服务、访问数据库、执行一个EDI事务或其他机制。在这种情况下，
 *     不宜让页面的响应时间受限于最慢的响应时间，而应该只显示在指定时间内收到的信息。
 *     对于没有及时响应的服务提供者，页面可以忽略他们，或者显示一个提示信息，
 *     例如：“Did not hear from Air Java in time”。
 * </p>
 * <p>
 *     从一个公司获得报价的过程与从其他公司获得报价的过程无关，因此可以将获取报价的过程当成一个任务，
 *     从而使获得报价的过程能并发执行，创建n个任务，将其提交到一个线程池，保留n个Future，
 *     并使用显示的get方法通过Future串行地获取每一个结果，这一切都很简单，但还有一个更简单的方法--invokeAll。
 * </p>
 * <p>
 *     将多个任务提交到一个 ExecutorService 并获得结果， InvokeAll 方法的参数为一组任务，并返回一组Future。
 *     这两个集合有着相同的结构。invokeAll 按照任务集合中迭代器的顺序将所有的Future添加到返回的集合中，
 *     从而调用者能将各个Future与其表示的Callable关联起来。当超过指定时限后，任何未完成的任务都会取消。
 *     当invokeAll返回后，每个任务要么正常地完成，要么被取消，而客户端代码可以调用get或isCancelled来判断究竟是何种情况。
 * </p>
 */
public class TimeBudget {
    private ExecutorService exec = Executors.newCachedThreadPool();

    public List<TravelQuote> getRankedTravelQuotes(TravelInfo travelInfo, Set<TravelCompany> companies,
                                                   Comparator<TravelQuote> ranking, long time, TimeUnit unit)
                                                    throws InterruptedException {
        List<QuoteTask> tasks = new ArrayList<>();
        for (TravelCompany company : companies)
            tasks.add(new QuoteTask(company, travelInfo));

        List<Future<TravelQuote>> futures = exec.invokeAll(tasks, time, unit);
        List<TravelQuote> quotes =
                new ArrayList<>(tasks.size());
        Iterator<QuoteTask> taskIterator = tasks.iterator();
        for (Future<TravelQuote> f : futures) {
            QuoteTask task = taskIterator.next();
            try {
                quotes.add(f.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (CancellationException e) {
                quotes.add(task.getTimeoutQuote(e));
            }
        }
        Collections.sort(quotes, ranking);
        return quotes;
    }
}

class QuoteTask implements Callable<TravelQuote> {
    private final TravelCompany company;
    private final TravelInfo travelInfo;

    public QuoteTask(TravelCompany company, TravelInfo travelInfo) {
        this.company = company;
        this.travelInfo = travelInfo;
    }

    TravelQuote getFailureQuote(Throwable t) {
        return null;
    }

    TravelQuote getTimeoutQuote(CancellationException e) {
        return null;
    }

    public TravelQuote call() throws Exception {
        return company.solicitQuote(travelInfo);
    }
}

interface TravelCompany {
    TravelQuote solicitQuote(TravelInfo travelInfo) throws Exception;
}

interface TravelQuote {
}

interface TravelInfo {
}
