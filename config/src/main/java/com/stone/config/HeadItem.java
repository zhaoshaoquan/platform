package com.stone.config;

/**
 * 每个项目head.properties的实体类映射
 */
public class HeadItem {
	/**
	 * 项目名字描述
	 */
	protected String systemName;
	/**
	 * 项目在head文件中要引入的html文件路径,根路径开头:/config.html
	 */
	protected String headHtml;
	/**
	 * 项目在head文件中引入的顺序
	 */
	protected Integer headOrder;
	/**
	 * 项目中不被登陆拦截的地址,多个都好分隔
	 */
	protected String nofilter;
	/**
	 * extjs4虚拟路径映射
	 */
	protected String systemExtPath;

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getHeadHtml() {
		return headHtml;
	}

	public void setHeadHtml(String headHtml) {
		this.headHtml = headHtml;
	}

	public Integer getHeadOrder() {
		return headOrder;
	}

	public void setHeadOrder(Integer headOrder) {
		this.headOrder = headOrder;
	}

	public String getNofilter() {
		return nofilter;
	}

	public void setNofilter(String nofilter) {
		this.nofilter = nofilter;
	}

	public String getSystemExtPath() {
		return systemExtPath;
	}

	public void setSystemExtPath(String systemExtPath) {
		this.systemExtPath = systemExtPath;
	}
}
