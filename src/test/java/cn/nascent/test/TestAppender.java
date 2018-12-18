package cn.nascent.test;

import org.apache.log4j.Logger;

/**
 * 测试appender是否工作正常
 */
public class TestAppender {
    private static Logger LOGGER = Logger.getLogger(TestAppender.class);


    public static void main(String[] args) throws InterruptedException {

        int times = 10;

        for (int i = 0; i < times; i++) {
            LOGGER.info("cn.nascent.kafka.KafkaLog4jAppender  Hello World" + i);

            Thread.sleep(400);
        }

       System.out.println(TestAppender.class.getName());

    }
}
