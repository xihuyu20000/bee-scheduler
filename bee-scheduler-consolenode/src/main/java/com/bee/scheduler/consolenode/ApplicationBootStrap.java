package com.bee.scheduler.consolenode;

import com.bee.scheduler.consolenode.core.BuildInTaskModuleLoader;
import com.bee.scheduler.consolenode.core.ClassPathJarArchiveTaskModuleLoader;
import com.bee.scheduler.consolenode.web.PassportInterceptor;
import com.bee.scheduler.context.BeeSchedulerFactoryBean;
import com.bee.scheduler.context.CustomizedQuartzSchedulerFactoryBean;
import com.bee.scheduler.context.task.TaskModuleRegistry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class ApplicationBootStrap {
    private static Log logger = LogFactory.getLog(ApplicationBootStrap.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ApplicationBootStrap.class);
        //检查启动参数
        SimpleCommandLinePropertySource commandLinePropertySource = new SimpleCommandLinePropertySource(args);
        //dburl
        String dburl = commandLinePropertySource.getProperty("dburl");
        if (dburl == null) {
            dburl = "jdbc:mysql://localhost:3306/bee?user=root&password=admin&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
//            throw new RuntimeException("please specify --dburl in args(e.g. --dburl=jdbc:mysql://127.0.0.1:3306/bee-scheduler?user=root&password=root&useSSL=false&characterEncoding=UTF-8)");
        }
        if (dburl.startsWith("jdbc:mysql://")) {
            app.setAdditionalProfiles("mysql");
        } else if (dburl.startsWith("jdbc:postgresql://")) {
            app.setAdditionalProfiles("postgresql");
        } else {
            throw new RuntimeException("invalid argument:dburl");
        }
        //load executor modules
        app.addInitializers(applicationContext -> {
            try {
                logger.info("loading build-in task modules...");
                new BuildInTaskModuleLoader().load().forEach(TaskModuleRegistry::register);
                logger.info("loading classpath-jar-archive task modules...");
                new ClassPathJarArchiveTaskModuleLoader().load().forEach(TaskModuleRegistry::register);
            } catch (Exception e) {
                throw new ApplicationContextException("scheduler init failed!", e);
            }
        });
        app.run(args);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer PropertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new PassportInterceptor()).excludePathPatterns("/public/**", "/configs", "/passport/**", "/error", "/", "");
            }
        };
    }

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
                errorAttributes.remove("exception");
                errorAttributes.remove("status");
                errorAttributes.remove("errors");
                errorAttributes.remove("trace");
                errorAttributes.remove("path");
                return errorAttributes;
            }
        };
    }

    //调度器工厂
    @Bean
    public CustomizedQuartzSchedulerFactoryBean schedulerFactoryBean(Environment env, DataSource dataSource) {
        CustomizedQuartzSchedulerFactoryBean beeSchedulerFactoryBean = new CustomizedQuartzSchedulerFactoryBean("BeeScheduler", dataSource);
        beeSchedulerFactoryBean.setClusterMode(env.containsProperty("cluster"));
        Optional.ofNullable(env.getProperty("thread-pool-size", Integer.TYPE)).ifPresent(beeSchedulerFactoryBean::setThreadPoolSize);
        Optional.ofNullable(env.getProperty("instance-id")).ifPresent(beeSchedulerFactoryBean::setInstanceId);
        return beeSchedulerFactoryBean;
    }

    @Bean
    public BeeSchedulerFactoryBean beeSchedulerFactoryBean(CustomizedQuartzSchedulerFactoryBean customizedQuartzSchedulerFactoryBean) {
        return new BeeSchedulerFactoryBean(customizedQuartzSchedulerFactoryBean);
    }
}