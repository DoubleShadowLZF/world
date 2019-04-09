package org.webserver.demo;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.Collection;

public class FileUtilsTest {
    @Test
    public void list(){
        String path = "D:\\Users\\Double\\Documents";
        Collection<File> files = FileUtils.listFiles(new File(path), null, false);
        System.out.println(files);
    }
}
