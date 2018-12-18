package cn.nascent.test;

import cn.nascent.heartbeat.QuartzManager;
import org.apache.log4j.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class MoreThreadTest {
    public static final Integer threadCount = 3;
    public static final Integer totalCount = 100;
    public static final Logger log = Logger.getLogger(MoreThreadTest.class);

    public static void main(String[] args) throws InterruptedException {

        final Semaphore semaphore = new Semaphore(threadCount);
        final ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(totalCount);

        final String sendMess = threadCount + "个线程 ， 共" + totalCount + "条记录";
        for (int i = 0; i < totalCount; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        log.info(sendMess);
                        semaphore.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            });

        }

        countDownLatch.await();
        QuartzManager.removeJob("HeartBeat");

        executorService.shutdown();
    }
}
