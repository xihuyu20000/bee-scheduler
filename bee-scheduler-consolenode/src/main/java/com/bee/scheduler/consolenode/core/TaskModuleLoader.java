package com.bee.scheduler.consolenode.core;

import java.util.List;

import com.bee.scheduler.context.core.ExecutorModule;

public interface TaskModuleLoader {
    List<ExecutorModule> load() throws Exception;
}