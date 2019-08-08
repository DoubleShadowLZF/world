package com.demo.concurrencyinpractice.problem.g_dead_lock;

import com.demo.concurrencyinpractice.problem.g_dead_lock.entity.Account;
import com.demo.concurrencyinpractice.problem.g_dead_lock.entity.DollarAmount;
import com.demo.concurrencyinpractice.problem.g_dead_lock.exception.InsufficientFundsException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Double
 * @Function 动态死锁
 */
@Slf4j
public class DynamicLock {
    public void transferMoney(Account fromAccount, Account toAccount, DollarAmount amount) {
        synchronized (fromAccount) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (toAccount) {
                if (fromAccount.getBalance().compareTo(amount) < 0) {
                    throw new InsufficientFundsException();
                } else {
                    fromAccount.debit(amount);
                    toAccount.credit(amount);
                }
            }
        }
    }

    public static void main(String[] args) {
        DynamicLock lock = new DynamicLock();
        Account fromAccount = new Account();
        Account toAccount = new Account();
        DollarAmount dollarAmount = new DollarAmount();
        ExecutorService service = Executors.newFixedThreadPool(2);
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                lock.transferMoney(fromAccount, toAccount, dollarAmount);
            }
        };
        Runnable task2 = new Runnable() {
            @Override
            public void run() {
                lock.transferMoney(toAccount, fromAccount, dollarAmount);
            }
        };
        service.execute(task1);
        service.execute(task2);
    }


}
