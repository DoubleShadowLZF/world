package com.demo.concurrencyinpractice.problem.f_thread_pool;

import javax.annotation.concurrent.Immutable;
import java.util.LinkedList;
import java.util.List;

/**
 * Node 代表通过一系列的移动到达的一个位置，其中保存了到达该位置的移动以及前一个Node。
 * 只要沿着Node链表逐步回溯，就可以重新构建出到达当前位置的移动序列。
 *
 * @param <P> position 位置类
 * @param <M> move 移动类
 */
@Immutable
public class Node<P, M> {
    final P pos;
    final M move;
    final Node<P, M> prev;

    public Node(P pos, M move, Node<P, M> prev) {
        this.pos = pos;
        this.move = move;
        this.prev = prev;
    }

    List<M> asMoveList() {
        List<M> solution = new LinkedList<>();
        for (Node<P, M> n = this; n.move != null; n = n.prev) {
            solution.add(0, n.move);
        }
        return solution;
    }
}