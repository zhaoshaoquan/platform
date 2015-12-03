package com.stone.commons.jqgrid;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.stone.commons.page.Condition;
import com.stone.commons.page.Operation;
import com.stone.commons.page.Order;
import com.stone.commons.page.OrderType;
import com.stone.commons.page.PageBean;
import com.stone.commons.page.RelateType;

public class JqGridUtil{
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
		pageBean.setRowsPerPage(queryParams.getRows());
		String searchField = queryParams.getSearchField();
		String SearchString = queryParams.getSearchString();
		Operation operation = map.get(queryParams.getSearchOper());
		String sidx = queryParams.getSidx();
		String sord = queryParams.getSord();
		if(StringUtils.isNotEmpty(searchField)) {
			pageBean.addCondition(new Condition(searchField, SearchString, operation));
		}
		if(StringUtils.isNotEmpty(sidx)) {
			pageBean.addOrder(new Order(sidx, OrderType.valueOf(sord.toUpperCase())));
		}
		String content = queryParams.getFilters();
		if(StringUtils.isNotEmpty(content)) {
			Filters filters = objectMapper.readValue(content, Filters.class);
			String groupOp = filters.getGroupOp();
			for(RuleItem ruleItem : filters.getRules()){
				pageBean.addCondition(new Condition(RelateType.valueOf(groupOp.toUpperCase()), ruleItem.getField(),
						ruleItem.getData(), map.get(ruleItem.getOp())));
			}
		}
		return pageBean;
	}

	public static <T> JsonReader<T> getJsonReader(PageBean<T> pageBean){
		JsonReader<T> jsonReader = new JsonReader<T>();
		jsonReader.setPage(pageBean.getCurrentPage());
		jsonReader.setTotal(pageBean.getTotalPages());
		jsonReader.setRecords(pageBean.getTotalRows());
		List<?> list = pageBean.getResult();
		if(list.size() != 0) {
			Object obj = list.get(0);
			if(obj.getClass().isArray()) {
				for(int i = 0; i < list.size(); i++){
					//jsonReader.addRows(new RowItem(i + 1, (Object[]) list.get(i)));
				}
			}else{
				jsonReader.setRows(pageBean.getResult());
			}
		}
		return jsonReader;
	}

	public static FormData parseForm(Object request, Locale locale) throws Exception{
		FormData formData = new FormData();
		Method getParameterNames = request.getClass().getMethod("getParameterNames");
		Method getParameter = request.getClass().getMethod("getParameter", String.class);
		Enumeration<?> names = (Enumeration<?>) getParameterNames.invoke(request);
		while(names.hasMoreElements()){
			String name = (String) names.nextElement();
			String value = (String) getParameter.invoke(request, name);
			if(name.equals("className")) {
				formData.setClassName(value);
			}else if(name.equals("oper")) {
				formData.setOper(OperType.valueOf(value));
			}else{
				formData.put(name, value);
			}
		}
		formData.setLocale(locale);
		return formData;
	}
}
