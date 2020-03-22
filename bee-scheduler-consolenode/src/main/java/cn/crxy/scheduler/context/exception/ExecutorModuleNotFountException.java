package cn.crxy.scheduler.context.exception;

/**
 * @author  吴超
 */
public class ExecutorModuleNotFountException extends RuntimeException {
    private final String executorModuleId;

    public ExecutorModuleNotFountException(String executorModuleId) {
        this.executorModuleId = executorModuleId;
    }

    public String getExecutorModuleId() {
        return executorModuleId;
    }
}
