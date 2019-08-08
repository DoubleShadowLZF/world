package com.demo.concurrencyinpractice.problem.g_dead_lock;

import com.demo.concurrencyinpractice.problem.g_dead_lock.entity.Account;
import com.demo.concurrencyinpractice.problem.g_dead_lock.entity.DollarAmount;

import java.util.Random;

/**
 * @Function 在典型条件下会发生死锁的循环
 * @author Double
 */
public class DemonstrateDeadlock {
    private static final int NUM_THREADS = 20;
    private static final int NUM_ACCOUNTS = 5;
    private static final int NUM_ITERATIONS = 1000 *1000;

    public static void main(String[] args) {
        final Random rnd = new Random();
        final Account[] accounts = new Account[NUM_ACCOUNTS];
        DynamicLockAvoidance avoidance = new DynamicLockAvoidance();

        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account();

            class TransferThread extends Thread{
                @Override
                public void run() {
                    for (int j = 0; j < NUM_ITERATIONS; j++) {
                        int fromAcct = rnd.nextInt(NUM_ACCOUNTS);
                        int toAcct = rnd.nextInt(NUM_ACCOUNTS);
                        DollarAmount amount = new DollarAmount();
                        avoidance.transferMoney(accounts[fromAcct],accounts[toAcct],amount);
                    }
                }
            }
            for (int j = 0; j < NUM_THREADS; j++) {
                new TransferThread().start();
            }
        }
    }
}
