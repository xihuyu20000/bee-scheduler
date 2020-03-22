package cn.crxy.scheduler.consolenode.dao;

import java.util.List;

import cn.crxy.scheduler.consolenode.entity.User;
import cn.crxy.scheduler.consolenode.model.ClusterSchedulerNode;
import cn.crxy.scheduler.consolenode.model.ExecutedTask;
import cn.crxy.scheduler.consolenode.model.Pageable;
import cn.crxy.scheduler.consolenode.model.SpiderConfig;
import cn.crxy.scheduler.consolenode.model.TaskDetail;

public interface StandardDao {
    /**
     * ================= User =====================
     */
    User getUserByAccount$Pwd(String account, String pwd);

    void updateUserByAccount(User entity);

    /**
     * ================= Cluster =====================
     */
    List<ClusterSchedulerNode> getClusterSchedulerNodes(String schedulerName);

    /**
     * ================= Task =====================
     */
    Pageable<TaskDetail> queryTask(String schedulerName, List<String> taskNameList, List<String> taskGroupList, List<String> taskStateList, Integer pageNum, Integer pageSize);

    Pageable<String> queryTaskGroups(String schedulerName, String kw, Integer page, Integer pageSize);

    Pageable<String> queryTaskNames(String schedulerName, String kw, Integer page, Integer pageSize);

    /**
     * ================= TaskHistory =====================
     */
    ExecutedTask getTaskHistory(String fireId);

    Pageable<ExecutedTask> queryTaskHistory(String schedulerName, List<String> fireIdList, List<String> taskNameList, List<String> taskGroupList, List<String> execStateList, List<String> firedWayList, List<String> instanceIdList, Long firedTimeBefore, Long firedTimeAfter, Integer pageNum, Integer pageSize);

    Pageable<String> queryTaskHistoryGroups(String schedulerName, String kw, Integer pageNum, Integer pageSize);

    Pageable<String> queryTaskHistoryNames(String schedulerName, String kw, Integer pageNum, Integer pageSize);

    void removeAllTaskHistory();
    /**
     * ================= Spider =====================
     */
    void saveSpider(SpiderConfig spiderConfig);
    
    Pageable<SpiderConfig> querySpider(String spiderName, Integer pageNum, Integer pageSize);

	void deleteSpider(Long spiderId);

	SpiderConfig getSpider(Long spiderId);

	
}
