package org.world.exception;

/**
 * 数据加载错误异常
 */
public class DataLoadException extends RuntimeException {
    public DataLoadException(){
        super();
    }

    public DataLoadException(String message){
        super(message);
    }
}
