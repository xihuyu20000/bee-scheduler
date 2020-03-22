package cn.crxy.scheduler.consolenode.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.crxy.scheduler.consolenode.exception.BadRequestException;
import cn.crxy.scheduler.consolenode.model.Pageable;
import cn.crxy.scheduler.consolenode.model.SpiderConfig;
import cn.crxy.scheduler.consolenode.service.SpiderService;
import cn.crxy.scheduler.consolenode.service.TaskService;
import cn.crxy.scheduler.context.model.QuickTaskConfig;
import cn.crxy.scheduler.context.task.TaskScheduler;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author  吴超
 */
@RestController
public class SpiderController {
    @Autowired
    private TaskScheduler scheduler;

    @Autowired
    private TaskService taskService;
    
    @Autowired
    private SpiderService spiderService;

//    @GetMapping("/task/groups")
//    public ResponseEntity<List<String>> taskGroups() throws Exception {
//        return ResponseEntity.ok(scheduler.getTaskGroups());
//    }
//
//    @GetMapping("/task/query-suggestions")
//    public ResponseEntity<List<String>> querySuggestions(String input) throws Exception {
//        return ResponseEntity.ok(taskService.taskQuerySuggestion(scheduler.getSchedulerName(), input));
//    }

    @GetMapping("/spider/list")
    public ResponseEntity<Pageable<SpiderConfig>> list(String keyword, Integer page) throws Exception {
    	keyword = StringUtils.trimToEmpty(keyword);
        page = page == null ? 1 : page;

        Pageable<SpiderConfig> queryResult = spiderService.querySpider(keyword, page);
        return ResponseEntity.ok(queryResult);
    }

    @PostMapping("/spider/new")
    public void newSpider(@RequestBody SpiderConfig spiderConfig) throws Exception {
        spiderConfig.setName(StringUtils.trimToEmpty(spiderConfig.getName()));
        spiderConfig.setGroup(StringUtils.trimToEmpty(spiderConfig.getGroup()));
        spiderConfig.setDescription(StringUtils.trimToEmpty(spiderConfig.getDescription()));
        spiderConfig.setParams(StringUtils.trimToEmpty(spiderConfig.getParams()));

        if (StringUtils.isEmpty(spiderConfig.getName())) {
            throw new BadRequestException("请输入爬虫名称");
        }
        if (StringUtils.isEmpty(spiderConfig.getGroup())) {
            throw new BadRequestException("请输入爬虫所属组");
        } 
        if (StringUtils.isEmpty(spiderConfig.getParams())) {
        	throw new BadRequestException("请输入爬虫代码");
        }
        
        spiderService.save(spiderConfig);
    }

//    @GetMapping("/task/detail")
//    public ResponseEntity<TaskConfig> detail(String group, String name) throws Exception {
//        return ResponseEntity.ok(scheduler.getTaskConfig(group, name));
//    }

    @PostMapping("/spider/edit")
    public void edit(@RequestBody SpiderConfig spiderConfig) throws Exception {
        spiderConfig.setName(StringUtils.trimToEmpty(spiderConfig.getName()));
        spiderConfig.setGroup(StringUtils.trimToEmpty(spiderConfig.getGroup()));
        spiderConfig.setDescription(StringUtils.trimToEmpty(spiderConfig.getDescription()));
        spiderConfig.setParams(StringUtils.trimToEmpty(spiderConfig.getParams()));

        if (StringUtils.isEmpty(spiderConfig.getName())) {
            throw new BadRequestException("请输入爬虫名称");
        } 
        if (StringUtils.isEmpty(spiderConfig.getGroup())) {
            throw new BadRequestException("请输入爬虫所属组");
        }
        if (StringUtils.isEmpty(spiderConfig.getParams())) {
        	throw new BadRequestException("请输入爬虫代码");
        }
        
        spiderService.save(spiderConfig);
    }

    @PostMapping("/spider/delete")
    public void delete(String[] spiderIds) throws Exception {
        for (String spiderId : spiderIds) {
        	spiderService.delete(Long.parseLong(spiderId));
        }
    }

//    @PostMapping("/task/pause")
//    public void pause(String[] taskIds) throws Exception {
//        for (String taskId : taskIds) {
//            String[] group$name = StringUtils.split(taskId, "-");
//            String group = group$name[0], name = group$name[1];
//            scheduler.pause(group, name);
//        }
//    }
//
//    @PostMapping("/task/resume")
//    public void resume(String[] taskIds) throws Exception {
//        for (String taskId : taskIds) {
//            String[] group$name = StringUtils.split(taskId, "-");
//            String group = group$name[0], name = group$name[1];
//            scheduler.resume(group, name);
//        }
//    }
//
//    @PostMapping("/task/execute")
//    public void execute(String[] taskIds) throws Exception {
//        for (String taskId : taskIds) {
//            String[] group$name = StringUtils.split(taskId, "-");
//            String name = group$name[1];
//            String group = group$name[0];
//            scheduler.trigger(group, name);
//        }
//    }
//
    @PostMapping("/spdier/tmp")
    public void quickTask(@RequestBody QuickTaskConfig quickTaskConfig) throws Exception {
        quickTaskConfig.setName(StringUtils.trimToEmpty(quickTaskConfig.getName()));

        if (StringUtils.isEmpty(quickTaskConfig.getName())) {
            throw new BadRequestException("请输入任务名称");
        } 

        if (StringUtils.isEmpty(quickTaskConfig.getTaskModule())) {
            throw new BadRequestException("请选择任务组件");
        }
        if (StringUtils.isNotEmpty(quickTaskConfig.getParams())) {
            try {
                JSONObject parseObject = JSON.parseObject(quickTaskConfig.getParams());
                Long spiderId = parseObject.getLong("spider");
                parseObject.put("spider", spiderService.get(spiderId).getParams());
                quickTaskConfig.setParams(parseObject.toJSONString());
            } catch (Exception e) {
                throw new BadRequestException("任务参数输入有误，必须是JSON格式");
            }
        }

        scheduler.quickTask(quickTaskConfig);
    }
}
