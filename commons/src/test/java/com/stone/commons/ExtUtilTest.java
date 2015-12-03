package com.stone.commons;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.stone.commons.extjs.Filters;
import com.stone.commons.extjs.RuleItem;

public class ExtUtilTest {

	@Test
	public void test01() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String filtersJson = "{\"rules\":[{\"field\":\"roleId\",\"data\":\"262147,262146,262148,262149,262151,\",\"op\":\"in\"},{\"field\":\"roleName\",\"data\":\"\",\"op\":\"cn\"}]}";
		Filters filters = objectMapper.readValue(filtersJson, Filters.class);
		String groupOp = filters.getGroupOp();
		System.out.println(groupOp);
		for (RuleItem ruleItem : filters.getRules()) {
			System.out.println(ruleItem);
		}

	}

}
