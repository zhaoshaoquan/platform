package com.stone.extjs.controller;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stone.commons.extjs.ExtColumn;
import com.stone.commons.extjs.ExtGenerateExcel;
import com.stone.commons.extjs.ExtModel;

@Controller
@RequestMapping(value="/extjs")
public class ExportController {
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="exportExcel",method=RequestMethod.POST)
	public ResponseEntity<byte[]> export(String columns,String data,String filename) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		ExtColumn[] extColumns = objectMapper.readValue(columns, ExtColumn[].class);
		List<Map<String,Object>> extData = objectMapper.readValue(data, List.class);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		new ExtGenerateExcel(new ExtModel(extColumns,extData)).buildExcel(baos);
		HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
        if(StringUtils.isEmpty(filename)){
        	filename = System.currentTimeMillis()+"";
        }
        responseHeaders.set("Content-Disposition","attachment;filename=\""+URLEncoder.encode(filename,"UTF-8")+".xls\"");
		return new ResponseEntity<byte[]>(baos.toByteArray(),responseHeaders, HttpStatus.OK);
	}

}
