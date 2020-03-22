package cn.crxy.scheduler.consolenode.model;

/**
 * @author  吴超
 */
public class SpiderConfig {
	private Long   id = null;
    private String name = "";
    private String group = "";
    private String cmd = "";
    private String params = "";
    private String description = "";

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }


	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	@Override
	public String toString() {
		return "SpiderConfig [id=" + id + ", name=" + name + ", group=" + group + ", cmd=" + cmd + ", params=" + params
				+ ", description=" + description + "]";
	}

}
