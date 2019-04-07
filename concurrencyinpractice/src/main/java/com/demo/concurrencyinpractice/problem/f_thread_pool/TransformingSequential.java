package com.demo.concurrencyinpractice.problem.f_thread_pool;

import javax.swing.text.Element;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * 将串行执行转换为并行执行
 * <p>
 *     如果在循环体中包含了一些密集计算，或者需要执行可能阻塞的I/O操作，那么只要每次迭代是独立的，
 *     都可以对其进行并行化。
 * </p>
 */
public class TransformingSequential {

    void processSequentially(List<Element> elements){
        for (Element e : elements) {
            process(e);
        }
    }

    void processInParallel(Executor exec,List<Element> elements){
        for (final Element e : elements) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    process(e);
                }
            });
        }
    }

    private void process(Element e) {

    }

    /**
     * 将串行递归转换为并行递归
     * <p>
     *     当串行循环中的各个迭代操作之间彼此独立，并且每个迭代操作执行的工作量比管理一个新任务时，
     *     带来的开销更多，那么这个串行循环就适合并行化。
     * </p>
     * <p>遍历过程仍然是串行的，只有compute调用才是并行执行的</p>
     */
    public<T> void sequentialRecursive(List<Node<T>> nodes, Collection<T> results){
        for (final Node<T> n: nodes ) {
            results.add(n.compute());
            sequentialRecursive(n.getChildren(),results);
        }
    }

    public <T> void parallelRecursive(final Executor exec,List<Node<T>> nodes,
                                      final Collection<T> results){
        for (final Node<T> n : nodes) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    results.add(n.compute());
                }
            });
            parallelRecursive(exec,n.getChildren(),results);
        }
    }

    interface Node<T>{
        T compute();
        List<Node<T>> getChildren();
    }

    /**
     * 等待通过并行方法计算的结果
     */
    public<T> Collection<T> getParallelResults(List<Node<T>> nodes) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        Queue<T> resultQUeue = new ConcurrentLinkedQueue<T>();
        parallelRecursive(exec,nodes,resultQUeue);
        exec.shutdown();
        exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        return resultQUeue;
    }
}
