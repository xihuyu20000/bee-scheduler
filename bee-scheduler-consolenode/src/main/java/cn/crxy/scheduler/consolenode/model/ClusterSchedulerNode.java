package cn.crxy.scheduler.consolenode.model;

import java.util.Date;

/**
 * Created by  吴超  
 */
public class ClusterSchedulerNode extends SchedulerNode {
    private Date lastCheckinTime;
    private Long checkinInterval;

    public Date getLastCheckinTime() {
        return lastCheckinTime;
    }

    public void setLastCheckinTime(Date lastCheckinTime) {
        this.lastCheckinTime = lastCheckinTime;
    }

    public Long getCheckinInterval() {
        return checkinInterval;
    }

    public void setCheckinInterval(Long checkinInterval) {
        this.checkinInterval = checkinInterval;
    }
}
