package com.demo.concurrencyinpractice.problem.c_module;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.world.exception.DataLoadException;
import org.world.model.fruits.Apple;
import org.world.model.fruits.Orange;
import org.world.util.ExceptionUtil;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 闭锁应用场景：使用 FutureTask 来提前加载稍后需要的数据
 * <p>
 * 创建一个 FutureTask ，其中包含从数据库加载产品信息的任务，以及执行运算的线程。
 * 由于在构造函数或静态初始化方法中启动线程并不是一种好方法，
 * 因此提供了start方法来启动线程。当程序随后需要 ProductInfo时，可以调用 get 方法，
 * 如果数据已经加载，那么将返回这些数据，否则将等待加载完成后再返回。
 * </p>
 */
@Slf4j
public class Preloader {
    private final FutureTask<ProductInfo> future = new FutureTask<ProductInfo>(new Callable<ProductInfo>() {
        @Override
        public ProductInfo call() throws Exception {
            return loadProductInfo();
        }
    });

    /**
     * 从数据库取数据的方法
     *
     * @return
     */
    private ProductInfo loadProductInfo() {
        ProductInfo productInfo = new ProductInfo();
        //从数据库加载数据
        log.info("正在从数据库中取数据...");
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        productInfo.apple = (Apple) new Apple().setName("I'm apple").setSugar(30);
        productInfo.orange = (Orange) new Orange().setName("I'm orange").setSugar(35);
        return productInfo;
    }

    private final Thread thread = new Thread(future);

    public void start() {
        thread.start();
    }

    public ProductInfo get() throws Exception {
        try {
            return future.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof DataLoadException) {
                throw (DataLoadException) cause;
            } else {
                throw ExceptionUtil.launderThrowable(cause);
            }
        }
    }



    @Data
    private class ProductInfo {
        private Apple apple;
        private Orange orange;
    }


    public static void main(String[] args) throws Exception {
        Preloader preloader = new Preloader();
        preloader.start();
        ProductInfo productInfo = preloader.get();
        log.info("从数据库中取到的数据：{}", JSONObject.toJSONString(productInfo));
    }
}


