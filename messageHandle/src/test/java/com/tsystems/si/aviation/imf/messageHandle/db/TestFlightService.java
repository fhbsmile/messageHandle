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
import com.tsystems.si.aviation.imf.messageHandle.db.service.FlightServiceI;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext_DB.xml")
public class TestFlightService {
	private static final Logger     logger               = LoggerFactory.getLogger(TestFlightService.class);
	
	@Autowired
	private FlightServiceI flightService;
	
	
	
	@Test
	public void TestGetFlight(){
		Map<String,Object> params = new HashMap<String,Object>();
		Date beforeDateTime = new Date();
		Date afterDateTime = new Date();
		afterDateTime.setMonth(7);
		params.put("flightNumber", "MU999");
		params.put("direction", "A");
		params.put("beforeDateTime", beforeDateTime);
		params.put("afterDateTime", afterDateTime);
		List<Flight> flightlist = flightService.getFlightByFlightNumberWithDateTimePeriod(params);
		if(flightlist.size()>0){
			for(Flight f :flightlist){
				logger.info("Get Flight: {}",f);
			}
			
		}else{
			logger.info("No Flight Found!");
		}
	}
}
