package cn.crxy.scheduler.consolenode.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"classpath:application.properties"}, ignoreResourceNotFound = false, encoding = "UTF-8", name = "application.properties")
//@ConfigurationProperties(prefix = "aaa")
public class AppProp {
    @Value("${server.port}")
    public String serverPort;
    @Value("${dburl}")
    public String dburl;
}
