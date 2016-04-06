package com.stone.commons.extjs;

import java.util.List;

public class Filters {
	protected String groupOp;
	protected List<RuleItem> rules;
	protected List<GroupItem> groups;
	
	public Filters(){
		
	}
	
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

	public List<GroupItem> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupItem> groups) {
		this.groups = groups;
	}
	
}
