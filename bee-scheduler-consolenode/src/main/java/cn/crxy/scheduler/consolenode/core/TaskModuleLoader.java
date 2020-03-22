package cn.crxy.scheduler.consolenode.core;

import java.util.List;

import cn.crxy.scheduler.context.core.ExecutorModule;

public interface TaskModuleLoader {
    List<ExecutorModule> load() throws Exception;
}