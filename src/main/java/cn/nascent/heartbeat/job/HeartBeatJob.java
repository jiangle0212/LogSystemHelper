package cn.nascent.heartbeat.job;

import cn.nascent.util.KafkaUtils;
import cn.nascent.util.Log4jUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author wei
 * @date 2018/12/18
 * <p>
 * 发送心跳信息的任务，在quartz中一个job表示一个被调度的任务
 */
public class HeartBeatJob implements Job {

    private static KafkaProducer<String, String> KAFKA_PRODUCER;
    private static final String HEARTBEAT_MESS = Log4jUtils.getProjectName() + "I am alive. Date:";

    /**
     * 构造出Kafka Producer
     */
    static {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", KafkaUtils.BROKER_LIST);
        properties.put("acks", "1");
        properties.put("retries", 3);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KAFKA_PRODUCER = new KafkaProducer<String, String>(properties);
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        /**
         * @TODO 在发送消息之前，还需要判断应用还存活或者应用挂掉了发送特定的消息去停止相应的定时任务
         */


        System.err.println("I am a HeartBeat packet");

        Future<RecordMetadata> response = HeartBeatJob.KAFKA_PRODUCER.send(new ProducerRecord<String, String>(KafkaUtils.HEARTBEAT_TOPIC,
                HeartBeatJob.HEARTBEAT_MESS));

        try {

            //当服务器返回错误，get方法会抛出异常，否则返回RecordMetadata对象，可以来获取消息的偏移量
            response.get();

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("服务器返回错误");
            throw new RuntimeException(e.getMessage());
        }

    }
}
