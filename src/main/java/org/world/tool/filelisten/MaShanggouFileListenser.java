package org.world.tool.filelisten;

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
    private static WatchFilePathTask r;

    static {
        try {
            r = new WatchFilePathTask();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Log log = LogFactory.getLog(MaShanggouFileListenser.class);

    private List<String> mashanggouList ;

    /**
     * tomcat启动的时候创建一个线程
     * @param paramServletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent paramServletContextEvent)
    {
        r.start();
        mashanggouList =  r.getData();
        log.info("MaShanggouFileListenser is started!");
    }

    /**
     * tomcat关闭的时候销毁这个线程
     * @param paramServletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent paramServletContextEvent)
    {
        r.interrupt();
    }

}
