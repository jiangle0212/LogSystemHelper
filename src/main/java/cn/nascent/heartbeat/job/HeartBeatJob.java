package cn.nascent.heartbeat.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author wei
 * @date 2018/12/18
 * <p>
 * 发送心跳信息的任务，在quartz中job表示一个调度的任务
 */
public class HeartBeatJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println("I am a QuartzManager packet");

    }
}
