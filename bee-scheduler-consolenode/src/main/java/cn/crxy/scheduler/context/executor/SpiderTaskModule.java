package cn.crxy.scheduler.context.executor;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.io.Files;

import cn.crxy.scheduler.context.core.ExecutionContext;
import cn.crxy.scheduler.context.core.ExecutionResult;
import cn.crxy.scheduler.context.core.ExecutorModule;
import cn.crxy.scheduler.context.dao.SpiderDao;
import cn.crxy.scheduler.context.util.ProcessTool;


/**
 * @author  吴超 该组件提供运行服务端Python脚本的功能
 */
public class SpiderTaskModule implements ExecutorModule {
    @Override
    public String getId() {
        return "SpiderTaskModule";
    }

    @Override
    public String getName() {
        return "SpiderTaskModule";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String getAuthor() {
        return "吴超";
    }

    @Override
    public String getDescription() {
        return "该组件提供运行服务端爬虫程序的功能";
    }

    @Override
    public String getParamTemplate() {
        return "{\r" +
        "    \"spider\":\"\",\r" +
        "    \"python\":\"\"\r" +
        "}";        
    }

    @Override
    public ExecutionResult exec(ExecutionContext context) throws Exception {
        JSONObject taskParam = context.getParam();
        Log logger = context.getLogger();
        
        String python = taskParam.getString("python");
        if(Strings.isNullOrEmpty(python)) {
        	python = "python";
        }
        String spiderId = taskParam.getString("spider");
        final File tempFile = File.createTempFile(System.currentTimeMillis()+"", ".py");//创建临时文件
        String code = new SpiderDao().getSpider(Long.parseLong(spiderId)).get("params").toString();
        Files.write(code, tempFile, Charset.forName("UTF-8"));
        String cmd = python+" "+tempFile.getAbsolutePath();
        String back = new ProcessTool().run(cmd);
        logger.info("运行结果: <p>" + back);
        JSONObject data = new JSONObject();
        data.put("echo", back);
        tempFile.delete();
        return ExecutionResult.success(data);
    }
}
