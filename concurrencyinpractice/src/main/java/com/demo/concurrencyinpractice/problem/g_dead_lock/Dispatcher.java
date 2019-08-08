package com.demo.concurrencyinpractice.problem.g_dead_lock;

import com.demo.concurrencyinpractice.problem.g_dead_lock.entity.Image;

import javax.annotation.concurrent.GuardedBy;
import java.util.HashSet;
import java.util.Set;

/**
 * @Function TODO
 * @author Double
 */
public class Dispatcher {

    @GuardedBy("this")
    private final Set<Taxi> taxis;

    @GuardedBy("this")
    private final Set<Taxi> availableTaxis;

    public Dispatcher(){
        taxis = new HashSet();
        availableTaxis = new HashSet<>();
    }

    public synchronized void notifyAvailable(Taxi taxi){
        availableTaxis.add(taxi);
    }

    public Image getImage(){
        Set<Taxi> copy;
        synchronized(this){
            copy = new HashSet<Taxi>();
        }
        Image image = new Image();
        for(Taxi taxi : copy){
            image.drawMarker(taxi.getLocation());
        }
        return image;
    }
}
