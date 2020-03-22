package cn.crxy.scheduler.consolenode.service;


import java.util.List;

import cn.crxy.scheduler.consolenode.model.ClusterSchedulerNode;

/**
 * @author  吴超
 */
public interface SchedulerService {
    List<ClusterSchedulerNode> getAllClusterScheduler(String schedulerName);
}
