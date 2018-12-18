package cn.nascent.kafka;


import cn.nascent.util.KafkaUtils;
import cn.nascent.util.Log4jUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author wei
 * @date 2018/12/10
 * <p>
 * <p>
 * 功能：将log4j产生的日志输送到kafka下
 */
public class KafkaLog4jAppender extends org.apache.kafka.log4jappender.KafkaLog4jAppender {


    public KafkaLog4jAppender() {

        this.setBrokerList(KafkaUtils.BROKER_LIST);
        this.setTopic(KafkaUtils.TOPIC);
        this.setSyncSend(Log4jUtils.KAFKA_SYN_SEND);

    }

    /**
     * 当用户未定义了layout并且没有指定layout.ConversionPattern的时候，那么就使用默认的模板
     */ {
        if (this.layout == null) {
            this.setLayout(new PatternLayout(Log4jUtils.KAFKA_DEFAULT_LAYOUT_CONVERSIONPATTERN));
        }
    }


    @Override
    protected void append(LoggingEvent event) {

        String message = this.subAppend(event);
        LogLog.debug("[" + (new Date(event.getTimeStamp())) + "]" + " : " + event.getMessage());
        Future<RecordMetadata> response = this.getProducer().send(new ProducerRecord<>(this.getTopic(), message.getBytes(StandardCharsets.UTF_8)));

        if (this.getSyncSend()) {
            try {
                response.get();
            } catch (InterruptedException | ExecutionException e) {
                if (!this.getIgnoreExceptions()) {
                    throw new RuntimeException(e);
                }

                LogLog.debug("Exception while getting response {}", e);
            }
        }

    }

    /**
     * 将日志中的信息提取出来,以字符串的形式返回
     *
     * @param event
     * @return
     */
    private String subAppend(LoggingEvent event) {

        return (Log4jUtils.getProjectName() + this.layout.format(event));

    }
}
