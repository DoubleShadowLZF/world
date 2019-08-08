package com.demo.concurrencyinpractice.problem.g_dead_lock;

import com.demo.concurrencyinpractice.problem.g_dead_lock.entity.Image;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.concurrent.GuardedBy;
import java.util.HashSet;
import java.util.Set;

/**
 * @Function 发布器
 * @author Double
 */
@Slf4j
public class DeadLockDispatcher {

    @GuardedBy("this")
    private final Set<DeadLockTaxi> deadLockTaxis;
    @GuardedBy("this")
    private final Set<DeadLockTaxi> availableDeadLockTaxis;

    public DeadLockDispatcher(){
        deadLockTaxis = new HashSet<>();
        availableDeadLockTaxis = new HashSet<DeadLockTaxi>();
    }

    public void joinTaxis(DeadLockTaxi deadLockTaxi){
        deadLockTaxis.add(deadLockTaxi);
    }

    public synchronized void notifyAvailable(DeadLockTaxi deadLockTaxi) {
        availableDeadLockTaxis.add(deadLockTaxi);
        log.info("{}:notify available success",Thread.currentThread().getName());
    }

    public synchronized Image getImage(){
        Image image = new Image();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (DeadLockTaxi t : deadLockTaxis) {
            image.drawMarker(t.getLocation());
        }
        log.info("{}:get image success", Thread.currentThread().getName());
        return image;
    }
}
