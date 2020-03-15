package com.bee.scheduler.context.task;

import com.bee.scheduler.core.ExecutorModule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.TreeMap;

/**
 * @author  吴超
 */
public class TaskModuleRegistry {
    public static final TreeMap<String, ExecutorModule> TaskModuleMap = new TreeMap<>();
    private static Log logger = LogFactory.getLog(TaskModuleRegistry.class);

    public static void register(ExecutorModule taskModule) {
        logger.info("module registered: " + taskModule.getId());
        TaskModuleMap.put(taskModule.getId(), taskModule);
    }

    public static ExecutorModule get(String taskModuleId) {
        return TaskModuleMap.get(taskModuleId);
    }

    public static void unregister(String taskModuleId) {
        TaskModuleMap.remove(taskModuleId);
    }
}
