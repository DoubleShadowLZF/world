package com.demo.concurrencyinpractice.problem.f_thread_pool;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 在解决器中找不到解答
 * <p>
 *     如果不存在解答，那么 ConcurrentPuzzleSolver 就不能很好地处理这种情况：
 *     如果已经遍历了所有的移动和位置都没有找到解答，那么在getSolution调用中将永远等待下去。
 *     当遍历了整个搜索空间时，串行版本的程序将结束，但要结束并发程序会更困难。
 *     其中一种方法是：
 *      记录活动任务的数量，当该值为零时将解答设置为null（下方的代码）。
 *      另一种方式是：
 *      设置超时时间。ValueLatch中实现一个限时的getValue（其中一个限时版本的await），
 *      如果getValue超时，那么关闭Executor并声明出现了一个失败。另一个结束条件是某种特定于谜题的标准，
 *      例如只搜索特定数量的位置。
 * </p>
 * @param <P>
 * @param <M>
 */
public class PuzzleSolver<P, M> extends ConcurrentPuzzleSolver {

    private final AtomicInteger taskCount = new AtomicInteger(0);

    public PuzzleSolver(Puzzle puzzle) {
        super(puzzle);
    }

    protected Runnable newTask(P p, M m, Node<P, M> n) {
        return new CountingSolverTask(p, m, n);
    }

    class CountingSolverTask extends SolverTask {

        CountingSolverTask(Object pos, Object move, Node prev) {
            super(pos, move, prev);
            taskCount.incrementAndGet();
        }

        @Override
        public void run() {
            try {
                super.run();
            } finally {
                if (taskCount.decrementAndGet() == 0) {
                    solution.setValue(null);
                }
            }
        }
    }
}
