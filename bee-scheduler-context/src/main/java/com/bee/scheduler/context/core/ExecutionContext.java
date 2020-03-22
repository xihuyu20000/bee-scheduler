package com.bee.scheduler.context.core;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;

public interface ExecutionContext {
    JSONObject getParam();

    Log getLogger();
}
