package org.world.concurrencyinpractice.problem.c_module;

import lombok.extern.slf4j.Slf4j;
import org.world.model.Board;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 栅栏应用场景
 * 栅栏：阻塞一组线程直到某个事件发生
 * <p>通过 CyclicBarrier 协调细胞自动衍生系统中的计算</p>
 * <p>
 *     通过栅栏来计算细胞的自动化模拟。
 * </p>
 * <p>
 *     细胞自动机论主要研究由小的计算机或部件，按邻域连接方式连接成较大的、并行工作的计算机或部件的理论模型。
 *     为了理解细胞自动机，可看一个简单例子：找一张画有许多格子的图纸，用铅笔涂黑其中一些格子就可得到一个图案（样式）。
 *     第一排也许有一个或几个格子被涂黑了，而一个简单的细胞自动机是确定某种简单的规则，从第二排开始往下画出新图案来。
 *     具体到每一行中的每一个格子，要观察其上一行的对应格子及该对应格子两边的情况，然后根据这三个格子是否被涂黑，
 *     以及黑白格子如何相邻的已定规则（比如，当这三个格子从左至右分别为黑、黑、白时，其正下面的格子为白，否则为黑），
 *     确定当前的格子是涂黑还是留白。如此反复进行下去。一条或一组这样的简单规则及简单的初始条件就构成了一个细胞自动机。
 * </p>
 */
@Slf4j
public class CellularAutomata {

    private final Board mainBoard;
    private final CyclicBarrier barrier;
    private final Worker[] workers;

    public CellularAutomata(Board board) {
        this.mainBoard = board;
        int count = Runtime.getRuntime().availableProcessors();
        log.info("the number of processors available to the Java virtual machine is {}.", count);
        //使用 CPU数量 进行计算
        this.barrier = new CyclicBarrier(count, new Runnable() {
            @Override
            public void run() {
                //更新主面板中的值
                mainBoard.commitNewValues();
            }
        });
        this.workers = new Worker[count];
        for (int i = 0; i < count; i++) {
            workers[i] = new Worker(mainBoard.getSubBoard(count, i));
        }
    }

    /**
     * 工作者
     * 负责细胞的演算，演算完后一起返回
     */
    private class Worker implements Runnable {
        private final Board board;

        public Worker(Board board) {
            this.board = board;
        }

        @Override
        public void run() {
            while (!board.hasConverged()) {
                for (int i = 0; i < board.getMaxX(); i++) {
                    for (int j = 0; j < board.getMaxY(); j++) {
                        //设置面板中的值
                        board.setNewValue(i, j, computeValue(i, j));
                        try {
                            barrier.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        public void start() {
            for (int i = 0; i < workers.length; i++) {
                new Thread(workers[i]).start();
            }
            //汇聚所有计算结果
            mainBoard.waitForConvergence();
        }
    }

    private int computeValue(int i, int j) {
        return i + j;
    }

    public static void main(String[] args) {
        CellularAutomata cellularAutomata = new CellularAutomata(new Board(10, 10));
        int result = cellularAutomata.computeValue(2, 3);
        log.info("结果为{}", result);
    }
}
