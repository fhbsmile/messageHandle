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

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tsystems.si.aviation.imf.messageHandle.hu.request.schedule.RequestScheduleMessageOperator;

public class TestRequestScheduleMessageOperator {
	private static final Logger     logger               = LoggerFactory.getLogger(TestRequestScheduleMessageOperator.class);
	
	@Autowired
	private RequestScheduleMessageOperator requestScheduleMessageOperator;
	
	String jsonString =null;
	
	@Test
	public void testProcess(){
		
		requestScheduleMessageOperator.process(jsonString);
	}
}
