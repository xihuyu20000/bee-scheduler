package cn.crxy.scheduler.consolenode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crxy.scheduler.consolenode.dao.StandardDao;
import cn.crxy.scheduler.consolenode.model.ClusterSchedulerNode;
import cn.crxy.scheduler.consolenode.service.SchedulerService;

import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private StandardDao standardDao;

    @Override
    public List<ClusterSchedulerNode> getAllClusterScheduler(String schedulerName) {
        return standardDao.getClusterSchedulerNodes(schedulerName);
    }
}
