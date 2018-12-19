package cn.nascent.util;

/**
 * @author wei
 * @date 2018/12/10
 * <p>
 * <p>
 * kafka 相关的一些配置
 */
public class KafkaUtils {

    /**
     * 使用Log4j输出的日志相关的kafka主题
     */
    public static final String TOPIC = "logstash-kafka-topic";

    /**
     * kafka Broker地址
     */
    public static final String BROKER_LIST = "192.168.80.176:9092";


    /**
     * 发送心跳信息到这个topic下
     */
    public static final String HEARTBEAT_TOPIC = "heartbeat-kafka-topic";

    /**
     * 发送心跳信息的时间间隔(秒)
     */
    public static final Integer HEARTBEAT_PACKET_SEND_INTERVAL = 5;


}
