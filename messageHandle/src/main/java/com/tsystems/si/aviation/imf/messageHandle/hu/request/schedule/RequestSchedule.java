/** 
 * Project Name:huMessageHandle 
 * File Name:RequestSchedule.java 
 * Package Name:com.tsystems.si.aviation.imf.huMessageHandle.request 
 * Date:2016年5月10日下午11:15:32
 * version:v1.0
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */ 


package com.tsystems.si.aviation.imf.messageHandle.hu.request.schedule;



import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.hnair.opcnet.api.complextype.PageParam;
import com.hnair.opcnet.api.complextype.Result;
import com.hnair.opcnet.api.ods.foc.FocFltInfoApi;
import com.hnair.opcnet.api.v2.ApiRequest;
import com.hnair.opcnet.api.v2.ApiResponse;
import com.hnair.opcnet.api.v2.ApiUtils;
import com.tsystems.si.aviation.imf.messageHandle.db.bean.SysParameter;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.TelexConstant;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.TimeHandle;

public class RequestSchedule {
	private static final Logger     logger               = LoggerFactory.getLogger(RequestSchedule.class);
	String scheduleStartDateTime = "1,5,0,0";
	String scheduleEndDateTime   = "2,5,0,0";

	
	private FocFltInfoApi focFltInfoApi;
	@Autowired
	private RequestScheduleMessageOperator requestScheduleMessageOperator;
	@Autowired
	private RequestScheduleForComFlightOperator requestScheduleForComFlightOperator;
	public void request(){
	  try {
				String requestStartString = TimeHandle.getDateTimeString(new Date());
				logger.info(">>>>>>>>>Request Satrt Date Time:{}",requestStartString);
				String requestStartDateTime = TimeHandle.getQueryDateTime(scheduleStartDateTime);
				String requestEndDateTime =   TimeHandle.getQueryDateTime(scheduleEndDateTime);
				
				logger.info("Request Schedule peried is :{}-{}",new Object[]{requestStartDateTime,requestEndDateTime});
				// 处理联程的航班
				ApiRequest speRequest = ApiUtils.makeApiRequest();
			    
				// specails = new ArrayList<String>();
				// specails.add("HU7682");
			   // logger.info("Try to retrieve flight for =================="+specails.get(0)+" from:"+start+" to:"+end);
				//speRequest.getOptions().put("fltNoList", specails);
				speRequest.getOptions().put("stdStart", requestStartDateTime);
				//speRequest.getOptions().put("staStart", requestStartDateTime);
				speRequest.getOptions().put("stdEnd", requestEndDateTime);
				//speRequest.getOptions().put("staEnd", requestEndDateTime);
				speRequest.getOptions().put("depOrArrStn", "HAK");
				//speRequest.getOptions().put("isStopOver", 1);
				PageParam spePageParam = new PageParam();
				spePageParam.setPageIndex(1);
				spePageParam.setPageSize(500);
				speRequest.setPageParam(spePageParam);
			    
		
				ApiResponse response = focFltInfoApi.getFocFlightInfoV2(speRequest);
		
				Result result = response.getResult();
				String resultJson =JSON.toJSONString(result);
				logger.info("Request Result:{}",resultJson);
				List<Map<String,Object>> responseDatalist= response.getData();
				if(responseDatalist.isEmpty()){
					logger.info("No record return.");
				}else{
					int listSize = responseDatalist.size();
					logger.info("Recieved record amount:{}",listSize);
					for(int i=0;i<listSize;i++){
						Map<String,Object> dataMap = responseDatalist.get(i);
						String recordeJson =JSON.toJSONString(dataMap);
						logger.info("Process record:{},content:{}",new Object[]{i,recordeJson});
						String depStop = (String) dataMap.get("depStn");
						String arrStop = (String) dataMap.get("arrStn");if(arrStop==null || depStop==null){
							logger.error("Ignore message, Has null stop! {}-{}",new Object[]{depStop,arrStop});
						}else{
							if(depStop.equalsIgnoreCase(TelexConstant.default_baseAirportIATA) || arrStop.equalsIgnoreCase(TelexConstant.default_baseAirportIATA)){
								requestScheduleMessageOperator.process(recordeJson);
							}else{
								logger.warn("Ignore message, No baseAirport! {}-{}",new Object[]{depStop,arrStop});
							}
						}
						
						
					}
				}
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		String requestEndString = TimeHandle.getDateTimeString(new Date());
		logger.info("Request End Date Time:{}",requestEndString);
	}
	
	public void requestForCompare(){
		try {
				SysParameter huScheduleRequestParameter = requestScheduleForComFlightOperator.getHUScheduleParameter();
				String indictor = huScheduleRequestParameter.getSpValue1();
				String startDateTime = huScheduleRequestParameter.getSpValue2().trim();
				String   endDateTime = huScheduleRequestParameter.getSpValue3();
				logger.info("Request indictor:{}",indictor);
				logger.info("Request Schedule peried is :{}-{}",new Object[]{startDateTime,endDateTime});
				if(indictor.equalsIgnoreCase("R")){
					if(matcheHUTimeFormat(startDateTime)&&matcheHUTimeFormat(endDateTime)){
						int n=requestScheduleForComFlightOperator.deleteComFlightByOwner("HU");
						logger.info("{} ComFlights deleted.",n);
		
						ApiRequest speRequestForstd = ApiUtils.makeApiRequest();
		
						speRequestForstd.getOptions().put("stdStart", startDateTime);				
						speRequestForstd.getOptions().put("stdEnd", endDateTime);
						speRequestForstd.getOptions().put("depOrArrStn", "HAK");
						PageParam spePageParamForstd = new PageParam();
						spePageParamForstd.setPageIndex(1);
						spePageParamForstd.setPageSize(500);
						speRequestForstd.setPageParam(spePageParamForstd);
						ApiResponse responseForstd = focFltInfoApi.getFocFlightInfoV2(speRequestForstd);
						Result resultForstd = responseForstd.getResult();
						String resultJsonForstd =JSON.toJSONString(resultForstd);
						logger.info("Request Result For std:{}",resultJsonForstd);
						List<Map<String,Object>> responseDatalist= responseForstd.getData();
						
						ApiRequest speRequestForsta = ApiUtils.makeApiRequest();
						speRequestForsta.getOptions().put("staStart", startDateTime);
						speRequestForsta.getOptions().put("staEnd", endDateTime);
						speRequestForsta.getOptions().put("depOrArrStn", "HAK");
						PageParam spePageParamForsta = new PageParam();
						spePageParamForsta.setPageIndex(1);
						spePageParamForsta.setPageSize(500);
						speRequestForsta.setPageParam(spePageParamForsta);
						ApiResponse responseForsta = focFltInfoApi.getFocFlightInfoV2(speRequestForsta);
						Result resultForsta = responseForsta.getResult();
						String resultJsonForsta =JSON.toJSONString(resultForsta);
						logger.info("Request Result For sta:{}",resultJsonForsta);				
						List<Map<String,Object>> responseDatalistForsta= responseForsta.getData();
						
						
						responseDatalist.addAll(responseDatalistForsta);
						
						if(responseDatalist.isEmpty()){
							logger.info("No record return.");
						}else{
							int listSize = responseDatalist.size();
							logger.info("Recieved record amount:{}",listSize);
							for(int i=0;i<listSize;i++){
								Map<String,Object> dataMap = responseDatalist.get(i);
								String recordeJson =JSON.toJSONString(dataMap);
								logger.info("Process record:{},content:{}",new Object[]{i,recordeJson});
								String depStop = (String) dataMap.get("depStn");
								String arrStop = (String) dataMap.get("arrStn");
								if(arrStop==null || depStop==null){
									logger.error("Ignore message, Has null stop! {}-{}",new Object[]{depStop,arrStop});
								}else{
									if(depStop.equalsIgnoreCase(TelexConstant.default_baseAirportIATA) || arrStop.equalsIgnoreCase(TelexConstant.default_baseAirportIATA)){
										requestScheduleForComFlightOperator.process(recordeJson);
									}else{
										logger.warn("Ignore message, No baseAirport! {}-{}",new Object[]{depStop,arrStop});
									}
								}
								
								
							}
						}
						
						huScheduleRequestParameter.setSpValue1("S");
						
						huScheduleRequestParameter.setSpValue4("request success.");
						huScheduleRequestParameter.setUpdateUser("IMG");
						huScheduleRequestParameter.setUpdateDateTime(new Date());
						requestScheduleForComFlightOperator.updateHUScheduleParameter(huScheduleRequestParameter);
					}else{
						logger.error("The request is failed, request startdatetime or enddatetime is in wrong format.");
						huScheduleRequestParameter.setSpValue1("E");
						huScheduleRequestParameter.setSpValue4("The request is failed, request startdatetime or enddatetime is in wrong format.");
						huScheduleRequestParameter.setUpdateUser("IMG");
						huScheduleRequestParameter.setUpdateDateTime(new Date());
						requestScheduleForComFlightOperator.updateHUScheduleParameter(huScheduleRequestParameter);
					}
					
				}
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
		}
		
	}
      public void initRequest(){
    	  logger.info("init requestSchedule instance begin......");
    	  Pattern pattern = Pattern.compile("[-?|+?]{0,1}\\d+,\\d+,\\d+,\\d+");

		  Matcher matcherStart = pattern.matcher(scheduleStartDateTime);
          if(!matcherStart.matches()){
  			logger.error("The format of scheduleStartDateTime:{} is invalid!",scheduleStartDateTime);
  			logger.error("System exit!");
  			System.exit(0);
          }
		  Matcher matcherEnd = pattern.matcher(scheduleEndDateTime);
		  if(!matcherEnd.matches()){
	  			logger.error("The format of scheduleEndDateTime:{} is invalid!",scheduleEndDateTime);
	  			logger.error("System exit!");
	  			System.exit(0);
		  }
		  logger.info("init requestSchedule instance end......");
      }
      
      public boolean matcheHUTimeFormat(String datetimeString){
    	  Pattern huPattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
    	  Matcher matcher = huPattern.matcher(datetimeString);
    	  
    	  return matcher.matches();
      }
	public FocFltInfoApi getFocFltInfoApi() {
		return focFltInfoApi;
	}


	public void setFocFltInfoApi(FocFltInfoApi focFltInfoApi) {
		this.focFltInfoApi = focFltInfoApi;
	}

	public String getScheduleStartDateTime() {
		return scheduleStartDateTime;
	}

	public void setScheduleStartDateTime(String scheduleStartDateTime) {
		this.scheduleStartDateTime = scheduleStartDateTime;
	}

	public String getScheduleEndDateTime() {
		return scheduleEndDateTime;
	}

	public void setScheduleEndDateTime(String scheduleEndDateTime) {
		this.scheduleEndDateTime = scheduleEndDateTime;
	}
	
}
