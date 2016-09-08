/** 
 * Project Name:messageHandle 
 * File Name:TestAirportDictionary.java 
 * Package Name:com.tsystems.si.aviation.imf.messageHandle.handles.tools 
 * Date:2016年7月12日下午3:47:00
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


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:AirportDictionary.xml")
public class TestAirportDictionary {
	private static final Logger     logger               = LoggerFactory.getLogger(TestAirportDictionary.class);
	@Autowired
      public AirportDictionary airportDictionary;
	@Test
	public void testGetNewIATAByIATA(){
		String orgRouting="HAK-HHA-ZGC-SIA";
		 String newRouting =airportDictionary.getNewIATARouting(orgRouting);

       logger.info("newRouting:{}",newRouting);
	}
}
