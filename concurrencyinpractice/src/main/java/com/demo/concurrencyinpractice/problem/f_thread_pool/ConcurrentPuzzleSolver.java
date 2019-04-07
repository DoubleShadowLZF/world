package com.demo.concurrencyinpractice.problem.f_thread_pool;

import java.util.List;
import java.util.concurrent.*;

/**
 * 并发的谜题解答器
 * <p>
 *     因为计算某次移动的过程在很大程度上与聚酸其他移动的过程是相互独立的
 *     （之所以说“在很大程度上”，是因为在各个人物之间会共享一些可变状态，例如已遍历位置的集合。）
 *     如果有多个处理器可用，那么这将减少寻找解决方案所花费的时间。
 * </p>
 * <p>
 *     使用了一个内部类SolverTask，这个类拓展了Node并实现了Runnable。大多数工作都是在run方法中完成的：
 *     首先计算出下一步可能到达的所有位置，并去掉已经到达的位置，然后判断（这个任务或者其他某个任务）
 *     是否已经成功地完成，最后将尚未搜索过的位置提交给Executor。
 * </p>
 * @param <P> position 位置类
 * @param <M> move 移动类
 */
public class ConcurrentPuzzleSolver<P, M> {
    private final Puzzle<P, M> puzzle;
    private final ExecutorService exec;
    /**
     * 提供了线程安全性，避免了在更新共享集合时存在的竟态问题，
     * 因为putIfAbsent只有在之前没有遍历过的某个位置才会通过原子方式添加到集合中。
     */
    private final ConcurrentMap<P, Boolean> seen;
    final ValueLatch<Node<P, M>> solution = new ValueLatch<>();

    public ConcurrentPuzzleSolver(Puzzle<P, M> puzzle) {
        this.puzzle = puzzle;
        this.exec = initThreadPool();
        this.seen = new ConcurrentHashMap<>();
        if (exec instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor tpe = (ThreadPoolExecutor) exec;
            tpe.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        }
    }

    private ExecutorService initThreadPool() {
        return Executors.newCachedThreadPool();
    }

    public List<M> solve() throws InterruptedException {
        try {
            P p = puzzle.initialPosition();
            exec.execute(newTask(p, null, null));
            //阻塞直到找到解答
            Node<P, M> solnPuzzleNode = solution.getValue();
            return (solnPuzzleNode == null) ? null : solnPuzzleNode.asMoveList();
        } finally {
            exec.shutdown();
        }
    }

    private Runnable newTask(P p, M m, Node<P, M> n) {
        return new SolverTask(p, m, n);
    }

    protected class SolverTask extends Node<P, M> implements Runnable {

        SolverTask(P pos, M move, Node<P, M> prev) {
            super(pos, move, prev);
        }

        @Override
        public void run() {
            if (solution.isSet() || seen.putIfAbsent(pos, true) != null) {
                return;//已经找到了解答或者遍历了这个位置
            }
            if (puzzle.isGoal(pos)) {
                solution.setValue(this);
            } else {
                for (M m : puzzle.legalMoves(pos)) {
                    exec.execute(newTask(puzzle.move(pos, m), m, this));
                }

            }
        }
    }
}
