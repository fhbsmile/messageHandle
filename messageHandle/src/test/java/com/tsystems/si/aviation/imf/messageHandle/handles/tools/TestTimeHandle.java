/** 
 * Project Name:messageHandle 
 * File Name:TestTimeHandle.java 
 * Package Name:com.tsystems.si.aviation.imf.messageHandle.handles.tools 
 * Date:2016年5月19日下午4:22:32
 * version:v1.0
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */ 


package com.tsystems.si.aviation.imf.messageHandle.handles.tools;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTimeHandle {
	 private static final Logger logger = LoggerFactory.getLogger(TestTimeHandle.class);
	 
	 @Test
	 public void testGetQueryDateTime(){
		 String mk = "+2,5,03,0";
		 
		String res = TimeHandle.getQueryDateTime(mk);
		 logger.info("res:{}",res);
	 }
	 
	 @Test
	 public void testDateAddMinutes(){
             Date date = new Date();
            logger.info("Old Date:{}",date);
           Date n=   TimeHandle.dateAddMinutes(date, -30);
           logger.info("New Date:{}",n);
	 }
	 
}
