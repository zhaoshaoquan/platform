package com.stone.commons.jqgrid;

import java.util.List;

public class Filters {
	private String groupOp;
	private List<RuleItem> rules;
	public String getGroupOp() {
		return groupOp;
	}
	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}
	public List<RuleItem> getRules() {
		return rules;
	}
	public void setRules(List<RuleItem> rules) {
		this.rules = rules;
	}
	
	
	
}
