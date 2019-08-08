package com.demo.concurrencyinpractice.problem.g_dead_lock;

import com.demo.concurrencyinpractice.problem.g_dead_lock.entity.Account;
import com.demo.concurrencyinpractice.problem.g_dead_lock.entity.DollarAmount;
import com.demo.concurrencyinpractice.problem.g_dead_lock.exception.InsufficientFundsException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Function 通过锁顺序来避免死锁
 * @author Double
 */
public class DynamicLockAvoidance {

    private static final Object tieLock = new Object();

    public void transferMoney(final Account fromAcct,
                              final Account toAcct,
                              final DollarAmount amount) throws InsufficientFundsException {
        class Helper{
            public void transfer ()throws InsufficientFundsException{
                if(fromAcct.getBalance().compareTo(amount) < 0){
                    throw new InsufficientFundsException();
                }else{
                    fromAcct.debit(amount);
                    toAcct.credit(amount);
                }
            }
        }
        int fromHash = System.identityHashCode(fromAcct);
        int toHash = System.identityHashCode(toAcct);

        if(fromHash < toHash){
            synchronized (fromAcct){
                new Helper().transfer();
            }
        }else if(fromHash > toHash){
            synchronized (toAcct){
                new Helper().transfer();
            }
        }else{
            synchronized (tieLock){
                synchronized (fromAcct){
                    /*try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    synchronized (toAcct){
                        new Helper().transfer();
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        DynamicLockAvoidance avoidance = new DynamicLockAvoidance();
        Account fromAcct = new Account();
        Account toAcct = new Account();
        DollarAmount amount = new DollarAmount();
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                avoidance.transferMoney(fromAcct,toAcct,amount);
            }
        };
        Runnable task2 = new Runnable() {
            @Override
            public void run() {
                avoidance.transferMoney(toAcct,fromAcct,amount);
            }
        };
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.execute(task1);
        service.execute(task2);

    }

}
