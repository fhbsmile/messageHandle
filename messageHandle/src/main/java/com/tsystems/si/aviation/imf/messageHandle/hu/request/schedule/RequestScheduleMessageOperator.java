/** 
 * Project Name:messageHandle 
 * File Name:RequestScheduleMessageOperator.java 
 * Package Name:com.tsystems.si.aviation.imf.messageHandle.hu.request.schedule 
 * Date:2016年5月13日下午9:02:13
 * version:v1.0
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */ 


/** 
 * Project Name:messageHandle 
 * File Name:RequestScheduleMessageOperator.java 
 * Package Name:com.tsystems.si.aviation.imf.messageHandle.hu.request.schedule 
 * Date:2016年5月13日下午9:02:13 
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */  
package com.tsystems.si.aviation.imf.messageHandle.hu.request.schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tsystems.si.aviation.imf.messageHandle.db.bean.Flight;
import com.tsystems.si.aviation.imf.messageHandle.db.bean.ImfMessage;
import com.tsystems.si.aviation.imf.messageHandle.db.bean.OrgMessage;
import com.tsystems.si.aviation.imf.messageHandle.db.service.FlightServiceI;
import com.tsystems.si.aviation.imf.messageHandle.db.service.OrgMessageServiceI;
import com.tsystems.si.aviation.imf.messageHandle.handles.Handle;
import com.tsystems.si.aviation.imf.messageHandle.handles.hu.HuHandle;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.AirportDictionary;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.FlightXmlBean;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.SequenceGenerator;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.StcDictionary;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.TelexConstant;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.TimeHandle;

/**   
 * @ClassName:  RequestScheduleMessageOperator   
 * @Description:TODO
 * @author: Bolo.Fang@t-systems.com  
 * @date:   2016年5月13日 下午9:02:13   
 *      
 */
public class RequestScheduleMessageOperator {
	private static final Logger     logger               = LoggerFactory.getLogger(RequestScheduleMessageOperator.class);
	@Autowired
	private FlightServiceI flightService;
	
	@Autowired
	private OrgMessageServiceI orgMessageService;
	
	@Autowired
	private HuHandle huScheduleHandle;
	
	@Autowired
	private StcDictionary stcDictionary;
	@Autowired
	private AirportDictionary airportDictionary;
	
	public void process(String recordeJson){
		OrgMessage orgMessage = new OrgMessage();
		
		Set<ImfMessage>  imfMessageSet = new HashSet<ImfMessage>();
		ImfMessage xmlMessage =null;
		JSONObject jb = JSON.parseObject(recordeJson);

		String depStop = airportDictionary.getNewIATAByIATA(jb.getString("depStn"));
		String arrStop = airportDictionary.getNewIATAByIATA(jb.getString("arrStn"));
		
		String direction = getDirection(depStop,arrStop);
		String flightNumbert = jb.getString("flightNo");
		String flightNumber = huScheduleHandle.flightNumberFormat(flightNumbert);
		String registration =jb.getString("acLongNo");
		Date originalDateTime =jb.getDate("updateTime");
		String orgRouting = jb.getString("airlineCode");
		
		String routing = airportDictionary.getNewIATARouting(orgRouting);
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

		orgMessage.setGenerateDateTime(originalDateTime);
		String recordContent =JSON.toJSONString(jb,true);
		orgMessage.setContent(recordContent);
		orgMessage.setOwner("HU");
		orgMessage.setType("HURS");

		
		//String direction = null;
		//arrival flight direction A
		if(!depStop.equalsIgnoreCase(huScheduleHandle.getBaseAirportIATA()) && arrStop.equalsIgnoreCase(huScheduleHandle.getBaseAirportIATA())){
/*			Map<String,Object> params = new HashMap<String,Object>();
			params.put("flightNumber", flightNumber);
			params.put("direction", "A");
			params.put("flightScheduledDate", arrStopSchedluedLandingDateTime);
			List<Flight> flights=flightService.getFlightByFlightNumberWithDate(params);*/
			List<Flight> flights=getFlightsByFlightNumberWithPeriodForHU(flightNumber,"A",arrStopSchedluedLandingDateTime,huScheduleHandle.getBeforeMin(),huScheduleHandle.getAfterMin());
			if(flights.isEmpty() ){
				logger.info("   Flight not found ,Create New Arrival Flight!");
				comments.append("Flight not found ,Create New Arrival Flight").append(System.lineSeparator());;
				FlightXmlBean fxbean = new FlightXmlBean();
				fxbean.setMessageSequenceID(HuHandle.sequenceGenerator.generateNextNumber());
				fxbean.setOperationMode("NEW");
				fxbean.setOriginalDateTime(originalDateTime);
				fxbean.setSendDateTime(new Date());
				fxbean.setCreateDateTime(new Date());
				fxbean.setFlightIdentity(flightNumber);
				fxbean.setFlightDirection("A");
				fxbean.setFlightScheduledDate(arrStopSchedluedLandingDateTime);
				fxbean.setFlightScheduledDateTime(arrStopSchedluedLandingDateTime);
				fxbean.setFlightServiceType(serviceType);
				fxbean.setCreateReason(huScheduleHandle.getCreateReason());
				fxbean.setXmlStatus(huScheduleHandle.getStatusNew());
				fxbean.setScheduledPreviousAirportDepartureDateTime(depStopSchedluedTakeOffDateTime);
				fxbean.setRegistration(registration);
                String[] routingArray = routing.split("-");
                if(routingArray.length==2){
                	fxbean.setAirportIATACodeLeg1(routingArray[0]);
                }
                if(routingArray.length==3){
                	if(routingArray[1].equalsIgnoreCase(huScheduleHandle.getBaseAirportIATA())){
                		fxbean.setAirportIATACodeLeg1(routingArray[0]);
                	}else{
                		fxbean.setAirportIATACodeLeg1(routingArray[0]);
                		fxbean.setAirportIATACodeLeg2(routingArray[1]);
                	}
                }
                if(status.equalsIgnoreCase("SCH")){
                	logger.info("Message ststus:{},For NEW arrival flight,add nothing.",new Object[]{status});
					comments.append("For NEW arrival flight,add nothing.").append(System.lineSeparator());
                	 xmlMessage =huScheduleHandle.createImfMessage(fxbean);
				}else if(status.equalsIgnoreCase("ATD")){
					
					logger.info("Message ststus:{},For NEW arrival flight,No need to add offblocktime.",new Object[]{status});
					comments.append("For NEW arrival flight,No need to add offblocktime.").append(System.lineSeparator());
					 xmlMessage =huScheduleHandle.createImfMessage(fxbean);
				}else if(status.equalsIgnoreCase("DEP")){
					logger.info("Message ststus:{},add ATOTout:{},ELDT{}",new Object[]{status,depStopActualTakeOffDateTimeString,arrStopEstimatedLandingDateTimeString});
					comments.append("add ATOTout:").append(depStopActualTakeOffDateTimeString).append(System.lineSeparator());
					comments.append("add ELDT:").append(arrStopEstimatedLandingDateTimeString).append(System.lineSeparator());
					fxbean.setActualPreviousAirportDepartureDateTime(depStopActualTakeOffDateTime);
					fxbean.setEstimatedLandingDateTime(arrStopEstimatedLandingDateTime);
					xmlMessage =huScheduleHandle.createImfMessage(fxbean);
					
				}else if(status.equalsIgnoreCase("ARR")||status.equalsIgnoreCase("NDR")){
					logger.info("Message ststus:{},add ALDT:{}",new Object[]{status,arrStopActualLandingDateTimeString});
					comments.append("add ALDT:").append(arrStopActualLandingDateTimeString).append(System.lineSeparator());
					fxbean.setActualLandingDateTime(arrStopActualLandingDateTime);
					 xmlMessage =huScheduleHandle.createImfMessage(fxbean);
					
				}else if(status.equalsIgnoreCase("ATA")){
					logger.info("Message ststus:{},add AIBT:{}",new Object[]{status,arrStopActualOnBlockDateTimeString});
					comments.append("add AIBT:").append(arrStopActualOnBlockDateTimeString).append(System.lineSeparator());
					fxbean.setActualOnBlockDateTime(arrStopActualOnBlockDateTime);
					 xmlMessage =huScheduleHandle.createImfMessage(fxbean);
					
				}else if(status.equalsIgnoreCase("DEL")){
					logger.info("Message ststus:{},add OperationStatus:{},add ELDT:{}",new Object[]{status,"DELET",arrStopEstimatedLandingDateTimeString});
					comments.append("add OperationStatus:").append("DELET").append(System.lineSeparator());
					comments.append("add ELDT:").append(arrStopEstimatedLandingDateTimeString).append(System.lineSeparator());
					fxbean.setEstimatedLandingDateTime(arrStopEstimatedLandingDateTime);					
					fxbean.setOperationStatus("DELET");
					fxbean.setXmlStatus(huScheduleHandle.getStatusDly());
					xmlMessage =huScheduleHandle.createImfMessage(fxbean);
				}else if(status.equalsIgnoreCase("RTR")){
					logger.info("Message ststus:{},add nothing!",new Object[]{status});
					comments.append("add nothing!").append(System.lineSeparator());
				}else if(status.equalsIgnoreCase("DIV")){
					logger.info("Message ststus:{},add nothing!",new Object[]{status});
					comments.append("add nothing!").append(System.lineSeparator());
				}else if(status.equalsIgnoreCase("CNL")){
					logger.info("Message ststus:{},add OperationStatus:{}",new Object[]{"CNCL"});
					comments.append("add OperationStatus:").append("CNCL").append(System.lineSeparator());
					fxbean.setOperationStatus("CNCL");
					fxbean.setXmlStatus(huScheduleHandle.getStatusCnl());
					  xmlMessage =huScheduleHandle.createImfMessage(fxbean);
				}else{
					logger.info("Message ststus:{},Unknow status, add nothing!",new Object[]{status});
					comments.append(" Unknow status, add nothing!").append(System.lineSeparator());
					  xmlMessage =huScheduleHandle.createImfMessage(fxbean);
				}
                //当出现一天两班的情况时，并且晚上的那个航班不存在，则不发送晚上的那个航班
                Map<String,Object> params = new HashMap<String,Object>();
    			params.put("flightNumber", flightNumber);
    			params.put("direction", "A");
    			params.put("flightScheduledDate", arrStopSchedluedLandingDateTime);
    			List<Flight> flightss=flightService.getFlightByFlightNumberWithDate(params);
                if(!flightss.isEmpty() && xmlMessage!=null){
                	xmlMessage.setStatus("N");
                	logger.info("For one day two flight,ignore the flight which haven't has a plan yet");
					comments.append("For one day two flight,ignore the flight which haven't has a plan yet ").append("set status N").append(System.lineSeparator());
                }
                logger.info("ImfMessage:\n{}",xmlMessage);
				if(xmlMessage!=null){
					xmlMessage.setOwner("HU");
					xmlMessage.setOrgMessage(orgMessage);
					imfMessageSet.add(xmlMessage);
					orgMessage.setStatus("S");
				}else{
					orgMessage.setStatus("I");
				}
				
				
		}else{
			Flight flight = flights.get(0);
			logger.info("   Find Flight, Id:{},Flight Number:{}",new Object[]{flight.getId(),flight.getFlightNumber()});
			FlightXmlBean fxbean = new FlightXmlBean();
			fxbean.setMessageSequenceID(HuHandle.sequenceGenerator.generateNextNumber());
			fxbean.setOperationMode("MOD");
			fxbean.setOriginalDateTime(originalDateTime);
			fxbean.setSendDateTime(new Date());
			fxbean.setCreateDateTime(new Date());
			fxbean.setFlightIdentity(flight.getFlightNumber());
			fxbean.setFlightDirection("A");
			fxbean.setXmlStatus(huScheduleHandle.getStatusMod());
			fxbean.setFlightScheduledDate(flight.getFlightScheduledDate());
			fxbean.setFlightScheduledDateTime(flight.getFlightScheduledDateTime());
			//请求计划时MOD消息发送机号
			fxbean.setRegistration(registration);
			if(status.equalsIgnoreCase("SCH")){
				logger.info("Message ststus:{},For MOD arrival flight,No key fields need to update.");
				comments.append("For MOD arrival flight,No key fields need to update.").append(System.lineSeparator());
				fxbean.setXmlStatus(huScheduleHandle.getStatusMod());
				xmlMessage =huScheduleHandle.createImfMessage(fxbean);
			}else if(status.equalsIgnoreCase("ATD")){
				logger.info("Message ststus:{},For MOD arrival flight,No need to update offblocktime.",new Object[]{status});
				comments.append("For MOD arrival flight,No need to  update offblocktime.").append(System.lineSeparator());
				fxbean.setXmlStatus(huScheduleHandle.getStatusMod());
				xmlMessage =huScheduleHandle.createImfMessage(fxbean);
			}else if(status.equalsIgnoreCase("DEP")){
				logger.info("Message ststus:{},update ATOTout:{},ELDT{}",new Object[]{status,depStopActualTakeOffDateTimeString,arrStopEstimatedLandingDateTimeString});
				comments.append("Update ATOTout:").append(depStopActualTakeOffDateTimeString).append(System.lineSeparator());
				comments.append("Update ELDT:").append(arrStopEstimatedLandingDateTimeString).append(System.lineSeparator());
				fxbean.setActualPreviousAirportDepartureDateTime(depStopActualTakeOffDateTime);
				fxbean.setEstimatedLandingDateTime(arrStopEstimatedLandingDateTime);
				fxbean.setXmlStatus(huScheduleHandle.getStatusMod());
				xmlMessage =huScheduleHandle.createImfMessage(fxbean);
				
			}else if(status.equalsIgnoreCase("ARR")||status.equalsIgnoreCase("NDR")){
				logger.info("Message ststus:{},update ALDT:{}",new Object[]{status,arrStopActualLandingDateTimeString});
				comments.append("Update ALDT:").append(arrStopActualLandingDateTimeString).append(System.lineSeparator());
				fxbean.setActualLandingDateTime(arrStopActualLandingDateTime);
				fxbean.setXmlStatus(huScheduleHandle.getStatusMod());
				xmlMessage =huScheduleHandle.createImfMessage(fxbean);
				
			}else if(status.equalsIgnoreCase("ATA")){
				logger.info("Message ststus:{},update AIBT:{}",new Object[]{status,arrStopActualOnBlockDateTimeString});
				comments.append("Update AIBT:").append(arrStopActualOnBlockDateTimeString).append(System.lineSeparator());
				fxbean.setActualOnBlockDateTime(arrStopActualOnBlockDateTime);
				fxbean.setXmlStatus(huScheduleHandle.getStatusMod());
				xmlMessage =huScheduleHandle.createImfMessage(fxbean);
				
			}else if(status.equalsIgnoreCase("DEL")){
				logger.info("Message ststus:{},update OperationStatus:{},update ELDT:{}",new Object[]{status,"DELET",arrStopEstimatedLandingDateTimeString});
				comments.append("Update OperationStatus:").append("DELET").append(System.lineSeparator());
				comments.append("ignore ELDT:").append(arrStopEstimatedLandingDateTimeString).append(System.lineSeparator());
				//fxbean.setEstimatedLandingDateTime(arrStopEstimatedLandingDateTime);					
				fxbean.setOperationStatus("DELET");
				fxbean.setXmlStatus(huScheduleHandle.getStatusDly());
				xmlMessage =huScheduleHandle.createImfMessage(fxbean);
			}else if(status.equalsIgnoreCase("RTR")){
				logger.info("Message ststus:{},Ignore Return flight!",new Object[]{status});
				comments.append("Ignore Return flight!");
			}else if(status.equalsIgnoreCase("DIV")){
				logger.info("Message ststus:{},Ignore Diversion flight!",new Object[]{status});
				comments.append("Ignore Diversion flight!");
			}else if(status.equalsIgnoreCase("CNL")){
				logger.info("Message ststus:{},update OperationStatus:{}",new Object[]{"CNCL"});
				comments.append("Update OperationStatus:").append("CNCL").append(System.lineSeparator());
				fxbean.setOperationStatus("CNCL");
				fxbean.setXmlStatus(huScheduleHandle.getStatusCnl());
				xmlMessage =huScheduleHandle.createImfMessage(fxbean);
			}else{
				logger.info("Message ststus:{},Unknow status ignored!",new Object[]{status});
				comments.append(" Unknow status ignored!");
			}
			
			logger.info("ImfMessage:\n{}",xmlMessage);
			if(xmlMessage!=null){
				xmlMessage.setOwner("HU");
				xmlMessage.setOrgMessage(orgMessage);
				imfMessageSet.add(xmlMessage);
				orgMessage.setStatus("S");
			}else{
				orgMessage.setStatus("I");
			}
		}
		}
		//arrival flight direction D
		if(depStop.equalsIgnoreCase(huScheduleHandle.getBaseAirportIATA()) && !arrStop.equalsIgnoreCase(huScheduleHandle.getBaseAirportIATA())){
/*			Map<String,Object> params = new HashMap<String,Object>();
		    params.put("flightNumber", flightNumber);
			params.put("direction", "D");
			params.put("flightScheduledDate", depStopSchedluedTakeOffDateTime);
		    List<Flight> flights=flightService.getFlightByFlightNumberWithDate(params);*/
			
			List<Flight> flights=getFlightsByFlightNumberWithPeriodForHU(flightNumber,"D",depStopSchedluedTakeOffDateTime,huScheduleHandle.getBeforeMin(),huScheduleHandle.getAfterMin());
			if(flights.isEmpty() ){
				logger.info("   Flight not found ,Create New Departure Flight!");
				comments.append("Flight not found ,Create New Departure Flight").append(System.lineSeparator());;
				FlightXmlBean fxbean = new FlightXmlBean();
				fxbean.setMessageSequenceID(HuHandle.sequenceGenerator.generateNextNumber());
				fxbean.setOperationMode("NEW");
				fxbean.setOriginalDateTime(originalDateTime);
				fxbean.setSendDateTime(new Date());
				fxbean.setCreateDateTime(new Date());
				fxbean.setFlightIdentity(flightNumber);
				fxbean.setFlightDirection("D");
				fxbean.setFlightScheduledDate(depStopSchedluedTakeOffDateTime);
				fxbean.setFlightScheduledDateTime(depStopSchedluedTakeOffDateTime);
				fxbean.setFlightServiceType(serviceType);
				fxbean.setCreateReason(huScheduleHandle.getCreateReason());
				fxbean.setXmlStatus(huScheduleHandle.getStatusNew());
				fxbean.setRegistration(registration);
                String[] routingArray = routing.split("-");
                if(routingArray.length==2){
                	fxbean.setAirportIATACodeLeg1(routingArray[1]);
                }
                if(routingArray.length==3){
                	if(routingArray[0].equalsIgnoreCase(huScheduleHandle.getBaseAirportIATA())){
                		fxbean.setAirportIATACodeLeg1(routingArray[1]);
                		fxbean.setAirportIATACodeLeg2(routingArray[2]);
                	}else{
                		fxbean.setAirportIATACodeLeg1(routingArray[2]);

                	}
                }
                fxbean.setScheduledNextAirportArrivalDateTime(arrStopSchedluedLandingDateTime);
                if(status.equalsIgnoreCase("SCH")){
					logger.info("Message ststus:{},For NEW departure flight,add nothing.",new Object[]{status});
					comments.append("add nothing.").append(System.lineSeparator());
					xmlMessage =huScheduleHandle.createImfMessage(fxbean);
				}else if(status.equalsIgnoreCase("ATA")){
					logger.info("Message ststus:{},For NEW departure flight,No need to add onblocktime.");
					comments.append("For NEW departure flight,No need to add onblocktime.");
					xmlMessage =huScheduleHandle.createImfMessage(fxbean);
				}else if(status.equalsIgnoreCase("ARR") || status.equalsIgnoreCase("NDR")){
					logger.info("Message ststus:{},add nextALDT:{}.",new Object[]{status,arrStopActualLandingDateTimeString});
					comments.append("add nextALDT:").append(arrStopActualLandingDateTimeString).append(System.lineSeparator());
					fxbean.setActualNextAirportArrivalDateTime(arrStopActualLandingDateTime);
					xmlMessage =huScheduleHandle.createImfMessage(fxbean);
				}else if(status.equalsIgnoreCase("ATD")){
					logger.info("Message ststus:{},add AOBT:{}",new Object[]{status,depStopActualOffBlockDateTimeString});				
					comments.append("add AOBT:").append(depStopActualOffBlockDateTimeString).append(System.lineSeparator());
					fxbean.setActualOffBlockDateTime(depStopActualOffBlockDateTime);
					xmlMessage =huScheduleHandle.createImfMessage(fxbean);
				}else if(status.equalsIgnoreCase("DEP")){
					logger.info("Message ststus:{},add ATOT:{}",new Object[]{status,depStopActualTakeOffDateTimeString});				
					comments.append("add ATOT:").append(depStopActualTakeOffDateTimeString).append(System.lineSeparator());
					fxbean.setActualTakeOffDateTime(depStopActualTakeOffDateTime);
					xmlMessage =huScheduleHandle.createImfMessage(fxbean);
				}else if(status.equalsIgnoreCase("DEL")){
					logger.info("Message ststus:{},add OperationStatus:{}, add ETOT:{}",new Object[]{status,"DELET",depStopEstimatedTakeOffDateTimeString});				
					comments.append("add OperationStatus:").append("DELET").append(System.lineSeparator());
					comments.append("add ETOT:").append(depStopEstimatedTakeOffDateTimeString).append(System.lineSeparator());
					fxbean.setEstimatedTakeOffDateTime(depStopEstimatedTakeOffDateTime);					
					fxbean.setOperationStatus("DELET");
					fxbean.setXmlStatus(huScheduleHandle.getStatusDly());
					xmlMessage =huScheduleHandle.createImfMessage(fxbean);
				}else if(status.equalsIgnoreCase("RTR")){
					logger.info("Message ststus:{},add nothing!",new Object[]{status});
					comments.append("add nothing!").append(System.lineSeparator());
				}else if(status.equalsIgnoreCase("DIV")){
					logger.info("Message ststus:{},add nothing!",new Object[]{status});
					comments.append("add nothing!").append(System.lineSeparator());
				}else if(status.equalsIgnoreCase("CNL")){
					logger.info("Message ststus:{},update OperationStatus:{}",new Object[]{status,"CNCL"});				
					comments.append("Update OperationStatus:").append("CNCL").append(System.lineSeparator());
					fxbean.setOperationStatus("CNCL");
					fxbean.setXmlStatus(huScheduleHandle.getStatusCnl());
					xmlMessage =huScheduleHandle.createImfMessage(fxbean);
				}else{
					logger.info("Message ststus:{},Unknow status,add nothing!",new Object[]{status});
					comments.append(" Unknow status,add nothing!").append(System.lineSeparator());
					xmlMessage =huScheduleHandle.createImfMessage(fxbean);
					 
				}
				
	                logger.info("ImfMessage:\n{}",xmlMessage);
					if(xmlMessage!=null){
						xmlMessage.setOwner("HU");
						xmlMessage.setOrgMessage(orgMessage);
						imfMessageSet.add(xmlMessage);
						orgMessage.setStatus("S");
					}else{
						orgMessage.setStatus("I");
					}
				
		}else{
			Flight flight = flights.get(0);
			logger.info("   Find Flight, Id:{},Flight Number:{}",new Object[]{flight.getId(),flight.getFlightNumber()});
			FlightXmlBean fxbean = new FlightXmlBean();
			fxbean.setMessageSequenceID(HuHandle.sequenceGenerator.generateNextNumber());
			fxbean.setOperationMode("MOD");
			fxbean.setOriginalDateTime(originalDateTime);
			fxbean.setSendDateTime(new Date());
			fxbean.setCreateDateTime(new Date());
			fxbean.setFlightIdentity(flight.getFlightNumber());
			fxbean.setFlightDirection("D");
			fxbean.setFlightScheduledDate(flight.getFlightScheduledDate());
			fxbean.setFlightScheduledDateTime(flight.getFlightScheduledDateTime());
			fxbean.setXmlStatus(huScheduleHandle.getStatusMod());
			//请求计划时MOD消息发送机号
			fxbean.setRegistration(registration);
			if(status.equalsIgnoreCase("SCH")){
				logger.info("Message ststus:{},For MOD departure flight,No key fields need to update.",new Object[]{status});
				comments.append("For MOD departure flight,No key fields need to update.").append(System.lineSeparator());
				xmlMessage =huScheduleHandle.createImfMessage(fxbean);
			}else if(status.equalsIgnoreCase("ATA")){
				logger.info("Message ststus:{},For MOD departure flight,No need to update onblocktime.");
				comments.append("For MOD departure flight,No need to update onblocktime.");
				xmlMessage =huScheduleHandle.createImfMessage(fxbean);
			}else if(status.equalsIgnoreCase("ARR") || status.equalsIgnoreCase("NDR")){
				logger.info("Message ststus:{},update nextALDT:{}.",new Object[]{status,arrStopActualLandingDateTimeString});
				comments.append("update nextALDT:").append(arrStopActualLandingDateTimeString).append(System.lineSeparator());
				fxbean.setActualNextAirportArrivalDateTime(arrStopActualLandingDateTime);
				fxbean.setXmlStatus(huScheduleHandle.getStatusMod());
				xmlMessage =huScheduleHandle.createImfMessage(fxbean);
			}else if(status.equalsIgnoreCase("ATD")){
				logger.info("Message ststus:{},update AOBT:{}",new Object[]{status,depStopActualOffBlockDateTimeString});				
				comments.append("update AOBT:").append(depStopActualOffBlockDateTimeString).append(System.lineSeparator());
				fxbean.setActualOffBlockDateTime(depStopActualOffBlockDateTime);
				fxbean.setXmlStatus(huScheduleHandle.getStatusMod());
				xmlMessage =huScheduleHandle.createImfMessage(fxbean);
			}else if(status.equalsIgnoreCase("DEP")){
				logger.info("Message ststus:{},update ATOT:{}",new Object[]{status,depStopActualTakeOffDateTimeString});				
				comments.append("update ATOT:").append(depStopActualTakeOffDateTimeString).append(System.lineSeparator());
				fxbean.setActualTakeOffDateTime(depStopActualTakeOffDateTime);
				fxbean.setXmlStatus(huScheduleHandle.getStatusMod());
				xmlMessage =huScheduleHandle.createImfMessage(fxbean);
			}else if(status.equalsIgnoreCase("DEL")){
				logger.info("Message ststus:{},update OperationStatus:{}, update ETOT:{}",new Object[]{status,"DELET",depStopEstimatedTakeOffDateTimeString});				
				comments.append("Update OperationStatus:").append("DELET").append(System.lineSeparator());
				comments.append("ignore ETOT:").append(depStopEstimatedTakeOffDateTimeString).append(System.lineSeparator());
				//fxbean.setEstimatedTakeOffDateTime(depStopEstimatedTakeOffDateTime);					
				fxbean.setOperationStatus("DELET");
				fxbean.setXmlStatus(huScheduleHandle.getStatusDly());
				xmlMessage =huScheduleHandle.createImfMessage(fxbean);
			}else if(status.equalsIgnoreCase("RTR")){
				logger.info("Message ststus:{},Ignore Return flight!",new Object[]{status});
				comments.append("Ignore Return flight!");
			}else if(status.equalsIgnoreCase("DIV")){
				logger.info("Message ststus:{},Ignore Diversion flight!",new Object[]{status});
				comments.append("Ignore Diversion flight!");
			}else if(status.equalsIgnoreCase("CNL")){
				logger.info("Message ststus:{},update OperationStatus:{}",new Object[]{status,"CNCL"});				
				comments.append("Update OperationStatus:").append("Delay").append(System.lineSeparator());
				fxbean.setOperationStatus("CNCL");
				fxbean.setXmlStatus(huScheduleHandle.getStatusCnl());
				xmlMessage =huScheduleHandle.createImfMessage(fxbean);
			}else{
				logger.info("Message ststus:{},Unknow status ignored!",new Object[]{status});
				comments.append(" Unknow status,ignored!").append(System.lineSeparator());
				
			}

            logger.info("ImfMessage:\n{}",xmlMessage);
			if(xmlMessage!=null){
				xmlMessage.setOwner("HU");
				xmlMessage.setOrgMessage(orgMessage);
				imfMessageSet.add(xmlMessage);
				orgMessage.setStatus("S");
			}else{
				orgMessage.setStatus("I");
			}
		}
		}
		//本场到本场，试飞航班，生成2个航班
		if(depStop.equalsIgnoreCase(huScheduleHandle.getBaseAirportIATA()) && arrStop.equalsIgnoreCase(huScheduleHandle.getBaseAirportIATA())){
			
		}
		
		orgMessage.setComments(comments.toString());
		orgMessage.setImfMessages(imfMessageSet);
		orgMessage.setCreateDateTime(new Date());
		orgMessage.setUpdateDateTime(new Date());
		
		if(huScheduleHandle.getHandleMode().equalsIgnoreCase("PRD")){
			orgMessageService.saveOrUpdate(orgMessage);
			logger.info("Save orgmessage into database success.");
		}else{
			logger.info("Non PRD Mode,wouldn't save.");
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
public List<Flight> getFlightsByFlightNumberWithPeriodForHU(String flightNumber,String direction,Date scheduledDateTime,int beforeMin,int afterMin){
	HashMap<String, Object> params = new HashMap<String,Object>();
	params.put("flightNumber", flightNumber);
	params.put("direction", direction);
	Map<String, Date> period = TimeHandle.getFlightDateTimePeriod(scheduledDateTime, beforeMin, afterMin);
	params.putAll(period);
	logger.info("   Query Parameters:");
	logger.info("     flightNumber:{}   Direction:{}",new Object[]{params.get("flightNumber"),params.get("direction")});
	logger.info("     Period:{} ~ {}",new Object[]{TimeHandle.getDateTimeString((Date) params.get("beforeDateTime")),TimeHandle.getDateTimeString((Date) params.get("afterDateTime"))});
	List<Flight> flights = flightService.getFlightByFlightNumberWithDateTimePeriod(params);
	//add suffix N as new flightnumber
	if(flights.isEmpty()){
		logger.info("     Flight not found,try suffix N");
    		String newFlightNumber = flightNumber +"N";
    		params.put("flightNumber", newFlightNumber);
    		logger.info("     New flightNumber:{}   Direction:{}",new Object[]{params.get("flightNumber"),params.get("direction")});
    	 flights = flightService.getFlightByFlightNumberWithDateTimePeriod(params);
	}
	
	return flights;
 }

}
