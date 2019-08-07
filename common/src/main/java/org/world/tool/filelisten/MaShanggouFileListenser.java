package org.world.tool.filelisten;

import lombok.Getter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * 监听web容器中的文件
 * 可以通过 注册 MaShanggouFileListenser 这个Bean获取文件中的值
 */
@Component
public class MaShanggouFileListenser implements ServletContextListener
{
    @Getter
    private static WatchFilePathTask watchFilePathTask;

    static {
        try {
            watchFilePathTask = new WatchFilePathTask();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private Log log = LogFactory.getLog(MaShanggouFileListenser.class);

    /*
     * tomcat启动的时候创建一个线程
     * */
    @Override
    public void contextInitialized(ServletContextEvent paramServletContextEvent)
    {
        watchFilePathTask.start();
        log.info("MaShanggouFileListenser is started!");
    }

    /*
     * tomcat关闭的时候销毁这个线程
     * */
    @Override
    public void contextDestroyed(ServletContextEvent paramServletContextEvent)
    {
        watchFilePathTask.interrupt();
    }
}
