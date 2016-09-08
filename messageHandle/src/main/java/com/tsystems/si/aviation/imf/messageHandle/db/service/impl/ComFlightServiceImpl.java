package com.tsystems.si.aviation.imf.messageHandle.db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tsystems.si.aviation.imf.messageHandle.db.bean.ComFlight;
import com.tsystems.si.aviation.imf.messageHandle.db.bean.Flight;
import com.tsystems.si.aviation.imf.messageHandle.db.dao.ComFlightDaoI;
import com.tsystems.si.aviation.imf.messageHandle.db.service.ComFlightServiceI;



@Service
public class ComFlightServiceImpl implements ComFlightServiceI {
	private static final Logger     logger               = LoggerFactory.getLogger(ComFlightServiceImpl.class);
	@Autowired
    private ComFlightDaoI comFlightDao;
	@Override
	public void saveOrUpdate(ComFlight comFlight) {
		// TODO Auto-generated method stub
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("flightNumber", comFlight.getFlightNumber());
		params.put("flightScheduledDate", comFlight.getFlightScheduledDate());
		params.put("flightDirection", comFlight.getFlightDirection());
		params.put("owner", comFlight.getOwner());
		String sql = "from ComFlight f where f.owner = :owner and f.flightNumber = :flightNumber and f.flightScheduledDate=:flightScheduledDate and f.flightDirection =:flightDirection";
		List<ComFlight> comFlights =comFlightDao.find(sql, params);
		
		if(comFlights.isEmpty()){
			logger.info("Can not find the ComFlight to update, insert new flight......");
			comFlightDao.save(comFlight);
			logger.info("Insert new ComFlight success......");
		}else{
			logger.info("find the ComFlight to update, update old ComFlight......");
			ComFlight flightQ = comFlights.get(0);
			logger.info("Old ComFlight :{}",JSON.toJSONString(flightQ));
			logger.info("New ComFlight :{}",JSON.toJSONString(comFlight));
			String[] a = {"id","createDateTime"};
	        BeanUtils.copyProperties(comFlight, flightQ,a);
	        comFlightDao.update(flightQ);
	        logger.info("update old ComFlight success......");
		}
	}

	@Override
	public void delete(ComFlight comFlight) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("flightNumber", comFlight.getFlightNumber());
		params.put("flightScheduledDate", comFlight.getFlightScheduledDate());
		params.put("flightDirection", comFlight.getFlightDirection());
		params.put("owner", comFlight.getOwner());
		String sql = "from ComFlight f where f.owner = :owner and f.flightNumber = :flightNumber and f.flightScheduledDate=:flightScheduledDate and f.flightDirection =:flightDirection";
		List<ComFlight> comFlights =comFlightDao.find(sql, params);
		if(comFlights.isEmpty()){
			logger.error("Can not find the comflight to delete!!!! Ingnore delete message!");
		}else{
			logger.info("ComFlight is deleting........");
          for(ComFlight flightQ: comFlights){
        	  comFlightDao.delete(flightQ);
          }
          logger.info("ComFlight delete success........");
		}
	}

	@Override
	public int deleteComFlightByOwner(String owner) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("owner", owner);
		String sql = "delete from ComFlight f where f.owner = :owner ";
		int n =comFlightDao.executeHql(sql, params);
		logger.info("{} ComFlights deleted.",n);
		return n;
	}



}
