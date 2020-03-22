package cn.crxy.scheduler.context.executor;

import com.alibaba.fastjson.JSONObject;

import cn.crxy.scheduler.context.core.ExecutionContext;
import cn.crxy.scheduler.context.core.ExecutionResult;
import cn.crxy.scheduler.context.core.ExecutorModule;
import cn.crxy.scheduler.context.util.ProcessTool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author  吴超 该组件提供运行服务端脚本的功能
 */
public class ShellTaskModule implements ExecutorModule {
    @Override
    public String getId() {
        return "ShellTaskModule";
    }

    @Override
    public String getName() {
        return "ShellTaskModule";
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
        return "该组件提供运行服务端脚本的功能";
    }

    @Override
    public String getParamTemplate() {
        return "{\r" +
                "    \"shell\":\"\"\r" +
                "}";
    }

    @Override
    public ExecutionResult exec(ExecutionContext context) throws Exception {
        JSONObject taskParam = context.getParam();

        String shell = taskParam.getString("shell");

//        Runtime runtime = Runtime.getRuntime();
//        Process process = runtime.exec(shell);
//        InputStream stderr = process.getInputStream();
//        InputStreamReader isr = new InputStreamReader(stderr);
//        BufferedReader br = new BufferedReader(isr);
//        String line;
//        StringBuilder back = new StringBuilder();
//        while ((line = br.readLine()) != null) {
//            back.append(line).append("\r");
//        }
        String back = new ProcessTool().run(shell);
        JSONObject data = new JSONObject();
        data.put("echo", back.toString());
        return ExecutionResult.success(data);
    }
}
