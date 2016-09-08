/** 
 * Project Name:messageHandle 
 * File Name:JsonParseTest.java 
 * Package Name:com.tsystems.si.aviation.imf.messageHandle.request 
 * Date:2016年5月13日下午9:46:08
 * version:v1.0
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */ 


/** 
 * Project Name:messageHandle 
 * File Name:JsonParseTest.java 
 * Package Name:com.tsystems.si.aviation.imf.messageHandle.request 
 * Date:2016年5月13日下午9:46:08 
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */  
package com.tsystems.si.aviation.imf.messageHandle.request;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hnair.opcnet.api.ods.foc.FltLegsChangeDetail;
import com.hnair.opcnet.api.ods.foc.FltLegsChangeTitle;
import com.tsystems.si.aviation.imf.messageHandle.db.bean.Flight;

/**   
 * @ClassName:  JsonParseTest   
 * @Description:TODO
 * @author: Bolo.Fang@t-systems.com  
 * @date:   2016年5月13日 下午9:46:08   
 *      
 */
public class JsonParseTest {
	private static final Logger     logger               = LoggerFactory.getLogger(JsonParseTest.class);
	
	String jsonString= null;
			
	@Before
	public void setUp() throws Exception {
		
		File text = new File("file/jsonString.txt");
		jsonString = FileUtils.readFileToString(text, "utf-8");
	}	
	
	@Test
	public void testParseJson(){
		JSONObject jb = JSON.parseObject(jsonString);
		
		String datopChn = jb.getString("datopChn");
		logger.info("datopChn:{}",datopChn);
		String id = jb.getString("id");
		logger.info("id:{}",id);
		String datop =jb.getString("datop");
		logger.info("datop:{}",datop);
		String depStnCn =jb.getString("depStnCn");
		logger.info("depStnCn:{}",depStnCn);
		String std =jb.getString("std");
		logger.info("std:{}",std);
		Date stdDate =jb.getDate("std");
		logger.info("stdDate:{}",stdDate);
		String delay3 =jb.getString("delay3");
		logger.info("delay3:{}",delay3);
		String vipNum =jb.getString("vipNum");
		logger.info("vipNum:{}",vipNum);
		
		logger.info(JSON.toJSONString(jb,true));
		
	}
	
	@Test
	public void testchangeJson() throws IOException{
		File changeText = new File("file/changeExample.json");
		String changeJsonString = FileUtils.readFileToString(changeText, "utf-8");
		FltLegsChangeDetail fltLegsChangeDetail	=JSON.parseObject(changeJsonString, FltLegsChangeDetail.class);
		
		String changeJsonString2 = JSON.toJSONString(fltLegsChangeDetail, true);
		logger.info("changeJsonString2:\n{}",changeJsonString2);
		
		
		JSONObject jb = JSON.parseObject(changeJsonString2);
		String jbflString = jb.getString("fltLegsChangeTitle");
		logger.info("jbflString:{}",jbflString);
		JSONObject jbfl  = jb.getJSONObject("fltLegsChangeTitle");
		String subtimeString = jbfl.getString("subtime");
		logger.info("subtimeString:{}",subtimeString);
		Date subtimeDate = jbfl.getDate("subtime");
		logger.info("subtimeDate:{}",subtimeDate);
		FltLegsChangeTitle fltLegsChangeTitle=jb.getObject("fltLegsChangeTitle", FltLegsChangeTitle.class);
		String datopChn = jb.getString("datopChn");
		logger.info("datopChn:{}",datopChn);
		String id = jb.getString("id");
		logger.info("id:{}",id);
		String datop =jb.getString("datop");
		logger.info("datop:{}",datop);
		String depStnCn =jb.getString("depStnCn");
		logger.info("depStnCn:{}",depStnCn);
		String std =jb.getString("std");
		logger.info("std:{}",std);
		Date stdDate =jb.getDate("std");
		logger.info("stdDate:{}",stdDate);
		String delay3 =jb.getString("delay3");
		logger.info("delay3:{}",delay3);
		String vipNum =jb.getString("vipNum");
		logger.info("vipNum:{}",vipNum);
		
		logger.info(JSON.toJSONString(jb,true));
		
	}
	@Test
	public void testJsonDate(){
		
		Flight flight = new Flight(); 
		Date d = new Date();
		flight.setFlightScheduledDate(d);
		
		String s = JSON.toJSONString(flight, SerializerFeature.WriteDateUseDateFormat);
		
		logger.info("Flight Json:{}",s);
		
		JSONObject jo =JSON.parseObject(s);
		Date dd =jo.getDate("flightScheduledDate");
		String ddString =jo.getString("flightScheduledDate");
		logger.info("flightScheduledDate Json:{}",dd);
		logger.info("ddString Json:{}",ddString);
	}
	
	@Test
	public void testJsonCalendar(){
		

		Date now = new Date();		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		String s = JSON.toJSONString(calendar, SerializerFeature.WriteDateUseDateFormat);
		
		logger.info("calendar Json:{}",s);

	}
	@Test
	public void testMatcher(){
		 Pattern pattern = Pattern.compile("[-?|+?]{0,1}\\d+,\\d+,\\d+,\\d+");
		  String charSequence1 = "+123,34345,004,00";
		  String charSequence = "2,5,0,0";
		  Matcher matcher = pattern.matcher(charSequence);
		  
		  logger.info("Matchs:{}",matcher.matches());
	}
	
}
