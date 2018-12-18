package cn.nascent.heartbeat.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;


/**
 * @author wei
 * @date 2018/12/18
 * <p>
 * 功能：quartz 定时任务的管理
 */
public class QuartzManager {

    /**
     * 调度器工厂
     */
    private static final SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    /**
     * 默认的任务组名
     */
    private static final String JOB_GROUP_NAME = "MY_JOBGROUP_NAME";
    /**
     * 默认的触发器组名
     */
    private static final String TRIGGER_GROUP_NAME = "MY_TRIGGERGROUP_NAME";


    /**
     * 添加一个定时任务
     *
     * @param jobName  任务名
     * @param jobClazz 期望任务调度执行的组件,实现了Job接口的类
     * @param interval 执行的间隔时间(触发时间),单位:秒
     */
    public static void addJob(String jobName, Class jobClazz, int interval) {

        try {


            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            JobKey jobKey = new JobKey(jobName, QuartzManager.JOB_GROUP_NAME);
            JobDetail jobDetail = JobBuilder.newJob(jobClazz).withIdentity(jobKey).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, QuartzManager.TRIGGER_GROUP_NAME).
                    withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(interval).repeatForever()).build();
            scheduler.scheduleJob(jobDetail, trigger);

            scheduler.start();
        } catch (SchedulerException e) {
            System.err.println("定时任务任务添加失败");
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 添加一个定时任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClazz         期望任务调度执行的组件,实现了Job接口的类
     * @param interval         执行的间隔时间(触发时间),单位:秒
     */
    public static void addJob(String jobName, String jobGroupName, String triggerName,
                              String triggerGroupName, Class jobClazz, int interval) {

        try {

            Scheduler scheduler = QuartzManager.schedulerFactory.getScheduler();
            JobKey jobKey = new JobKey(jobName, jobGroupName);
            JobDetail jobDetail = JobBuilder.newJob(jobClazz).withIdentity(jobKey).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName).
                    withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(interval).repeatForever()).build();
            scheduler.scheduleJob(jobDetail, trigger);

            scheduler.start();

        } catch (SchedulerException e) {
            System.err.println("定时任务任务添加失败");
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }


    /**
     * 移除一个任务
     * 这个任务是使用的默认的任务组名，默认的触发器组名
     *
     * @param jobName 任务名
     */
    public static void removeJob(String jobName) {

        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, QuartzManager.TRIGGER_GROUP_NAME);
        JobKey jobKey = JobKey.jobKey(jobName, QuartzManager.JOB_GROUP_NAME);

        try {
            Scheduler scheduler = QuartzManager.schedulerFactory.getScheduler();
            Trigger trigger = scheduler.getTrigger(triggerKey);

            if (trigger == null) {
                return;
            }

            //停止触发器
            scheduler.pauseTrigger(triggerKey);
            //移除触发器
            scheduler.unscheduleJob(triggerKey);
            //删除任务
            scheduler.deleteJob(jobKey);

            System.err.println("删除任务: " + jobName + "  完成");
        } catch (SchedulerException e) {
            System.err.println("任务删除失败");
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 移除一个任务
     * 这个任务是使用了自定义任务组名，自定义触发器名的任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     */
    public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {

        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);

        try {
            Scheduler scheduler = QuartzManager.schedulerFactory.getScheduler();
            Trigger trigger = scheduler.getTrigger(triggerKey);

            if (trigger == null) {
                return;
            }

            //停止触发器
            scheduler.pauseTrigger(triggerKey);
            //移除触发器
            scheduler.unscheduleJob(triggerKey);
            //删除任务
            scheduler.deleteJob(jobKey);

            System.err.println("删除任务:  " + jobName + " 完成");
        } catch (SchedulerException e) {
            System.err.println("任务删除失败");
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }
}
