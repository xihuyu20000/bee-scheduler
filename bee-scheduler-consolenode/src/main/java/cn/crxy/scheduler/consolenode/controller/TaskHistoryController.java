package cn.crxy.scheduler.consolenode.controller;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.crxy.scheduler.consolenode.model.ExecutedTask;
import cn.crxy.scheduler.consolenode.model.Pageable;
import cn.crxy.scheduler.consolenode.service.TaskService;

import java.util.List;

/**
 * @author  吴超
 */
@RestController
public class TaskHistoryController {
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private TaskService taskService;

    @GetMapping("/task/history/list")
    public ResponseEntity<Pageable<ExecutedTask>> taskHistoryList(String keyword, Integer page) throws Exception {

        keyword = StringUtils.trimToEmpty(keyword);
        page = page == null ? 1 : page;

        // 查询任务历史信息
        Pageable<ExecutedTask> result = taskService.queryTaskHistory(scheduler.getSchedulerName(), keyword, page);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/task/history/detail")
    public ResponseEntity<ExecutedTask> taskHistoryDetail(String fireId) throws Exception {
        ExecutedTask result = taskService.getTaskHistory(fireId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }


    @GetMapping("/task/history/clearall")
    public void clearall() throws Exception {
    	taskService.removeAllTaskHistory();
    }

    @GetMapping("/task/history/query-suggestions")
    public ResponseEntity<List<String>> querySuggestions(String input) throws Exception {
        return ResponseEntity.ok(taskService.taskHistoryQuerySuggestion(scheduler.getSchedulerName(), input));
    }
}
