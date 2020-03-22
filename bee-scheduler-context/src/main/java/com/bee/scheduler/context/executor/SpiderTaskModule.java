package com.bee.scheduler.context.executor;

import java.io.File;
import java.nio.charset.Charset;

import com.alibaba.fastjson.JSONObject;
import com.bee.scheduler.context.core.ExecutionContext;
import com.bee.scheduler.context.core.ExecutionResult;
import com.bee.scheduler.context.core.ExecutorModule;
import com.bee.scheduler.context.util.ProcessTool;
import com.google.common.base.Strings;
import com.google.common.io.Files;


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
        "    \"python\":\"\",\r" +
        "    \"spider\":\"\"\r" +
        "}";        
    }

    @Override
    public ExecutionResult exec(ExecutionContext context) throws Exception {
        JSONObject taskParam = context.getParam();
        String python = taskParam.getString("python");
        if(Strings.isNullOrEmpty(python)) {
        	python = "python";
        }
        String spider = taskParam.getString("spider");
//        final File tempFile = File.createTempFile(System.currentTimeMillis()+"", ".py");//创建临时文件
        File tempFile = new File("D:/a.py");
        
        Files.write(spider, tempFile, Charset.forName("UTF-8"));
        
        String cmd = python+" "+tempFile.getAbsolutePath();
        String back = new ProcessTool().run(cmd);
        System.out.println(cmd);
        System.out.println("运行结果 "+back);
        JSONObject data = new JSONObject();
        data.put("echo", back);
//        tempFile.delete();
        return ExecutionResult.success(data);
    }
}
