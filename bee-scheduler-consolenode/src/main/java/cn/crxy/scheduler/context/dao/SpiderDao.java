package cn.crxy.scheduler.context.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
@ConditionalOnProperty(name = "spring.datasource.platform", havingValue = "mysql")
public class SpiderDao {
    Log logger = LogFactory.getLog(getClass());
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
	public SpiderPojo getSpider(Long spiderId) {
		try {
            return jdbcTemplate.queryForObject("select t1.SPIDER_ID 'id', t1.SPIDER_NAME 'name', t1.SPIDER_GROUP 'group', t1.SPIDER_CMD 'cmd', t1.SPIDER_PARAMS 'params', t1.SPIDER_DESCRIPTION 'description' from BS_SPIDER t1 where t1.SPIDER_ID = ?", new BeanPropertyRowMapper<>(SpiderPojo.class), spiderId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
}
