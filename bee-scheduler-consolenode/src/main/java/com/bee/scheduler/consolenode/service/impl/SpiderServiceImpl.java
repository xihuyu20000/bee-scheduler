package com.bee.scheduler.consolenode.service.impl;

import com.bee.scheduler.consolenode.dao.StandardDao;
import com.bee.scheduler.consolenode.model.ExecutedTask;
import com.bee.scheduler.consolenode.model.Pageable;
import com.bee.scheduler.consolenode.model.TaskDetail;
import com.bee.scheduler.consolenode.service.SpiderService;
import com.bee.scheduler.consolenode.service.TaskService;
import com.bee.scheduler.context.common.TaskExecState;
import com.bee.scheduler.context.common.TaskFiredWay;
import com.bee.scheduler.consolenode.model.SpiderConfig;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.terracotta.quartz.wrappers.TriggerWrapper;

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
