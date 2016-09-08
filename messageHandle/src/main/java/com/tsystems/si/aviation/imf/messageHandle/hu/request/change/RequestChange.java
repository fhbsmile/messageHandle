/** 
 * Project Name:huMessageHandle 
 * File Name:RequestChange.java 
 * Package Name:com.tsystems.si.aviation.imf.huMessageHandle.request 
 * Date:2016年5月10日下午11:16:47
 * version:v1.0
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */ 


package com.tsystems.si.aviation.imf.messageHandle.hu.request.change;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hnair.opcnet.api.complextype.PageParam;
import com.hnair.opcnet.api.complextype.Result;
import com.hnair.opcnet.api.ods.foc.FindFltLegsChangesDetailRequest;
import com.hnair.opcnet.api.ods.foc.FindFltLegsChangesDetailResponse;
import com.hnair.opcnet.api.ods.foc.FltLegsChangeApi;
import com.hnair.opcnet.api.ods.foc.FltLegsChangeDetail;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.TelexConstant;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.TimeHandle;

public class RequestChange {
	private static final Logger     logger               = LoggerFactory.getLogger(RequestChange.class);
	private FltLegsChangeApi fltLegsChangeApi;
	@Autowired
	private RequestChangeMessageOperator requestChangeMessageOperator;
    int intiMiniteOffset = -60;
    String requestSubTimeFrom =null;
    String requestSubTimeTo =null;
    //String requestFlightDate ="2016-05-11";
	
	public void request(){
		Date now = new Date();
		String requestStartString = JSON.toJSONString(now, SerializerFeature.WriteDateUseDateFormat);
		logger.info(">>>>>>>>>Request Satrt Date Time:{}",requestStartString);
		if(requestSubTimeFrom==null){
			Date calFrom = TimeHandle.dateAddMinutes(now, intiMiniteOffset);
			requestSubTimeFrom = JSON.toJSONString(calFrom, SerializerFeature.WriteDateUseDateFormat).replace("\"", "");
			logger.info("requestSubTimeFrom is null, calculated value is :{}",requestSubTimeFrom);
		}
		requestSubTimeTo = JSON.toJSONString(now, SerializerFeature.WriteDateUseDateFormat).replace("\"", "");
		logger.info("Request change period is :{}-{}",new Object[]{requestSubTimeFrom,requestSubTimeTo});
		FindFltLegsChangesDetailRequest findFltLegsChangesDetailRequest = new FindFltLegsChangesDetailRequest();
		//findFltLegsChangesDetailRequest.setFltDate(requestFlightDate);
		
		findFltLegsChangesDetailRequest.setSubTimeFrom(requestSubTimeFrom);
		findFltLegsChangesDetailRequest.setSubTimeTo(requestSubTimeTo);
		
		//findFltLegsChangesDetailRequest.setSubTimeFrom(requestSubTimeFrom);
		//findFltLegsChangesDetailRequest.setSubTimeTo(requestSubTimeTo);
		PageParam pageParam = new PageParam();
		pageParam.setPageSize(500);

		findFltLegsChangesDetailRequest.setPageParam(pageParam);
		
		FindFltLegsChangesDetailResponse findFltLegsChangesDetailResponse =fltLegsChangeApi.findFltLegsChangesDetail(findFltLegsChangesDetailRequest);
		Result requestChangeResult = findFltLegsChangesDetailResponse.getResult();
		int resultCode = requestChangeResult.getResultCode();
		String resultString = requestChangeResult.getResultMsg();
		logger.info("Request result:{}",JSON.toJSONString(requestChangeResult,true));
		List<FltLegsChangeDetail> flightLegsChangeDetailList = findFltLegsChangesDetailResponse.getFltLegsChangeDetail();
		int cnt = flightLegsChangeDetailList.size();
			if(cnt>0){
				logger.info("Received {} records.",cnt);
				for(int i=0;i<cnt;i++){
					FltLegsChangeDetail flcd = flightLegsChangeDetailList.get(i);
					String fltLegsChangeDetailJsonString = JSON.toJSONString(flcd);
					String arrStop = flcd.getArrStn();
					String depStop = flcd.getDepStn();					
					logger.info("message number {}:{}",new Object[]{i,fltLegsChangeDetailJsonString});
					logger.info("depStop:{}, arrStop:{}",new Object[]{depStop,arrStop});
					if(arrStop==null || depStop==null){
						logger.error("Ignore message, Has null stop! {}-{}",new Object[]{depStop,arrStop});
					}else{
						if(arrStop.equalsIgnoreCase(TelexConstant.default_baseAirportIATA) || depStop.equalsIgnoreCase(TelexConstant.default_baseAirportIATA)){
							requestChangeMessageOperator.process(fltLegsChangeDetailJsonString);
						}else{
							logger.warn("Ignore message, No baseAirport! {}-{}",new Object[]{depStop,arrStop});
						}
					}
				}
			}else{
				logger.info("No changes received!");
			}
			Date calFromT = TimeHandle.dateAddMinutes(now, -1);
			requestSubTimeFrom = JSON.toJSONString(calFromT, SerializerFeature.WriteDateUseDateFormat).replace("\"", "");
	}

	public FltLegsChangeApi getFltLegsChangeApi() {
		return fltLegsChangeApi;
	}

	public void setFltLegsChangeApi(FltLegsChangeApi fltLegsChangeApi) {
		this.fltLegsChangeApi = fltLegsChangeApi;
	}

	public RequestChangeMessageOperator getRequestChangeMessageOperator() {
		return requestChangeMessageOperator;
	}

	public void setRequestChangeMessageOperator(
			RequestChangeMessageOperator requestChangeMessageOperator) {
		this.requestChangeMessageOperator = requestChangeMessageOperator;
	}





	public int getIntiMiniteOffset() {
		return intiMiniteOffset;
	}

	public void setIntiMiniteOffset(int intiMiniteOffset) {
		this.intiMiniteOffset = intiMiniteOffset;
	}


	public void getSubfromString(){
		
	}
	
}
