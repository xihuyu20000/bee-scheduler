package cn.crxy.scheduler.context.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;

import cn.crxy.scheduler.context.task.TaskExecutionLogger;

/**
 * @author  吴超
 */
public class BindingTaskLoggerOnThreadLocalListener extends TaskListenerSupport {

    @Override
    public String getName() {
        return "BindingTaskLoggerOnThreadLocalListener";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        ThreadLocalTaskLoggerAppender.TaskExecutionLoggerThreadLocal.set(new TaskExecutionLogger());
    }
}
