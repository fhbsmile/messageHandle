/** 
 * Project Name:huMessageHandle 
 * File Name:TestFlightService.java 
 * Package Name:com.tsystems.si.aviation.imf.huMessageHandle.db 
 * Date:2016年5月12日下午2:52:06
 * version:v1.0
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */ 


package com.tsystems.si.aviation.imf.messageHandle.db;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tsystems.si.aviation.imf.messageHandle.db.bean.Flight;
import com.tsystems.si.aviation.imf.messageHandle.db.service.ComFlightServiceI;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext_DB.xml")
public class TestComFlightService {
	private static final Logger     logger               = LoggerFactory.getLogger(TestComFlightService.class);
	
	@Autowired
	private ComFlightServiceI comFlightService;
	
	
	
	@Test
	public void TestDeleteFlightByOwner(){
		String owner="HU";
		int n =comFlightService.deleteComFlightByOwner(owner);
		
		logger.info("Deleted number:{}",n);
	}
}
