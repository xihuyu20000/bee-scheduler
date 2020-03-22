package cn.crxy.scheduler.context.model;

/**
 * @author  吴超
 */
public class QuickTaskConfig extends TaskConfig {
    private Boolean enableStartDelay;
    private Integer startDelay;

    public Boolean getEnableStartDelay() {
        return enableStartDelay;
    }

    public void setEnableStartDelay(Boolean enableStartDelay) {
        this.enableStartDelay = enableStartDelay;
    }

    public Integer getStartDelay() {
        return startDelay;
    }

    public void setStartDelay(Integer startDelay) {
        this.startDelay = startDelay;
    }

	@Override
	public String toString() {
		return "QuickTaskConfig [enableStartDelay=" + enableStartDelay + ", startDelay=" + startDelay + ", getName()="
				+ getName() + ", getGroup()=" + getGroup() + ", getScheduleType()=" + getScheduleType()
				+ ", getScheduleTypeSimpleOptions()=" + getScheduleTypeSimpleOptions()
				+ ", getScheduleTypeCalendarIntervalOptions()=" + getScheduleTypeCalendarIntervalOptions()
				+ ", getScheduleTypeDailyTimeIntervalOptions()=" + getScheduleTypeDailyTimeIntervalOptions()
				+ ", getScheduleTypeCronOptions()=" + getScheduleTypeCronOptions() + ", getStartAtType()="
				+ getStartAtType() + ", getStartAt()=" + getStartAt() + ", getEndAtType()=" + getEndAtType()
				+ ", getEndAt()=" + getEndAt() + ", getTaskModule()=" + getTaskModule() + ", getParams()=" + getParams()
				+ ", getDescription()=" + getDescription() + ", getLinkageRule()=" + getLinkageRule() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
    
}
