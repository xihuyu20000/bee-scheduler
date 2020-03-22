package cn.crxy.scheduler.consolenode.service;


import java.util.List;

import cn.crxy.scheduler.consolenode.model.ExecutedTask;
import cn.crxy.scheduler.consolenode.model.Pageable;
import cn.crxy.scheduler.consolenode.model.TaskDetail;

/**
 * @author  吴超
 */
public interface TaskService {
    Pageable<TaskDetail> queryTask(String schedulerName, String keyword, int page);

    List<String> taskQuerySuggestion(String schedulerName, String input);

    ExecutedTask getTaskHistory(String fireId);

    Pageable<ExecutedTask> queryTaskHistory(String schedulerName, String keyword, int page);

    List<String> taskHistoryQuerySuggestion(String schedulerName, String input);

	void removeAllTaskHistory();

}
