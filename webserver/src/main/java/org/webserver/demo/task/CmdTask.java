package org.webserver.demo.task;

import lombok.extern.slf4j.Slf4j;
import org.webserver.demo.common.WriteBuffer;
import org.webserver.demo.entity.CmdCode;

import java.io.UnsupportedEncodingException;

/**
 * 指令任务
 */
@Slf4j
public class CmdTask implements Runnable {

    private String consoleText;


    public CmdTask(String consoleTest) {
        this.consoleText = consoleTest;
    }

    /**
     * 执行指令
     */
    @Override
    public void run() {
//        FileTask fileTask = new FileTask(consoleText);
//        fileTask.run();
        CmdCode cmdCode = CmdCode.transform(consoleText);
        if (cmdCode == CmdCode.LIST || consoleText.contains("1")) {
            log.debug("列表指令");
            try {
                WriteBuffer.writeAppend("<<<the list command accept");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (cmdCode == CmdCode.DOWNLOAD) {
        } else if (cmdCode == CmdCode.UPLOAD) {
        } else {
        }
    }
}
