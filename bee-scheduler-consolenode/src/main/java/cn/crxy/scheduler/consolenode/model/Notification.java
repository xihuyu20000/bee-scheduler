package cn.crxy.scheduler.consolenode.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author  吴超
 * <p>
 * 通知类，用于封装前台所需要的通知信息
 */
public class Notification {
    private NotificationType type;
    private Date publishTime;
    private Map<String, Object> content;
    public Notification() {
    }

    public Notification(NotificationType type, Map<String, Object> content) {
        super();
        this.type = type;
        this.content = content;
        this.publishTime = Calendar.getInstance().getTime();
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Map<String, Object> getContent() {
        return content;
    }

    public void setContent(Map<String, Object> content) {
        this.content = content;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public enum NotificationType {
        JOB_TO_BEEXECUTED, JOB_WAS_EXECUTED, JOB_EXECUTION_VETOED
    }

}
