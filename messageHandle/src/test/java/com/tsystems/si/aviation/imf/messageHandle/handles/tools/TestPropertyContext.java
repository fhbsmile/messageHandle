/** 
 * Project Name:messageHandle 
 * File Name:TestPropertyContext.java 
 * Package Name:com.tsystems.si.aviation.imf.messageHandle.handles.tools 
 * Date:2016年5月23日下午3:02:38
 * version:v1.0
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */ 


package com.tsystems.si.aviation.imf.messageHandle.handles.tools;

import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:propertyContext.xml")
public class TestPropertyContext {
	 private static final Logger logger = LoggerFactory.getLogger(TestPropertyContext.class);
	 
	 @Autowired
	 private Properties huProperties;
	 
	 
	 @Test
	 public void testP(){
		 logger.info("timeZone:{}",huProperties.get("timeZone"));

		 logger.info("localTimeZone:{}",huProperties.get("localTimeZone"));
		 int i =  (int) huProperties.get("timeZoneOffset");
		 logger.info("timeZoneOffset:{}",huProperties.get("timeZoneOffset"));
	 }
}
