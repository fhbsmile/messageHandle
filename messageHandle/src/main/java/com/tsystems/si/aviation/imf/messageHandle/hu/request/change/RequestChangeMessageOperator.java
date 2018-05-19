/** 
 * Project Name:messageHandle 
 * File Name:RequestChangeMessageOperator.java 
 * Package Name:com.tsystems.si.aviation.imf.messageHandle.hu.request.change 
 * Date:2016年5月13日上午11:07:59
 * version:v1.0
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */ 


/** 
 * Project Name:messageHandle 
 * File Name:RequestChangeMessageOperator.java 
 * Package Name:com.tsystems.si.aviation.imf.messageHandle.hu.request.change 
 * Date:2016年5月13日上午11:07:59 
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */  
package com.tsystems.si.aviation.imf.messageHandle.hu.request.change;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tsystems.si.aviation.imf.messageHandle.db.bean.Flight;
import com.tsystems.si.aviation.imf.messageHandle.db.bean.ImfMessage;
import com.tsystems.si.aviation.imf.messageHandle.db.bean.OrgMessage;
import com.tsystems.si.aviation.imf.messageHandle.db.service.FlightServiceI;
import com.tsystems.si.aviation.imf.messageHandle.db.service.OrgMessageServiceI;
import com.tsystems.si.aviation.imf.messageHandle.handles.hu.HuHandle;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.AirportDictionary;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.FlightXmlBean;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.StcDictionary;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.TelexConstant;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.TimeHandle;

/**   
 * @ClassName:  RequestChangeMessageOperator   
 * @Description:TODO
 * @author: Bolo.Fang@t-systems.com  
 * @date:   2016年5月13日 上午11:07:59   
 *      
 */
@Service
public class RequestChangeMessageOperator {

	private static final Logger     logger               = LoggerFactory.getLogger(RequestChangeMessageOperator.class);

	
	@Autowired
	private FlightServiceI flightService;
	
	@Autowired
	private OrgMessageServiceI orgMessageService;
	
	@Autowired
	private HuHandle huChangeHandle;
	
	@Autowired
	private StcDictionary stcDictionary;
	@Autowired
	private AirportDictionary airportDictionary;
	
	public void process(String flcdJsonString){
		JSONObject jb = JSON.parseObject(flcdJsonString);
		logger.info("FltLegsChangeDetail Json String :{}",flcdJsonString);
		OrgMessage orgMessage = new OrgMessage();
		Set<ImfMessage>  imfMessageSet = new HashSet<ImfMessage>();
		ImfMessage xmlMessage =null;
		String changeTypet = jb.getString("fType");
		String changeType = getEnglishType(changeTypet);
		String stc = jb.getString("stc");
		String serviceType = stcDictionary.getCAACStcByIATA(stc);
		String flightNumbert = jb.getString("flightNo");
		String flightNumber = huChangeHandle.flightNumberFormat(flightNumbert);
		String status = jb.getString("status");
		String registrationNew = jb.getString("acNo");
		String registrationOld =jb.getString("oldAcNo");
		Date orgDateTime = jb.getJSONObject("fltLegsChangeTitle").getDate("subtime");
		String depStop = airportDictionary.getNewIATAByIATA(jb.getString("depStn"));
		String arrStop = airportDictionary.getNewIATAByIATA(jb.getString("arrStn"));

		String direction =getDirection(depStop,arrStop);
		Date takeOffDateTime = jb.getDate("std");
		String takeOffDateTimeString = jb.getString("std");
		Date landingDateTime = jb.getDate("sta");
		String landingDateTimeString = jb.getString("sta");
		Date estimatedTakeOffDateTime = jb.getDate("etd");
		Date estimatedLandingDateTime = jb.getDate("eta");
		String estimatedTakeOffDateTimeString = jb.getString("etd");
		String estimatedLandingDateTimeString = jb.getString("eta");

		logger.info("flightNumber:{}",flightNumber);
		logger.info("MessageType:{}",changeType);
		logger.info("stcIATA:{}",stc);
		logger.info("stcCAAC:{}",serviceType);
		logger.info("Status:{}",status);
		logger.info("direction:{}",direction);
		logger.info("routing:{}-{}",new Object[]{depStop,arrStop});
		logger.info("scheduledDepatureTime:{}",takeOffDateTimeString);
		logger.info("scheduledArrivalTime:{}",landingDateTimeString);
		
		orgMessage.setGenerateDateTime(orgDateTime);
		String flcdJsonStringPretty = JSON.toJSONString(jb, true);
		orgMessage.setContent(flcdJsonStringPretty);
		orgMessage.setOwner("HU");
		orgMessage.setType("HURC");

		StringBuffer comments = new StringBuffer();
		comments.append("flightNumber:").append(flightNumber).append(System.lineSeparator());
		comments.append("stcIATA:").append(stc).append("   stcCAAC:").append(serviceType).append(System.lineSeparator());
		comments.append("Status:").append(status).append("   ChangeType:").append(changeType).append(System.lineSeparator());
		comments.append("direction:").append(direction).append(System.lineSeparator());
		comments.append("routing:").append(depStop).append("-").append(arrStop).append(System.lineSeparator());
		comments.append("std:").append(takeOffDateTimeString).append(System.lineSeparator());
		comments.append("sta:").append(landingDateTimeString).append(System.lineSeparator());
		if(changeType==null){
			logger.error("Message Type is null! ignored");
			
		}else{
			logger.info("MessageEnglishType:{}",changeType);
			if(direction.equalsIgnoreCase("A")){
				
/*				Map<String,Object> params = new HashMap<String,Object>();
				params.put("flightNumber", flightNumber);
				params.put("direction", direction);
				params.put("flightScheduledDate", landingDateTime);
				List<Flight> flights=flightService.getFlightByFlightNumberWithDate(params);*/
				List<Flight> flights=getFlightsByFlightNumberWithPeriodForHU(flightNumber,direction,landingDateTime,huChangeHandle.getBeforeMin(),huChangeHandle.getAfterMin());
				if(!flights.isEmpty() ){
					Flight flight = flights.get(0);
					logger.info("   Find Flight, Id:{},Flight Number:{}",new Object[]{flight.getId(),flight.getFlightNumber()});
					FlightXmlBean fxbean = new FlightXmlBean();
					fxbean.setMessageSequenceID(HuHandle.sequenceGenerator.generateNextNumber());
					fxbean.setOperationMode("MOD");
					fxbean.setOriginalDateTime(orgDateTime);
					fxbean.setSendDateTime(new Date());
					fxbean.setCreateDateTime(new Date());
					fxbean.setFlightIdentity(flight.getFlightNumber());
					fxbean.setFlightDirection(direction);
					fxbean.setFlightScheduledDate(flight.getFlightScheduledDate());
					fxbean.setFlightScheduledDateTime(flight.getFlightScheduledDateTime());
					fxbean.setXmlStatus(huChangeHandle.getStatusMod());
					if(changeType.equals("NewAdd")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("Change")||changeType.equals("ChangeAC")||changeType.equals("ChangeTimeAC")){						
						logger.info("MessageType:{},update registration:{}",new Object[]{changeType,registrationNew});
						comments.append("update new registration:").append(registrationNew).append(System.lineSeparator());
						fxbean.setRegistration(registrationNew);
						fxbean.setXmlStatus(huChangeHandle.getStatusMod());
					    xmlMessage =huChangeHandle.createImfMessage(fxbean);
					}else if(changeType.equals("ChangeACT")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("ChangeTime")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("ChangeTimeACT")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("Delay")){
						logger.info("MessageType:{},update OperationStatus:{}",new Object[]{changeType,"DELET"});
						comments.append("update OperationStatus:").append("DELET").append(System.lineSeparator());
						comments.append("ignore ELDT:").append(estimatedLandingDateTimeString).append(System.lineSeparator());
						fxbean.setOperationStatus("DELET");
						//fxbean.setEstimatedLandingDateTime(estimatedLandingDateTime);
						fxbean.setXmlStatus(huChangeHandle.getStatusDly());
					    xmlMessage =huChangeHandle.createImfMessage(fxbean);
					}else if(changeType.equals("ChangeDelay")||changeType.equals("ChangeDelayAC")){
						logger.info("MessageType:{},update registration:{}",new Object[]{changeType,registrationNew});
						comments.append("update new registration:").append(registrationNew).append(System.lineSeparator());
						fxbean.setRegistration(registrationNew);
						fxbean.setXmlStatus(huChangeHandle.getStatusMod());
					    xmlMessage =huChangeHandle.createImfMessage(fxbean);
					}else if(changeType.equals("ChangeDelayACT")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("Cancel")){
						logger.info("MessageType:{},update OperationStatus:{}",new Object[]{changeType,"CNCL"});
						comments.append("update OperationStatus:").append("CNCL").append(System.lineSeparator());
						fxbean.setOperationStatus("CNCL");
						fxbean.setXmlStatus(huChangeHandle.getStatusMod());
					    xmlMessage =huChangeHandle.createImfMessage(fxbean);
					}else if(changeType.equals("Divertion")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("Return")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("Recovery")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("Early")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("ChangeEarly")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("ChangeEarlyACT")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("ChangeEarlyAC")){
						logger.info("MessageType:{},update registration:{}",new Object[]{changeType,registrationNew});
						comments.append("update new registration:").append(registrationNew).append(System.lineSeparator());
						fxbean.setRegistration(registrationNew);
						fxbean.setXmlStatus(huChangeHandle.getStatusMod());
					    xmlMessage =huChangeHandle.createImfMessage(fxbean);
					}else if(changeType.equals("RecoveryEarly")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else{
						logger.info("MessageType:{},Unknow Type Ignore!",new Object[]{changeType});
						comments.append("Unknow Type Ignore!").append(System.lineSeparator());
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
					logger.info("   Flight not found ,Change message wouldn't Create new flight, Ignore!");
					comments.append("Flight not found ,Change message wouldn't Create new flight, Ignore!").append(System.lineSeparator());
					orgMessage.setStatus("I");
				}
				
			}else{
/*				Map<String,Object> params = new HashMap<String,Object>();
				params.put("flightNumber", flightNumber);
				params.put("direction", direction);
				params.put("flightScheduledDate", takeOffDateTime);
				List<Flight> flights=flightService.getFlightByFlightNumberWithDate(params);*/
				List<Flight> flights=getFlightsByFlightNumberWithPeriodForHU(flightNumber,direction,takeOffDateTime,huChangeHandle.getBeforeMin(),huChangeHandle.getAfterMin());
				if(!flights.isEmpty() ){
					Flight flight = flights.get(0);
					logger.info("   Find Flight, Id:{},Flight Number:{}",new Object[]{flight.getId(),flight.getFlightNumber()});
					FlightXmlBean fxbean = new FlightXmlBean();
					fxbean.setMessageSequenceID(HuHandle.sequenceGenerator.generateNextNumber());
					fxbean.setOperationMode("MOD");
					fxbean.setOriginalDateTime(orgDateTime);
					fxbean.setSendDateTime(new Date());
					fxbean.setCreateDateTime(new Date());
					fxbean.setFlightIdentity(flight.getFlightNumber());
					fxbean.setFlightDirection(direction);
					fxbean.setFlightScheduledDate(flight.getFlightScheduledDate());
					fxbean.setFlightScheduledDateTime(flight.getFlightScheduledDateTime());
					fxbean.setXmlStatus(huChangeHandle.getStatusMod());
					if(changeType.equals("NewAdd")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("Change")||changeType.equals("ChangeAC")||changeType.equals("ChangeTimeAC")){						
						logger.info("MessageType:{},update registration:{}",new Object[]{changeType,registrationNew});
						comments.append("update new registration:").append(registrationNew).append(System.lineSeparator());
						fxbean.setRegistration(registrationNew);
						fxbean.setXmlStatus(huChangeHandle.getStatusMod());
					    xmlMessage =huChangeHandle.createImfMessage(fxbean);
					}else if(changeType.equals("ChangeACT")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("ChangeTime")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("ChangeTimeACT")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("Delay")){
						logger.info("MessageType:{},update OperationStatus:{}",new Object[]{changeType,"DELET"});
						comments.append("update OperationStatus:").append("Delay").append(System.lineSeparator());
						comments.append("ignore ETOT:").append(estimatedTakeOffDateTimeString).append(System.lineSeparator());
						fxbean.setOperationStatus("DELET");
						//fxbean.setEstimatedTakeOffDateTime(estimatedTakeOffDateTime);
						fxbean.setXmlStatus(huChangeHandle.getStatusDly());
					    xmlMessage =huChangeHandle.createImfMessage(fxbean);
					}else if(changeType.equals("ChangeDelay")||changeType.equals("ChangeDelayAC")){
						logger.info("MessageType:{},update registration:{}",new Object[]{changeType,registrationNew});
						comments.append("update new registration:").append(registrationNew).append(System.lineSeparator());
						fxbean.setRegistration(registrationNew);
						fxbean.setXmlStatus(huChangeHandle.getStatusMod());
					    xmlMessage =huChangeHandle.createImfMessage(fxbean);
					}else if(changeType.equals("ChangeDelayACT")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("Cancel")){
						logger.info("MessageType:{},update OperationStatus:{}",new Object[]{changeType,"CNCL"});
						comments.append("update OperationStatus:").append("CNCL").append(System.lineSeparator());
						fxbean.setOperationStatus("CNCL");
						fxbean.setXmlStatus(huChangeHandle.getStatusMod());
					    xmlMessage =huChangeHandle.createImfMessage(fxbean);
					}else if(changeType.equals("Divertion")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("Return")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("Recovery")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("Early")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("ChangeEarly")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("ChangeEarlyACT")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else if(changeType.equals("ChangeEarlyAC")){
						logger.info("MessageType:{},update registration:{}",new Object[]{changeType,registrationNew});
						comments.append("update new registration:").append(registrationNew).append(System.lineSeparator());
						fxbean.setRegistration(registrationNew);
						fxbean.setXmlStatus(huChangeHandle.getStatusMod());
					    xmlMessage =huChangeHandle.createImfMessage(fxbean);
					}else if(changeType.equals("RecoveryEarly")){
						logger.info("MessageType:{},Ignore!",new Object[]{changeType});
						comments.append("Ignore!").append(System.lineSeparator());
					}else{
						logger.info("MessageType:{},Unknow Type Ignore!",new Object[]{changeType});
						comments.append("Unknow Type Ignore!").append(System.lineSeparator());
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
					logger.info("   Flight not found ,Change message wouldn't Create new flight, Ignore!");
					comments.append("Flight not found ,Change message wouldn't Create new flight, Ignore!").append(System.lineSeparator());
					orgMessage.setStatus("I");
				}
			}
			
			
			
		}
		
		
		orgMessage.setImfMessages(imfMessageSet);
		orgMessage.setComments(comments.toString());
		orgMessage.setCreateDateTime(new Date());
		orgMessage.setUpdateDateTime(new Date());

		
		if(huChangeHandle.getHandleMode().equalsIgnoreCase("PRD")){
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
	public String getEnglishType(String messageType){
		String type=null;
		if(messageType!=null){
			if(messageType.equals("新增")){
				type="NewAdd";
			}else if(messageType.equals("变更")){
				type="Change";
			}else if(messageType.equals("机型变更")){
				type="ChangeACT";
			}else if(messageType.equals("机号变更")){
				type="ChangeAC";
			}else if(messageType.equals("时刻变更")){
				type="ChangeTime";
			}else if(messageType.equals("机型及时刻变更")){
				type="ChangeTimeACT";
			}else if(messageType.equals("机号及时刻变更")){
				type="ChangeTimeAC";
			}else if(messageType.equals("延误")){
				type="Delay";
			}else if(messageType.equals("变更延误")){
				type="ChangeDelay";
			}else if(messageType.equals("机型变更及延误")){
				type="ChangeDelayACT";
			}else if(messageType.equals("机号变更及延误")){
				type="ChangeDelayAC";
			}else if(messageType.equals("取消")){
				type="Cancel";
			}else if(messageType.equals("备降")){
				type="Divertion";
			}else if(messageType.equals("返航")){
				type="Return";
			}else if(messageType.equals("恢复")){
				type="Recovery";
			}else if(messageType.equals("提前")){
				type="Early";
			}else if(messageType.equals("变更提前")){
				type="ChangeEarly";
			}else if(messageType.equals("机型变更及提前")){
				type="ChangeEarlyACT";
			}else if(messageType.equals("机号变更及提前")){
				type="ChangeEarlyAC";
			}else if(messageType.equals("恢复提前")){
				type="RecoveryEarly";
			}
		}
		
		return type;
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
			logger.info("     Flight not found,try suffix A");
        		String newFlightNumber = flightNumber +"A";
        		params.put("flightNumber", newFlightNumber);
        		logger.info("     New flightNumber:{}   Direction:{}",new Object[]{params.get("flightNumber"),params.get("direction")});
        	 flights = flightService.getFlightByFlightNumberWithDateTimePeriod(params);
		}
		
		return flights;
     }
}
