package com.stone.commons.extjs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.stone.commons.page.Condition;
import com.stone.commons.page.Operation;
import com.stone.commons.page.Order;
import com.stone.commons.page.OrderType;
import com.stone.commons.page.PageBean;
import com.stone.commons.page.RelateType;

public class ExtUtil{
	public static ObjectMapper objectMapper = new ObjectMapper();

	public static Map<String, Operation> map = new HashMap<String, Operation>();

	static{
		map.put("bw", Operation.BW);
		map.put("eq", Operation.EQ);
		map.put("ne", Operation.NE);
		map.put("bn", Operation.BN);
		map.put("ew", Operation.EW);
		map.put("en", Operation.EN);
		map.put("cn", Operation.CN);
		map.put("nc", Operation.NC);
		map.put("nu", Operation.NU);
		map.put("nn", Operation.NN);
		map.put("in", Operation.IN);
		map.put("ni", Operation.NI);
		map.put("le", Operation.LE);
		map.put("lt", Operation.LT);
		map.put("ge", Operation.GE);
		map.put("gt", Operation.GT);
		map.put("bt", Operation.BETWEEN);
	}

	public static <T> PageBean<T> getPageBean(QueryParams queryParams) throws Exception{
		PageBean<T> pageBean = new PageBean<T>();
		pageBean.setCurrentPage(queryParams.getPage());
		pageBean.setRowsPerPage(queryParams.getLimit());
		String sort = queryParams.getSort();
		String dir = queryParams.getDir();
		if(StringUtils.isNotEmpty(dir)) {
			pageBean.addOrder(new Order(sort, OrderType.valueOf(dir.toUpperCase())));
		}else if(StringUtils.isNotEmpty(sort)) {
			Sort[] sorts = objectMapper.readValue(sort, Sort[].class);
			for(Sort item : sorts){
				pageBean.addOrder(new Order(item.getProperty(), OrderType.valueOf(item.getDirection().toUpperCase())));
			}
		}
		String filtersJson = queryParams.getFilters();
		if(StringUtils.isNotEmpty(filtersJson)) {
			Filters filters = objectMapper.readValue(filtersJson, Filters.class);
			if(filters == null)
				return pageBean;
			String rootGroupOp = filters.getGroupOp();
			RelateType rootRelateType = null;
			if(StringUtils.isNotEmpty(rootGroupOp)) {
				rootRelateType = RelateType.valueOf(rootGroupOp.toUpperCase());
			}
			List<GroupItem> groups = filters.getGroups();

			if(groups != null && groups.size() > 0) {
				int prefixBracketsCount = groups.size();
				String groupPrefixBrackets = "";
				for(int i = 0; i < prefixBracketsCount; i++){
					groupPrefixBrackets += "(";
				}
				for(int i = 0; i < groups.size(); i++){
					GroupItem groupItem = groups.get(i);
					String suGroupOp = groupItem.getGroupOp().toUpperCase();
					List<RuleItem> subRuleItems = groupItem.getRules();

					for(int j = 0; j < subRuleItems.size(); j++){
						RuleItem subRuleItem = subRuleItems.get(j);
						Condition condition = new Condition(RelateType.valueOf(suGroupOp), subRuleItem.getField(),
								subRuleItem.getData(), map.get(subRuleItem.getOp().toLowerCase()));
						if((groups.size() == 1 || i == groups.size() - 1) && j == 0) {
							condition.setRelateType(null);
						}

						if(i == 0 && j == 0) {
							condition.setGroupPrefixBrackets(groupPrefixBrackets);
						}
						if(j == subRuleItems.size() - 1) {
							condition.setSuffixBrackets(true);
						}
						pageBean.addCondition(condition);
					}
				}
				List<RuleItem> rules = filters.getRules();
				for(int j = 0; j < rules.size(); j++){
					RuleItem ruleItem = rules.get(j);
					Condition condition = new Condition(rootRelateType, ruleItem.getField(), ruleItem.getData(),
							map.get(ruleItem.getOp().toLowerCase()));
					if(j == rules.size() - 1) {
						condition.setSuffixBrackets(true);
					}
					if(groups.size() == 0 && j == 0) {
						condition.setRelateType(null);
					}
					pageBean.addCondition(condition);
				}
			}else{
				List<RuleItem> rules = filters.getRules();
				for(int i = 0; i < rules.size(); i++){
					RuleItem ruleItem = rules.get(i);
					Condition condition = new Condition(rootRelateType, ruleItem.getField(), ruleItem.getData(),
							map.get(ruleItem.getOp().toLowerCase()));
					if(i == 0) {
						condition.setPrefixBrackets(true);
					}
					if(i == rules.size() - 1) {
						condition.setSuffixBrackets(true);
					}
					pageBean.addCondition(condition);
				}
			}

		}
		return pageBean;
	}

	public  static <T> JsonReader<T> getJsonReader(PageBean<T> pageBean){
		JsonReader<T> jsonReader = new JsonReader<T>();
		jsonReader.setTotal(pageBean.getTotalPages());
		jsonReader.setRows(pageBean.getResult());
		jsonReader.setRecords(pageBean.getTotalRows());
		return jsonReader;
	}

}
