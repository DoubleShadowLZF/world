package org.webserver.demo.task;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.EmptyFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.webserver.demo.common.Constants;
import org.webserver.demo.common.WriteBuffer;
import org.webserver.demo.entity.CmdCode;
import org.webserver.demo.entity.FileEntity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 文件任务
 * <p>
 *     中文乱码
 * </p>
 */
@Slf4j
@Deprecated
public class FileTask implements Runnable{

    private String consoleText;

    public FileTask(String consoleText){
        this.consoleText = consoleText;
    }

    /**
     * 文件列表
     * @param file
     * @return
     */
    public List fileList(FileEntity file) {
        log.info("文件列表操作");
        File qryFile = file.getFile();
        qryFile = qryFile != null ? qryFile : new File(Constants.BASE_PATH);
        IOFileFilter fileFilter = FileFilterUtils.and(EmptyFileFilter.NOT_EMPTY,
                FileFilterUtils.suffixFileFilter("log"));
        Collection<File> files = FileUtils.listFilesAndDirs(qryFile, fileFilter, DirectoryFileFilter.INSTANCE);
        return Arrays.asList(files);
    }

    public boolean download(FileEntity file) {
        log.info("文件下载操作");
        return false;
    }

    public boolean upload(FileEntity file) {
        log.info("文件上传操作");
        return false;
    }

    /**
     * 无效操作
     * @param fileEntity
     */
    public void invalid(FileEntity fileEntity) {
        log.error("无效操作");
    }

    @Override
    public void run() {
        CmdCode cmdCode = CmdCode.transform(consoleText);
        FileEntity fileEntity = new FileEntity();
        if(cmdCode == CmdCode.LIST || consoleText.contains("1")){
            List fileList = fileList(fileEntity);
            log.info("查询到的文件夹：{}",fileList);
            //写入buffer ，输出给客户端
            try {
                WriteBuffer.writeAppend(fileList.toString());
            } catch (UnsupportedEncodingException e) {
                log.error("转码失败");
            }
        }else if(cmdCode == CmdCode.DOWNLOAD){
            download(fileEntity);
        }else if(cmdCode == CmdCode.UPLOAD){
            upload(fileEntity);
        }else{
            invalid(fileEntity);
        }
    }

}
