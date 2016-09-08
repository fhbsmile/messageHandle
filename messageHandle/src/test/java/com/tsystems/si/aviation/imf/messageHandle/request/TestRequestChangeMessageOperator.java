/** 
 * Project Name:messageHandle 
 * File Name:TestRequestChangeMessageOperator.java 
 * Package Name:com.tsystems.si.aviation.imf.messageHandle.request 
 * Date:2016年5月18日下午3:31:00
 * version:v1.0
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */ 


package com.tsystems.si.aviation.imf.messageHandle.request;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.hnair.opcnet.api.ods.foc.FltLegsChangeDetail;
import com.tsystems.si.aviation.imf.messageHandle.hu.request.change.RequestChangeMessageOperator;

public class TestRequestChangeMessageOperator {

	private static final Logger     logger               = LoggerFactory.getLogger(TestRequestChangeMessageOperator.class);
	
	@Autowired
	private RequestChangeMessageOperator requestChangeMessageOperator;
	
	String jsonString =null;
	
	
	@Test
	public void testProcess(){
		FltLegsChangeDetail flcd = new FltLegsChangeDetail();
		String changeJsonString = JSON.toJSONString(flcd);
		requestChangeMessageOperator.process(changeJsonString);
	}
	
	
}
