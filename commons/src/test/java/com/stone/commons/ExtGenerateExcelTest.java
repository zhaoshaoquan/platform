package com.stone.commons;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Test;

import com.stone.commons.extjs.ExtColumn;
import com.stone.commons.extjs.ExtModel;

public class ExtGenerateExcelTest {
	
	@Test
	public void testBuildExcel()throws Exception {
		ExtModel em = new ExtModel();
		ExtColumn [] ecs0 = new ExtColumn[3];
		//--------------------------------------------------------
		ExtColumn ec0 = new ExtColumn();
		ec0.setText("基本信息");
		//--------------------------------------------------------
		ExtColumn [] ecs1 = new ExtColumn[2];
		ExtColumn ec0_0 = new ExtColumn();
		ec0_0.setText("奖励原因");
		ec0_0.setDataIndex("jlyyjs");
		ecs1[0] = ec0_0;
		//--------------------------------------------------------
		ExtColumn ec0_1 = new ExtColumn();
		ec0_1.setText("创建日期");
		ec0_1.setDataIndex("cjrq");
		ecs1[1] = ec0_1;
		//--------------------------------------------------------
		ec0.setColumns(ecs1);
		ecs0[0] = ec0;
		//========================================================
		ExtColumn ec1 = new ExtColumn();
		ec1.setText("其它信息");
		//--------------------------------------------------------
		ExtColumn [] ecs2 = new ExtColumn[2];
		ExtColumn ec1_0 = new ExtColumn();
		ec1_0.setText("审批状态");
		ec1_0.setDataIndex("spzt");
		ecs2[0] = ec1_0;
		//--------------------------------------------------------
		ExtColumn ec1_1 = new ExtColumn();
		ec1_1.setText("审批备注");
		
		ExtColumn [] ecs2_0 = new ExtColumn[2];
		ExtColumn ec3_0 = new ExtColumn();
		ec3_0.setText("审批备注1");
		ec3_0.setDataIndex("spbz01");
		ecs2_0[0] = ec3_0;
		//--------------------------------------------------------
		ExtColumn ec3_1 = new ExtColumn();
		ec3_1.setText("审批备注2");
		ec3_1.setDataIndex("spbz02");
		ecs2_0[1] = ec3_1;
		ec1_1.setColumns(ecs2_0);
		ecs2[1] = ec1_1;
		
		ec1.setColumns(ecs2);
		ecs0[1] = ec1;
		//========================================================
		ExtColumn ec2 = new ExtColumn();
		ec2.setText("其它备注");
		ec2.setDataIndex("qtbz");
		ecs0[2] = ec2;
		//========================================================
		em.setColumns(ecs0);
		em.setData(new ArrayList<Map<String,Object>>());
//		ExtGenerateExcel ege = new ExtGenerateExcel(em);
//		FileOutputStream fos = new FileOutputStream(new File("F:\\zhao.xls"));
//		ege.buildExcel(fos);
//		fos.flush();
//		fos.close();
	}
}
