package com.bee.scheduler.consolenode.util;

/**
 * @author  吴超
 */
public class Assert {
    public static void check(boolean expression, RuntimeException exception) {
        if (!expression) {
            throw exception;
        }
    }
}
