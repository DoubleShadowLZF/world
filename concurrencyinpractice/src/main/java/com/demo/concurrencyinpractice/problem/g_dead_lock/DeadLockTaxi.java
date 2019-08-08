package com.demo.concurrencyinpractice.problem.g_dead_lock;

import com.demo.concurrencyinpractice.problem.g_dead_lock.entity.Point;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.concurrent.GuardedBy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Function 在相互协作对象之间的锁顺序死锁（不要这么做）
 * @author Double
 */
@Slf4j
public class DeadLockTaxi {
    @GuardedBy("this")
    private Point location, destination;
    private DeadLockDispatcher deadLockDispatcher;

//    {
//        this.destination = this.location;
//    }

    public DeadLockTaxi(DeadLockDispatcher deadLockDispatcher){
        this.deadLockDispatcher = deadLockDispatcher;
    }

    public synchronized Point getLocation(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return location ;
    }

    public synchronized void setLocation(Point location){
        this.location = location;
        if(location.equals(destination)){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("{}:add deadLockDispatcher",Thread.currentThread().getName());
            deadLockDispatcher.notifyAvailable(this);
        }
    }



    public static void main(String[] args) {
        DeadLockDispatcher deadLockDispatcher = new DeadLockDispatcher();
        DeadLockTaxi deadLockTaxi = new DeadLockTaxi(deadLockDispatcher);
        Point point = new Point();
        //假设目的地也是当前位置
        deadLockTaxi.destination = point ;
        //将taxi 将入到发布器中
        deadLockDispatcher.joinTaxis(deadLockTaxi);

        ExecutorService service = Executors.newFixedThreadPool(2);
        Runnable task1 = () -> deadLockTaxi.setLocation(point);
        Runnable task2 = () -> deadLockDispatcher.getImage();
        service.execute(task1);
        service.execute(task2);

    }
}
