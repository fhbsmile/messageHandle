package com.tsystems.si.aviation.imf.messageHandle.db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsystems.si.aviation.imf.messageHandle.db.bean.RefAirport;
import com.tsystems.si.aviation.imf.messageHandle.db.dao.RefAirportDaoI;
import com.tsystems.si.aviation.imf.messageHandle.db.service.RefAirportServiceI;


@Service
public class RefAirportServiceImpl implements RefAirportServiceI {
	private static final Logger     logger               = LoggerFactory.getLogger(RefAirportServiceImpl.class);
	@Autowired
    private RefAirportDaoI refAirportDao;
	@Override
	public void saveOrUpdate(RefAirport refAirport) {
		// TODO Auto-generated method stub
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("airportIatacode", refAirport.getAirportIatacode());
		params.put("airportIcaocode", refAirport.getAirportIcaocode());
		String sql = "from RefAirport f where f.airportIatacode = :airportIatacode and f.airportIcaocode=:airportIcaocode";
		List<RefAirport> refAirports =refAirportDao.find(sql, params);
		
		if(refAirports.isEmpty()){
			logger.info("Can not find the RefAirport to update, insert new RefAirport......");
			refAirportDao.save(refAirport);
			logger.info("Insert new RefAirport success......");
		}else{
			logger.info("find the RefAirport to update, update old RefAirport......");
			RefAirport refAirportQ = refAirports.get(0);
			logger.info("Old RefAirport :{}",refAirportQ.toString());
			logger.info("New RefAirport :{}",refAirport.toString());
			String[] a = {"id","createtime"};
	        BeanUtils.copyProperties(refAirport, refAirportQ,a);
	        refAirportDao.update(refAirportQ);
	        logger.info("update old RefAirport success......");
		}
	}

	@Override
	public void delete(RefAirport refAirport) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("airportIatacode", refAirport.getAirportIatacode());
		params.put("airportIcaocode", refAirport.getAirportIcaocode());
		String sql = "from RefAirport f where f.airportIatacode = :airportIatacode and f.airportIcaocode=:airportIcaocode";
		List<RefAirport> refAirports =refAirportDao.find(sql, params);
		if(refAirports.isEmpty()){
			logger.error("Can not find the RefAirport to delete!!!! Ingnore delete message!");
		}else{
			logger.info("RefAirport is deleting........");
          for(RefAirport refAirportQ: refAirports){
        	  refAirportDao.delete(refAirportQ);
          }
          logger.info("RefAirport delete success........");
		}
	}


	@Override
	public int getFlyTimeByAirportICAO(String airportIcaocode) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("airportIcaocode", airportIcaocode);
		String sql = "from RefAirport f where f.airportIcaocode = :airportIcaocode order by f.airportIcaocode asc";
		List<RefAirport> airports = refAirportDao.find(sql, params);
		int flyTime=0;
		if(!airports.isEmpty()){
			RefAirport airport = airports.get(0);
			flyTime = airport.getAirportDistance();
		}
		return flyTime;
	}

	@Override
	public int getFlyTimeByAirportIATA(String airportIatacode) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("airportIatacode", airportIatacode);
		String sql = "from RefAirport f where f.airportIatacode = :airportIatacode order by f.airportIatacode asc";
		List<RefAirport> airports = refAirportDao.find(sql, params);
		int flyTime=0;
		if(!airports.isEmpty()){
			RefAirport airport = airports.get(0);
			flyTime = airport.getAirportDistance();
		}
		return flyTime;
	}

	@Override
	public String getAirportIATAByICAO(String airportIcaocode) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("airportIcaocode", airportIcaocode);
		String sql = "from RefAirport f where f.airportIcaocode = :airportIcaocode order by f.airportIcaocode asc";
		List<RefAirport> airports = refAirportDao.find(sql, params);
		String airportIATA =null;
		if(!airports.isEmpty()){
			RefAirport airport = airports.get(0);
			airportIATA = airport.getAirportIatacode();
		}
		return airportIATA;
	}
}
