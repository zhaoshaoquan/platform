package com.stone.commons;

import java.util.Date;

import org.junit.Test;

public class DateTest {

	@Test
	public void test01(){
		String time = DateUtil.dateToStr(new Date(), DateUtil.HH_mm_ss);
		System.out.println(time);
		int day = DateUtil.getLastDayOfMonth(2013, 6);
		System.out.println(day);
	}
}
