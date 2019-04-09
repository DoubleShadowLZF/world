package org.webserver.demo.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.File;

/**
 * 文件基础类
 */
@Data
@Accessors(chain = true)
public class FileEntity {
    private String fileName;

    private File file;

    public File getFile(){
        return fileName != null ? new File(fileName) : null;
    }

}
