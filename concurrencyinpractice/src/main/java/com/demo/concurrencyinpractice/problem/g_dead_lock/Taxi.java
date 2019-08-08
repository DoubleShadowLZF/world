package com.demo.concurrencyinpractice.problem.g_dead_lock;

import com.demo.concurrencyinpractice.problem.g_dead_lock.entity.Point;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

/**
 * @Function 通过公开调用来避免在相互协作的对象之间产生死锁
 * @author Double
 */
@ThreadSafe
public class Taxi {

    @GuardedBy("this")
    private Point location,destination;
    private final Dispatcher dispatcher;

    public Taxi(){
        dispatcher = new Dispatcher();
    }

    public synchronized Point getLocation(){
        return location;
    }

    public void setLocation(Point location){
        boolean reachedDestination;
        synchronized (this){
            this.location = location;
            reachedDestination = location.equals(destination);
        }
        if(reachedDestination){
            dispatcher.notifyAvailable(this);
        }
    }

}
