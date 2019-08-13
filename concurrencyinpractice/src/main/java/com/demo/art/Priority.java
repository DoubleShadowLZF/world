package com.demo.art;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Double
 * @Function 线程优先级
 */

@Slf4j
public class Priority {
    private static volatile boolean notStart = true;
    private static volatile boolean notEnd = true;

    public static void main(String[] args) throws InterruptedException {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobs.add(job);
            Thread thread = new Thread(job, "Thread" + i);
            thread.setPriority(priority);
            thread.start();
        }
        notStart = false;
        TimeUnit.SECONDS.sleep(10);
        notEnd = false;
        for (Job job : jobs) {
            log.info("Job Priority : {} , Count : {}", job.priority, job.jobCount);
        }
    }

    static class Job implements Runnable {

        private int priority;
        private long jobCount;

        public Job(int priority) {
            this.priority = priority;
        }

        @Override
        public void run() {
            while (notStart) {
                Thread.yield();
            }
            while (notEnd) {
                Thread.yield();
                jobCount++;
            }
        }
    }
}

/*
    09:44:03.599 [main] INFO com.demo.art.Priority - Job Priority : 1 , Count : 13927102
    09:44:03.609 [main] INFO com.demo.art.Priority - Job Priority : 1 , Count : 14064338
    09:44:03.609 [main] INFO com.demo.art.Priority - Job Priority : 1 , Count : 13953406
    09:44:03.609 [main] INFO com.demo.art.Priority - Job Priority : 1 , Count : 14038128
    09:44:03.613 [main] INFO com.demo.art.Priority - Job Priority : 1 , Count : 13983673
    09:44:03.613 [main] INFO com.demo.art.Priority - Job Priority : 10 , Count : 14009452
    09:44:03.613 [main] INFO com.demo.art.Priority - Job Priority : 10 , Count : 14024690
    09:44:03.613 [main] INFO com.demo.art.Priority - Job Priority : 10 , Count : 14027510
    09:44:03.613 [main] INFO com.demo.art.Priority - Job Priority : 10 , Count : 13943720
    09:44:03.613 [main] INFO com.demo.art.Priority - Job Priority : 10 , Count : 14028453
    即使设置了优先级，线程也不不会根据优先级依次运行
 */