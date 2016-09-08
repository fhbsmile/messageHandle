/** 
 * Project Name:huMessageHandle 
 * File Name:MQSimpleListener.java 
 * Package Name:com.tsystems.si.aviation.imf.huMessageHandle.mq.activeMQ 
 * Date:2016年5月10日下午9:34:25
 * version:v1.0
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */ 


package com.tsystems.si.aviation.imf.messageHandle.hu.activeMQ;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.TelexConstant;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.TimeHandle;



public class MQSimpleListener {
	private static final Logger     logger               = LoggerFactory.getLogger(MQSimpleListener.class);
	private String listenerName;
	private DynamicFlightMessageOperator dynamicFlightMessageOperator;
	private int bufferTime=0;
	
	public void handleMessage(String message) {

        logger.info("Lisener:{} received message>>>>>>>>>>>>>>>>>>，Msg:{}",new Object[]{listenerName, message});
        JSONObject jb = JSON.parseObject(message);
		String depStop = jb.getString("depStn");
		String arrStop = jb.getString("arrStn");
		String direction = getDrection(depStop,arrStop);
		if(direction==null){
			logger.info("Ignored,No BaseStop, {}-{}",new Object[]{depStop,arrStop});
		}else{
			Date depStopActualTakeOffDateTime = jb.getDate("tOffChn");
			Date arrStopActualLandingDateTime = jb.getDate("tDwnChn");
			boolean needProcess=true;
			Date leadDateTime=null;
			if(bufferTime>0){
				leadDateTime = TimeHandle.dateAddMinutes(new Date(), bufferTime);
				if(direction.equalsIgnoreCase("A")){
					
					if(arrStopActualLandingDateTime.after(leadDateTime)){
						needProcess = false;
					}
				}else{
					if(depStopActualTakeOffDateTime.after(leadDateTime)){
						needProcess = false;
					}
				}
				
			}
			
			if(needProcess){
				dynamicFlightMessageOperator.process(message);
					logger.info("Message handling finished");
				
			}else{
				logger.info("Ignore,Schedlue time exceeds the time gate. Time gate:{}",leadDateTime);
			}
			
		}
		
		
        

	}
	public void handleMessage(byte[] msg) {
           String message=null;
		try {
			message = new String(msg, "UTF-8");
			 logger.info("Lisener:{} received message>>>>>>>>>>>>>>>>>>，Msg:{}",new Object[]{listenerName, message});
			 JSONObject jb = JSON.parseObject(message);
				String depStop = jb.getString("depStn");
				String arrStop = jb.getString("arrStn");
				String direction = getDrection(depStop,arrStop);
				if(direction==null){
					logger.info("Ignored,No BaseStop, {}-{}",new Object[]{depStop,arrStop});
				}else{
					Date depStopActualTakeOffDateTime = jb.getDate("tOffChn");
					Date arrStopActualLandingDateTime = jb.getDate("tDwnChn");
					boolean needProcess=true;
					Date leadDateTime=null;
					if(bufferTime>0){
						leadDateTime = TimeHandle.dateAddMinutes(new Date(), bufferTime);
						if(direction.equalsIgnoreCase("A")){
							
							if(arrStopActualLandingDateTime.after(leadDateTime)){
								needProcess = false;
							}
						}else{
							if(depStopActualTakeOffDateTime.after(leadDateTime)){
								needProcess = false;
							}
						}
					}					
					if(needProcess){
						dynamicFlightMessageOperator.process(message);
							logger.info("Message handling finished");
						
					}else{
						logger.info("Ignore,Schedlue time exceeds the time gate. Time gate:{}",leadDateTime);
					}
					
				}
				
				
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
         }
	
	
	
	public String getDrection(String depatureStopIATA,String arrivalStopIATA){
		String direction=null;
		if(depatureStopIATA==null || arrivalStopIATA==null){
			logger.error("Has null Stop ignore message");
		}else{
			if(depatureStopIATA.equalsIgnoreCase(TelexConstant.default_baseAirportIATA)){
				direction ="D";
			}else if(arrivalStopIATA.equalsIgnoreCase(TelexConstant.default_baseAirportIATA)){
				direction ="A";
			}else{
				logger.error("No Base Stop.");
			}
		}
		
		return direction;
	}
	public String getListenerName() {
		return listenerName;
	}
	public void setListenerName(String listenerName) {
		this.listenerName = listenerName;
	}
	public DynamicFlightMessageOperator getDynamicFlightMessageOperator() {
		return dynamicFlightMessageOperator;
	}
	public void setDynamicFlightMessageOperator(DynamicFlightMessageOperator dynamicFlightMessageOperator) {
		this.dynamicFlightMessageOperator = dynamicFlightMessageOperator;
	}
	public int getBufferTime() {
		return bufferTime;
	}
	public void setBufferTime(int bufferTime) {
		this.bufferTime = bufferTime;
	}

	
	}

