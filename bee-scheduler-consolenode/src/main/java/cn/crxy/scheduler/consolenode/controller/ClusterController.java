package cn.crxy.scheduler.consolenode.controller;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.crxy.scheduler.consolenode.model.ClusterSchedulerNode;
import cn.crxy.scheduler.consolenode.service.SchedulerService;

import java.util.List;

@RestController
@ConditionalOnProperty(name = "cluster")
public class ClusterController {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private SchedulerService schedulerService;

    @RequestMapping("/cluster/nodes")
    public ResponseEntity<List<ClusterSchedulerNode>> nodes() throws Exception {
        return ResponseEntity.ok(schedulerService.getAllClusterScheduler(scheduler.getSchedulerName()));
    }
}