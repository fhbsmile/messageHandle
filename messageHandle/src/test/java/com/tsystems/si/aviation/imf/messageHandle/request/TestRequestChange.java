/** 
 * Project Name:huMessageHandle 
 * File Name:TestRequestSchedule.java 
 * Package Name:com.tsystems.si.aviation.imf.huMessageHandle.request 
 * Date:2016年5月11日下午2:31:00
 * version:v1.0
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */ 


package com.tsystems.si.aviation.imf.messageHandle.request;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tsystems.si.aviation.imf.messageHandle.hu.request.change.RequestChange;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContextTest_Change.xml")
public class TestRequestChange {
	
	@Autowired
    private RequestChange requestChange;
 
	   @Test
	  public void testRequestSchedule(){
		   requestChange.request();
	   }
}
