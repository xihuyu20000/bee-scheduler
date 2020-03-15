package com.bee.scheduler.consolenode.core;

import com.bee.scheduler.context.executor.ClearTaskHistoryTaskModule;
import com.bee.scheduler.context.executor.HttpExcutorModule;
import com.bee.scheduler.context.executor.JustTestModule;
import com.bee.scheduler.context.executor.SpiderTaskModule;
import com.bee.scheduler.context.executor.ShellTaskModule;
import com.bee.scheduler.core.ExecutorModule;

import java.util.ArrayList;
import java.util.List;

public class BuildInTaskModuleLoader implements TaskModuleLoader {
    @Override
    public List<ExecutorModule> load() {
        return new ArrayList<ExecutorModule>() {{
            add(new ShellTaskModule());
            add(new HttpExcutorModule());
            add(new JustTestModule());
            add(new ClearTaskHistoryTaskModule());
            add(new SpiderTaskModule());
        }};
    }
}
