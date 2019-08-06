package guava.io;

import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

@Slf4j
public class FilesTest {

    String path = "D:\\a.txt";

    /**
     * 文件操作
     * @throws IOException
     */
    @Test
    public void fileOperateTest() throws IOException {
        Files.write("hello world!".getBytes(),new File(path)); //往文件写入数据，如果文件不存在，则创建文件
//        Files.append("Second row",new File(path),Charset.defaultCharset()); //往文件追加字符串，不会换行

//        log.debug("读取文本内容为：{}",Files.toString(new File(path), Charset .defaultCharset())); //读取文件中的内容
//        Files.copy(new File(path),new File("D:\\b.txt")); //拷贝文件为指定文件
//        Files.move(new File(path),new File("D:\\c\\aCopy.txt")); //移动文件，第二个参数必须指定文件名称,如果指定移动到的目的路径不存在，则抛出异常 FileNotFoundException

    }

    /**
     * 文件路径，名称操作
     */
    @Test
    public void pathTest() throws IOException {
        path = "D:\\Document\\sunrizetech\\1.森锐花名册电子版V1.5（姓名）.xls";
        log.debug("获取文件的拓展名：{}",Files.getFileExtension(path));
        log.debug("获取不带文件拓展名的文件名：{}",Files.getNameWithoutExtension(path));
        log.debug("获取规范文件路径：{}",Files.simplifyPath(path));
        Files.touch(new File(path)); //刷新文件的操作时间为当前时间

        log.debug("breadthFirst：");
        File dir = new File("D:\\home");
        Iterable<File> files = Files.fileTraverser().breadthFirst(dir);
        for (File file : files ) {
            log.debug("{}{}",file.isDirectory() ? "/" : "" ,file.getName());
        }

        log.debug("depthFirstPreOrder：");
        files = Files.fileTraverser().depthFirstPreOrder(dir);
        for (File file : files ) {
            log.debug("{}{}",file.isDirectory() ? "/" : "" ,file.getName());
        }

        log.debug("depthFirstPostOrder：");
        files = Files.fileTraverser().depthFirstPostOrder(dir);
        for (File file : files ) {
            log.debug("{}{}",file.isDirectory() ? "/" : "" ,file.getName());
        }

        File dirP  = new File("D:\\a\\a.txt");
        Files.createParentDirs(dirP); //必要时，为文件创建父目录，但不会创建文件

    }
}
