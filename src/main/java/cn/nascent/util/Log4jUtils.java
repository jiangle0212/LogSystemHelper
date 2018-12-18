package cn.nascent.util;

import java.io.File;

/**
 * @author wei
 * @date 2018/12/10
 * <p>
 * <p>
 * log4j 的一些配置与工具类
 */
public class Log4jUtils {

    /**
     * 引入这个jar包的项目名
     */
    public static volatile String PROJECT_NAME = null;

    /**
     * log4j.appender.kafka.layout
     */
    public static final String KAFKA_LAYOUT_PATTERN = "org.apache.log4j.PatternLayout";

    /**
     * log4j.appender.kafka.layout.ConversionPattern
     * 未定义时才使用
     */
    public static final String KAFKA_DEFAULT_LAYOUT_CONVERSIONPATTERN = "%n%-d{yyyy-MM-dd HH:mm:ss:sss}%n[%p]-[Thread: %t]-[%C.%M()]: %m%n";

    /**
     * log4j.appender.kafka.SyncSend
     */
    public static final boolean KAFKA_SYN_SEND = true;

    /**
     * 用于获取引入这个jar包的项目的项目名
     *
     * @return 项目名
     */
    public static String getProjectName() {

        if (Log4jUtils.PROJECT_NAME == null) {
            synchronized (Log4jUtils.class) {
                if (Log4jUtils.PROJECT_NAME == null) {

                    Log4jUtils.PROJECT_NAME = "Project:";
                    File fileUtil = new File(".");
                    String absolutePath = fileUtil.getAbsolutePath();
                    String[] paths = absolutePath.split(getSeparator());
                    Log4jUtils.PROJECT_NAME = paths[paths.length - 2].toLowerCase() + "  :  ";
                }
            }
        }

        return Log4jUtils.PROJECT_NAME;


    }

    /**
     * 获取当前系统的文件分隔符
     *
     * @return 返回当前文件分隔符
     */
    private static String getSeparator() {

        if (OS_NAME.matches("Windows(.*)")) {
            return "\\\\";
        } else {
            return System.getProperty("file.separator");
        }

    }

    /**
     * 获取操作系统的类型
     */
    private static final String OS_NAME = System.getProperty("os.name");


    /**
     * 开始心跳机制
     */
    static {
        Thread heartBeat = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("HeartBeat------------------------<<<<<<>>>>>");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        heartBeat.setDaemon(true);
        System.out.println("HeartBeat");
        heartBeat.start();
    }
}
