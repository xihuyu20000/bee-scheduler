package cn.crxy.scheduler.consolenode.core;

import java.util.ArrayList;
import java.util.List;

import cn.crxy.scheduler.context.core.ExecutorModule;
import cn.crxy.scheduler.context.executor.ClearTaskHistoryTaskModule;
import cn.crxy.scheduler.context.executor.HttpExcutorModule;
import cn.crxy.scheduler.context.executor.JustTestModule;
import cn.crxy.scheduler.context.executor.ShellTaskModule;
import cn.crxy.scheduler.context.executor.SpiderTaskModule;

public class BuildInTaskModuleLoader implements TaskModuleLoader {
    @Override
    public List<ExecutorModule> load() {
        return new ArrayList<ExecutorModule>() {{
//            add(new ShellTaskModule());
//            add(new HttpExcutorModule());
//            add(new JustTestModule());
//            add(new ClearTaskHistoryTaskModule());
            add(new SpiderTaskModule());
        }};
    }
}
