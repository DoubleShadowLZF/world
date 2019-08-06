package io;

import org.junit.Test;

import java.io.*;

public class IoDemo {

    @Test
    public void InputStreamDemo() throws IOException {
//        File fb = new File("bbb.txt");
//        System.out.println(fb.getAbsolutePath());

        String path = IoDemo.class.getResource("/").getPath();
        System.out.println(path);
//        File file = new File(path + "/aaa.txt");
        File file = new File(path + "/aaa.txt");

        FileInputStream fis = new FileInputStream(file);
        String pathB = path  + "bbb.txt";
//        String pathB = "bbb.txt";
        System.out.println("pathB:" + pathB );
        FileOutputStream fos = new FileOutputStream(pathB);
        byte[] bs = new byte[1024];
        int index = 0;
        try{
            while ((index = fis.read(bs) ) > 0 ){
                fos.write(bs,0,index);
            }
        }finally {
            fos.close();
            fis.close();
        }
    }

    @Test
    public void ReaderDemo()throws IOException{
        String path = IoDemo.class.getResource("/").getPath();
        FileReader fr = new FileReader(path + "aaa.txt");
        FileWriter fw = new FileWriter("bbb.txt");
        int index = 0;
        try{
            while((index = fr.read()) != -1){
                fw.write(index);
            }
        }finally {
            fw.flush();
            fw.close();
            fr.close();
        }
    }
}
