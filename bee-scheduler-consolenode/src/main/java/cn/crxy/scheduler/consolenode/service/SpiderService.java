package cn.crxy.scheduler.consolenode.service;
import cn.crxy.scheduler.consolenode.model.Pageable;
import cn.crxy.scheduler.consolenode.model.SpiderConfig;


/**
 * @author  吴超
 */
public interface SpiderService {

	void save(SpiderConfig spiderConfig);
	
	Pageable<SpiderConfig> querySpider(String spiderName, int pageNum);

	void delete(long spiderId);

	SpiderConfig get(long spiderId);
}
