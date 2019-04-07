package com.demo.concurrencyinpractice.problem.f_thread_pool;

import java.util.Set;

/**
     * 表示“搬箱子”之类谜题的抽象类
     * @param <P> position 位置类
     * @param <M> move 移动类
     */
    public interface Puzzle<P,M>{
        /**
         * 初始化位置
         * @return
         */
        P initialPosition();

        /**
         * 是否是目标结果
         * @param position
         * @return
         */
        boolean isGoal(P position);

        /**
         * 判断是否有限移动的规则集
         * @param position
         * @return
         */
        Set<M> legalMoves(P position);

        /**
         * 移动到目标位置
         * @param position
         * @param move
         * @return
         */
        P move(P position,M move);
    }