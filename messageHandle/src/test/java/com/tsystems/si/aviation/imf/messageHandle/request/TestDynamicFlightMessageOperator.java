/** 
 * Project Name:messageHandle 
 * File Name:TestDynamicFlightMessageOprator.java 
 * Package Name:com.tsystems.si.aviation.imf.messageHandle.request 
 * Date:2016年5月18日下午4:36:49
 * version:v1.0
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */ 


package com.tsystems.si.aviation.imf.messageHandle.request;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tsystems.si.aviation.imf.messageHandle.hu.activeMQ.DynamicFlightMessageOperator;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContextTest_Dynamic_operator.xml")
public class TestDynamicFlightMessageOperator {
	private static final Logger     logger               = LoggerFactory.getLogger(TestDynamicFlightMessageOperator.class);
	
	@Autowired
	private DynamicFlightMessageOperator dynamicFlightMessageOperator;
	
	String jsonString =null;
	
	@Before
	public void setUp() throws Exception {
		
		//File text = new File("file/dynamicExample.json");
		File text = new File("file/dy3.json");
		jsonString = FileUtils.readFileToString(text, "utf-8");
	}	
	
	@Test
	public void testProcess(){
		
		dynamicFlightMessageOperator.process(jsonString);
	}
}
