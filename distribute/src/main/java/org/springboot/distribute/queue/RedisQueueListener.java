package org.springboot.distribute.queue;

public interface RedisQueueListener<T> {
  
    public void onMessage(T value);  
}  