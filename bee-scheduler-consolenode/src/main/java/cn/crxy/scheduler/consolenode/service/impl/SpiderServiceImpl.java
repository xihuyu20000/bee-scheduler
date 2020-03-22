package cn.crxy.scheduler.consolenode.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.terracotta.quartz.wrappers.TriggerWrapper;

import cn.crxy.scheduler.consolenode.dao.StandardDao;
import cn.crxy.scheduler.consolenode.model.ExecutedTask;
import cn.crxy.scheduler.consolenode.model.Pageable;
import cn.crxy.scheduler.consolenode.model.SpiderConfig;
import cn.crxy.scheduler.consolenode.model.TaskDetail;
import cn.crxy.scheduler.consolenode.service.SpiderService;
import cn.crxy.scheduler.consolenode.service.TaskService;
import cn.crxy.scheduler.context.common.TaskExecState;
import cn.crxy.scheduler.context.common.TaskFiredWay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;


@Service
public class SpiderServiceImpl implements SpiderService {
    private static final Integer DEFAULT_PAGE_SIZE = 20;
    @Autowired
    private StandardDao standardDao;

	@Override
	public void save(SpiderConfig spiderConfig) {
		standardDao.saveSpider(spiderConfig);
	}

	@Override
	public Pageable<SpiderConfig> querySpider(String spiderName, int pageNum) {
		return standardDao.querySpider(spiderName, pageNum, DEFAULT_PAGE_SIZE);
	}

	@Override
	public void delete(long spiderId) {
		standardDao.deleteSpider(spiderId);
	}

	@Override
	public SpiderConfig get(long spiderId) {
		return standardDao.getSpider(spiderId);
	}
}
