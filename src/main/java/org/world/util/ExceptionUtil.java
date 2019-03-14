package org.world.util;

public class ExceptionUtil {
    /**
     * 强制将未检查的 Throwable 转换为 RuntimeException
     *
     * @param cause
     * @return
     * @throws Error
     */
    public static RuntimeException launderThrowable(Throwable cause) {
        if (cause instanceof RuntimeException) {
            return (RuntimeException) cause;
        }else if(cause instanceof java.lang.Error){
            throw (Error) cause;
        }else {
            throw new IllegalArgumentException("Not unchecked", cause);
        }
    }
}
