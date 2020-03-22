package cn.crxy.scheduler.context.util;

import org.quartz.Scheduler;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import cn.crxy.scheduler.context.task.TaskScheduler;

/**
 * @author  吴超.
 */
public class SpiderSchedulerFactoryBean implements FactoryBean<TaskScheduler>, InitializingBean {
    private TaskScheduler scheduler;
    private SchedulerFactoryBean schedulerFactoryBean;

    public SpiderSchedulerFactoryBean(SchedulerFactoryBean schedulerFactoryBean) {
        this.schedulerFactoryBean = schedulerFactoryBean;
    }

    @Override
    public TaskScheduler getObject() {
        return this.scheduler;
    }

    public Class<?> getObjectType() {
        return (this.scheduler != null ? this.scheduler.getClass() : Scheduler.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.scheduler = new TaskScheduler(schedulerFactoryBean.getObject());
    }
}
