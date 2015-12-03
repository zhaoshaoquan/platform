package com.stone.commons.extjs;

import java.util.List;

public class GroupItem {
	protected String groupOp;
	protected List<RuleItem> rules;

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
