package org.world.util;

import org.apache.tomcat.jni.Error;

public class ExceptionUtil {
    /**
     * 强制将未检查的 Throwable 转换为 RuntimeException
     *
     * @param cause
     * @return
     * @throws Error
     */
    public static Exception launderThrowable(Throwable cause) {
        if (cause instanceof RuntimeException) {
            return (RuntimeException) cause;
        }else {
            throw new IllegalArgumentException("Not unchecked", cause);
        }
    }
}
