package org.world.concurrencyinpractice.problem.c_module;

import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@AllArgsConstructor
public class FileCrawler implements Runnable {
    private final BlockingQueue<File> fileQueue;
    private final FileFilter fileFilter;
    private final File root;

    @Override
    public void run() {
        try{
            crawl(root);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 文件遍历
     * @param root
     * @throws InterruptedException
     */
    private void crawl(File root)throws InterruptedException{
        File[] entries = root.listFiles(fileFilter);
        if(entries != null){
            for (File entry : entries) {
                if(entry.isDirectory()){
                    crawl(entry);
                }else if(! alreadyIndexed(entry)){
                    fileQueue.put(entry);
                }
            }
        }
    }

    private boolean alreadyIndexed(File entry){
        //判断文件是否已经被放置
        return false;
    }

    @AllArgsConstructor
    public class Indexer implements Runnable{
        private final BlockingQueue<File> queue;

        @Override
        public void run() {
            try{
                while(true){
                    indexFile(queue.take());
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * 建立索引
         * @param file
         */
        private void indexFile(File file){
            //建立索引
        }
    }

    public void startIndexing(File[] roots){
        List BOUND = Arrays.asList("此处应该是桌面的所有软件的集合");
        BlockingQueue<File> queue = new LinkedBlockingDeque<>(BOUND);
        /**
         * 桌面软件数量
         */
        Integer N_CONSUMERS = Integer.MAX_VALUE;
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        };
        for (File root : roots) {
            new Thread(new FileCrawler(queue,fileFilter,root)).start();;
        }
        for (int i = 0; i < N_CONSUMERS; i++) {
            new Thread(new Indexer(queue)).start();
        }
    }
}

