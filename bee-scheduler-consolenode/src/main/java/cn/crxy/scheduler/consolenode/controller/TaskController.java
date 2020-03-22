package cn.crxy.scheduler.consolenode.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.crxy.scheduler.consolenode.exception.BadRequestException;
import cn.crxy.scheduler.consolenode.model.Pageable;
import cn.crxy.scheduler.consolenode.model.TaskDetail;
import cn.crxy.scheduler.consolenode.service.SpiderService;
import cn.crxy.scheduler.consolenode.service.TaskService;
import cn.crxy.scheduler.context.common.TaskSpecialGroup;
import cn.crxy.scheduler.context.model.QuickTaskConfig;
import cn.crxy.scheduler.context.model.TaskConfig;
import cn.crxy.scheduler.context.task.TaskScheduler;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author  吴超
 */
@RestController
public class TaskController {
    @Autowired
    private TaskScheduler scheduler;

    @Autowired
    private TaskService taskService;
    
    @Autowired
    private SpiderService spiderService;

    @GetMapping("/task/groups")
    public ResponseEntity<List<String>> taskGroups() throws Exception {
        return ResponseEntity.ok(scheduler.getTaskGroups());
    }

    @GetMapping("/task/query-suggestions")
    public ResponseEntity<List<String>> querySuggestions(String input) throws Exception {
        return ResponseEntity.ok(taskService.taskQuerySuggestion(scheduler.getSchedulerName(), input));
    }

    @GetMapping("/task/list")
    public ResponseEntity<Pageable<TaskDetail>> task(String keyword, Integer page) throws Exception {
        keyword = StringUtils.trimToEmpty(keyword);
        page = page == null ? 1 : page;

        Pageable<TaskDetail> queryResult = taskService.queryTask(scheduler.getSchedulerName(), keyword, page);
        return ResponseEntity.ok(queryResult);
    }

    @PostMapping("/task/new")
    public void newTask(@RequestBody TaskConfig taskConfig) throws Exception {
        taskConfig.setName(StringUtils.trimToEmpty(taskConfig.getName()));
        taskConfig.setGroup(StringUtils.trimToEmpty(taskConfig.getGroup()));

        if (StringUtils.isEmpty(taskConfig.getName())) {
            throw new BadRequestException("请输入任务名称");
        } else {
            if (!Pattern.matches("^[A-Za-z0-9_]+$", taskConfig.getName())) {
                throw new BadRequestException("任务名称只允许使用字母、数字和下划线，请检查");
            }
        }
        if (StringUtils.isEmpty(taskConfig.getGroup())) {
            throw new BadRequestException("请输入任务所属组");
        } else {
            if (!Pattern.matches("^[A-Za-z0-9_]+$", taskConfig.getGroup())) {
                throw new BadRequestException("任务所属组只允许使用字母、数字和下划线，请检查");
            }
        }
        if (StringUtils.isEmpty(taskConfig.getTaskModule())) {
            throw new BadRequestException("请选择任务组件");
        }
        if (StringUtils.isNotEmpty(taskConfig.getParams())) {
            try {
                JSON.parseObject(taskConfig.getParams());
            } catch (Exception e) {
                throw new BadRequestException("任务参数输入有误，必须是JSON格式");
            }
        }
        if (StringUtils.isNotEmpty(taskConfig.getLinkageRule())) {
            try {
                JSON.parseArray(taskConfig.getLinkageRule());
            } catch (Exception e) {
                throw new BadRequestException("联动任务规则输入有误，必须是JSON格式");
            }
        }
        if (TaskSpecialGroup.contains(taskConfig.getGroup())) {
            throw new BadRequestException("任务组不允许使用系统保留关键词:" + taskConfig.getGroup());
        }

        if (taskConfig.getScheduleType() == TaskConfig.SCHEDULE_TYPE_CRON_TRIGGER) {
            TaskConfig.ScheduleTypeCronOptions scheduleOptions = taskConfig.getScheduleTypeCronOptions();
            if (!CronExpression.isValidExpression(scheduleOptions.getCron())) {
                throw new BadRequestException("Cron表达式输入有误");
            }
        }
        scheduler.schedule(taskConfig);
    }

    @GetMapping("/task/detail")
    public ResponseEntity<TaskConfig> detail(String group, String name) throws Exception {
        return ResponseEntity.ok(scheduler.getTaskConfig(group, name));
    }

    @PostMapping("/task/edit")
    public void edit(@RequestBody TaskConfig taskConfig) throws Exception {
        if (StringUtils.isNotEmpty(taskConfig.getParams())) {
            try {
                JSON.parseObject(taskConfig.getParams());
            } catch (Exception e) {
                throw new BadRequestException("任务参数输入有误，必须是JSON格式");
            }
        }
        if (StringUtils.isNotEmpty(taskConfig.getLinkageRule())) {
            try {
                JSON.parseArray(taskConfig.getLinkageRule());
            } catch (Exception e) {
                throw new BadRequestException("联动任务规则输入有误，必须是JSON格式");
            }
        }
        if (taskConfig.getScheduleType() == TaskConfig.SCHEDULE_TYPE_CRON_TRIGGER) {
            TaskConfig.ScheduleTypeCronOptions scheduleOptions = taskConfig.getScheduleTypeCronOptions();
            if (!CronExpression.isValidExpression(scheduleOptions.getCron())) {
                throw new BadRequestException("Cron表达式输入有误");
            }
        }
        scheduler.reschedule(taskConfig);
    }

    @PostMapping("/task/delete")
    public void delete(String[] taskIds) throws Exception {
        for (String taskId : taskIds) {
            String[] group$name = StringUtils.split(taskId, "-");
            scheduler.unschedule(group$name[0], group$name[1]);
        }
    }

    @PostMapping("/task/pause")
    public void pause(String[] taskIds) throws Exception {
        for (String taskId : taskIds) {
            String[] group$name = StringUtils.split(taskId, "-");
            String group = group$name[0], name = group$name[1];
            scheduler.pause(group, name);
        }
    }

    @PostMapping("/task/resume")
    public void resume(String[] taskIds) throws Exception {
        for (String taskId : taskIds) {
            String[] group$name = StringUtils.split(taskId, "-");
            String group = group$name[0], name = group$name[1];
            scheduler.resume(group, name);
        }
    }

    @PostMapping("/task/execute")
    public void execute(String[] taskIds) throws Exception {
        for (String taskId : taskIds) {
            String[] group$name = StringUtils.split(taskId, "-");
            String name = group$name[1];
            String group = group$name[0];
            scheduler.trigger(group, name);
        }
    }

    @PostMapping("/task/tmp")
    public void quickTask(@RequestBody QuickTaskConfig quickTaskConfig) throws Exception {
        quickTaskConfig.setName(StringUtils.trimToEmpty(quickTaskConfig.getName()));

        if (StringUtils.isEmpty(quickTaskConfig.getName())) {
            throw new BadRequestException("请输入任务名称");
        } else {
            if (!Pattern.matches("^[A-Za-z0-9_]+$", quickTaskConfig.getName())) {
                throw new BadRequestException("任务名称只允许使用字母、数字和下划线，请检查");
            }
        }

        if (StringUtils.isEmpty(quickTaskConfig.getTaskModule())) {
            throw new BadRequestException("请选择任务组件");
        }

        scheduler.quickTask(quickTaskConfig);
    }
}
