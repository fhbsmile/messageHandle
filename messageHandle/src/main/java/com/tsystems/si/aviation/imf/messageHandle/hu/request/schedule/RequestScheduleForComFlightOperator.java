/** 
 * Project Name:messageHandle 
 * File Name:RequestScheduleForComFlightOperator.java 
 * Package Name:com.tsystems.si.aviation.imf.messageHandle.hu.request.schedule 
 * Date:2016年6月27日下午3:27:13
 * version:v1.0
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */ 


package com.tsystems.si.aviation.imf.messageHandle.hu.request.schedule;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tsystems.si.aviation.imf.messageHandle.db.bean.ComFlight;
import com.tsystems.si.aviation.imf.messageHandle.db.bean.SysParameter;
import com.tsystems.si.aviation.imf.messageHandle.db.service.ComFlightServiceI;
import com.tsystems.si.aviation.imf.messageHandle.db.service.SysParameterServiceI;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.AirportDictionary;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.StcDictionary;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.TelexConstant;

public class RequestScheduleForComFlightOperator {
	private static final Logger     logger               = LoggerFactory.getLogger(RequestScheduleForComFlightOperator.class);
	
	@Autowired
	private ComFlightServiceI comFlightService;	
	@Autowired
	private SysParameterServiceI sysParameterServiceI;
	@Autowired
	private StcDictionary stcDictionary;
	@Autowired
	private AirportDictionary airportDictionary;
	
	public void process(String recordeJson){
		JSONObject jb = JSON.parseObject(recordeJson);

		String depStop = airportDictionary.getNewIATAByIATA(jb.getString("depStn"));
		String arrStop = airportDictionary.getNewIATAByIATA(jb.getString("arrStn"));
		
		String direction = getDirection(depStop,arrStop);
		String flightNumbert = jb.getString("flightNo");
		String flightNumber =flightNumberFormat(flightNumbert);
		String registration =jb.getString("acLongNo");
		Date originalDateTime =jb.getDate("updateTime");
		String orgRouting = jb.getString("airlineCode");		
		String routing =airportDictionary.getNewIATARouting(orgRouting);
		//"stc":"J",
		String stc = jb.getString("stc");
		String serviceType = stcDictionary.getCAACStcByIATA(stc);
		//"status":"ATA",
		String status = jb.getString("status");
		/*
		 * 
		 * 计划起飞    "stdChn":"2016-05-12 06:25:00",
		 * 预计起飞   "etdChn":"2016-05-12 06:25:00",
		 * 实际轮挡   "atdChn":"2016-05-12 06:19:00",
		 * 实际起飞   "tOffChn":"2016-05-12 06:33:00",
		 * 
		 * 实际降落   "tDwnChn":"2016-05-12 08:03:00",
		 * 实际轮挡   "ataChn":"2016-05-12 08:08:00",
		 * 预计降落   "etaChn":"2016-05-12 08:12:00",
		 * 计划降落     "staChn":"2016-05-12 08:20:00",
		 * 
		 * 
		 */
				
		String depStopSchedluedTakeOffDateTimeString = jb.getString("stdChn");		
		Date depStopSchedluedTakeOffDateTime = jb.getDate("stdChn");
		
		String depStopEstimatedTakeOffDateTimeString = jb.getString("etdChn");
		Date depStopEstimatedTakeOffDateTime = jb.getDate("etdChn");
		
		String depStopActualOffBlockDateTimeString = jb.getString("atdChn");
		Date depStopActualOffBlockDateTime = jb.getDate("atdChn");
		
		String depStopActualTakeOffDateTimeString = jb.getString("tOffChn");
		Date depStopActualTakeOffDateTime = jb.getDate("tOffChn");
		
		String arrStopSchedluedLandingDateTimeString =jb.getString("staChn");
		Date arrStopSchedluedLandingDateTime = jb.getDate("staChn");
		
		String arrStopEstimatedLandingDateTimeString = jb.getString("etaChn");
		Date arrStopEstimatedLandingDateTime = jb.getDate("etaChn");
		
		String arrStopActualOnBlockDateTimeString = jb.getString("ataChn");
		Date   arrStopActualOnBlockDateTime = jb.getDate("ataChn");
		
		String arrStopActualLandingDateTimeString = jb.getString("tDwnChn");
		Date arrStopActualLandingDateTime = jb.getDate("tDwnChn");
		logger.info("flightNumber:{}",flightNumber);
		logger.info("stcIATA:{}",stc);
		logger.info("stcCAAC:{}",serviceType);
		logger.info("Status:{}",status);
		logger.info("direction:{}",direction);
		logger.info("routing:{}",routing);
		logger.info("stdChn:{}",depStopSchedluedTakeOffDateTimeString);
		logger.info("etdChn:{}",depStopEstimatedTakeOffDateTimeString);
		logger.info("atdChn:{}",depStopActualOffBlockDateTimeString);
		logger.info("tOffChn:{}",depStopActualTakeOffDateTimeString);
		logger.info("staChn:{}",arrStopSchedluedLandingDateTimeString);
		logger.info("etaChn:{}",arrStopEstimatedLandingDateTimeString);
		logger.info("ataChn:{}",arrStopActualOnBlockDateTimeString);
		logger.info("tDwnChn:{}",arrStopActualLandingDateTimeString);
		
		StringBuffer comments= new StringBuffer();
		comments.append("flightNumber:").append(flightNumber).append(System.lineSeparator());
		comments.append("stcIATA:").append(stc).append("   stcCAAC:").append(serviceType).append(System.lineSeparator());
		comments.append("Status:").append(status).append("   direction:").append(direction).append(System.lineSeparator());
		comments.append("routing:").append(routing).append(System.lineSeparator());
		comments.append("stdChn:").append(depStopSchedluedTakeOffDateTimeString).append("   etdChn:").append(depStopEstimatedTakeOffDateTimeString).append(System.lineSeparator());
		comments.append("atdChn:").append(depStopActualOffBlockDateTimeString).append("   tOffChn:").append(depStopActualTakeOffDateTimeString).append(System.lineSeparator());
		comments.append("staChn:").append(arrStopSchedluedLandingDateTimeString).append("   etaChn:").append(arrStopEstimatedLandingDateTimeString).append(System.lineSeparator());
		comments.append("ataChn:").append(arrStopActualOnBlockDateTimeString).append("   tDwnChn:").append(arrStopActualLandingDateTimeString).append(System.lineSeparator());

		if(direction!=null && !status.equalsIgnoreCase("CNL")){
			ComFlight comFlight = new ComFlight();
			comFlight.setOwner("HU");
			comFlight.setFlightNumber(flightNumber);
			comFlight.setFlightDirection(direction);
			if(direction.equalsIgnoreCase("A")){
				comFlight.setFlightScheduledDate(arrStopSchedluedLandingDateTime);
				comFlight.setFlightScheduledDateTime(arrStopSchedluedLandingDateTime);
				comFlight.setScheduledPreviousAirportDepartureDateTime(depStopSchedluedTakeOffDateTime);
			}else{
				comFlight.setFlightScheduledDate(depStopSchedluedTakeOffDateTime);
				comFlight.setFlightScheduledDateTime(depStopSchedluedTakeOffDateTime);
			}
			comFlight.setRegistration(registration);
			comFlight.setFlightServiceType(serviceType);
			comFlight.setRoute(routing);
			comFlight.setFlightStatus(status);
			comFlight.setCreateDateTime(new Date());
			comFlight.setUpdateDateTime(new Date());
			comFlightService.saveOrUpdate(comFlight);
			
		}else{
			logger.error("direction is null or status is CNL,ignore this message!");
			
		}
		
		
	}
	
	
		public static String getImfDateTimeString(String huDateTimeString){
				
				return huDateTimeString.replaceAll("[\\s]+", "T");
			}
		
		public String getDirection(String depatureStopIATA,String arrivalStopIATA){
			String direction=null;
			if(depatureStopIATA==null || arrivalStopIATA==null){
				logger.error("Has null Stop ignore message");
			}else{
				if(depatureStopIATA.equalsIgnoreCase(TelexConstant.default_baseAirportIATA)){
					direction ="D";
				}else if(arrivalStopIATA.equalsIgnoreCase(TelexConstant.default_baseAirportIATA)){
					direction ="A";
				}else{
					logger.error("No Base Stop。");
				}
			}
			
			return direction;
		}
		
		 public String flightNumberFormat(String flightNumber){
		    	String formatedFlightNumber = flightNumber;
		    	if(flightNumber!=null){
		    		String iata = flightNumber.substring(0, 2);
		    		String number = flightNumber.substring(2);
		    		if(number.startsWith("0")&&number.length()>3){
		    			formatedFlightNumber = iata+number.substring(1);
		    		}
		    	}
		    	formatedFlightNumber = StringUtils.removeEndIgnoreCase(formatedFlightNumber, "N");
		    	return formatedFlightNumber;
		    }
		 
		 public SysParameter getHUScheduleParameter(){
			 String spName="huScheduleRequest";
			 SysParameter sysParameter=sysParameterServiceI.querySysParameterByName(spName);
			 
			 return sysParameter;
		 }
		 
		 
		 public void updateHUScheduleParameter(SysParameter sysParameter){

			 sysParameterServiceI.saveOrUpdate(sysParameter);
			 
		 }
		 
		 public int deleteComFlightByOwner(String owner){
			int n= comFlightService.deleteComFlightByOwner(owner);
			return n;
		 }
		 

}
