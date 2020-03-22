package com.bee.scheduler.context.executor;

import com.alibaba.fastjson.JSONObject;
import com.bee.scheduler.context.core.ExecutionContext;
import com.bee.scheduler.context.core.ExecutionResult;
import com.bee.scheduler.context.core.ExecutorModule;

import org.apache.commons.logging.Log;

/**
 * @author  吴超
 * 仅用于测试目的组件，简单地将content参数输出到日志
 */
public class JustTestModule implements ExecutorModule {
    @Override
    public String getId() {
        return "JustTestModule";
    }

    @Override
    public String getName() {
        return "JustTestModule";
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
        return "仅用于测试目的组件，简单地将content参数输出到日志";
    }

    @Override
    public String getParamTemplate() {
        return "{\r" +
                "    \"content\":\"\"\r" +
                "}";
    }

    @Override
    public ExecutionResult exec(ExecutionContext context) throws Exception {
        JSONObject taskParam = context.getParam();
        Log logger = context.getLogger();

        String content = taskParam.getString("content");
        logger.info("content: " + content);
        JSONObject data = new JSONObject();
        data.put("content", content);
        return ExecutionResult.success(data);
    }

}