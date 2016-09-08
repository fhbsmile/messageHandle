/** 
 * Project Name:messageHandle 
 * File Name:TestHuHandle.java 
 * Package Name:com.tsystems.si.aviation.imf.messageHandle.handles.tools 
 * Date:2016年5月30日上午11:32:31
 * version:v1.0
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */ 


package com.tsystems.si.aviation.imf.messageHandle.handles.tools;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tsystems.si.aviation.imf.messageHandle.handles.hu.HuHandle;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContextTest_HuHandle.xml")
public class TestHuHandle {
	private static final Logger     logger               = LoggerFactory.getLogger(TestHuHandle.class);
	
	@Autowired
	private HuHandle huDynamicHandle;
	
	@Test
	public void testFormatNumber(){
		String flightNumber = "H20107";
		String nm = huDynamicHandle.flightNumberFormat(flightNumber);
		
		logger.info("new Flight number:{}",nm);
		
	}
}
