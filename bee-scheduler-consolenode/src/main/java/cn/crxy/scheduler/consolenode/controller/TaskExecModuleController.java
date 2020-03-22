package cn.crxy.scheduler.consolenode.controller;

import com.alibaba.fastjson.JSONObject;

import cn.crxy.scheduler.context.core.ExecutorModule;
import cn.crxy.scheduler.context.task.TaskModuleRegistry;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author  吴超
 */
@RestController
public class TaskExecModuleController {
    @GetMapping("/task-module/list")
    public ResponseEntity<JSONObject> taskModuleList() throws Exception {
        JSONObject taskModules = new JSONObject();
        for (String taskModuleId : TaskModuleRegistry.TaskModuleMap.keySet()) {
            ExecutorModule taskModule = TaskModuleRegistry.TaskModuleMap.get(taskModuleId);
            taskModules.put(taskModuleId, taskModule);
        }
        return ResponseEntity.ok(taskModules);
    }
}
