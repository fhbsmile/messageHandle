/** 
 * Project Name:messageHandle 
 * File Name:TestRequestScheduleMessageOperator.java 
 * Package Name:com.tsystems.si.aviation.imf.messageHandle.request 
 * Date:2016年5月18日下午4:32:49
 * version:v1.0
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */ 


package com.tsystems.si.aviation.imf.messageHandle.request;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tsystems.si.aviation.imf.messageHandle.hu.request.schedule.RequestScheduleForComFlightOperator;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContextTest_Schedule_ComFlightOperator.xml")
public class TestRequestScheduleForComFlightOperator {
	private static final Logger     logger               = LoggerFactory.getLogger(TestRequestScheduleForComFlightOperator.class);
	
	@Autowired
	private RequestScheduleForComFlightOperator requestScheduleForComFlightOperator;
	
	String jsonString =null;
	@Before
	public void setUp() throws Exception {
		
		//File text = new File("file/dynamicExample.json");
		//File text = new File("file/scheduleExample.json");
		File text = new File("file/scheduleExample2.json");
		jsonString = FileUtils.readFileToString(text, "utf-8");
	}	
	@Test
	public void testProcess(){
		
		requestScheduleForComFlightOperator.process(jsonString);
	}
}
