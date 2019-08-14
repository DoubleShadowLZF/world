package com.demo.art;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @author Double
 * @Function 管道输入/输出流
 */
@Slf4j
public class Piped {

    public static void main(String[] args) throws IOException {

        PipedReader in  = new PipedReader();
        PipedWriter out = new PipedWriter();
        in.connect(out);
//        out.connect(in);

        Print print = new Print(in);
        Thread printThread = new Thread(print, "printThread");
        printThread.start();

        int rec = 0;
        System.out.println("请输入：");
        try{
            while((rec = System.in.read()) != -1){
                out.write((char)rec);
            }
        }finally {
            out.close();
            in.close();
        }

    }


    static class Print implements Runnable {

        private PipedReader in;

        public Print(PipedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            int rec = 0;
            try {
                while ((rec = in.read()) != -1) {
//                    log.info("{}", (char) rec);
                    System.out.print((char)rec);
                }
            } catch (Exception e) {

            }
        }
    }
}
