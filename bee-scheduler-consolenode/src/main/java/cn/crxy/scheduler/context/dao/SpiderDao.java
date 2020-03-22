package cn.crxy.scheduler.context.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;



public class SpiderDao {
	private QueryRunner queryRunner;
	public SpiderDao() {
		this.queryRunner = new QueryRunner();
	}
	
	public Map<String, Object> getSpider(Long spiderId) throws Exception {
		try {
			String url = "jdbc:mysql://localhost:3306/bee?user=root&password=admin&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
			Connection conn = DriverManager.getConnection(url);
			String sql = "select t1.SPIDER_ID 'id', t1.SPIDER_NAME 'name', t1.SPIDER_GROUP 'group', t1.SPIDER_CMD 'cmd', t1.SPIDER_PARAMS 'params', t1.SPIDER_DESCRIPTION 'description' from BS_SPIDER t1 where t1.SPIDER_ID = ?";
			return this.queryRunner.query(conn, sql, new MapHandler(), spiderId);
        } catch (Exception e) {
            return null;
        }
	}
}
