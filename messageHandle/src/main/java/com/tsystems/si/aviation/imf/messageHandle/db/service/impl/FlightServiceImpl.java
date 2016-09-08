package com.tsystems.si.aviation.imf.messageHandle.db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsystems.si.aviation.imf.messageHandle.db.bean.Flight;
import com.tsystems.si.aviation.imf.messageHandle.db.dao.FlightDaoI;
import com.tsystems.si.aviation.imf.messageHandle.db.service.FlightServiceI;



@Service
public class FlightServiceImpl implements FlightServiceI {
	private static final Logger     logger               = LoggerFactory.getLogger(FlightServiceImpl.class);
	@Autowired
    private FlightDaoI flightDao;
	@Override
	public void saveOrUpdate(Flight flight) {
		// TODO Auto-generated method stub
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("flightNumber", flight.getFlightNumber());
		params.put("flightScheduledDate", flight.getFlightScheduledDate());
		params.put("flightDirection", flight.getFlightDirection());
		String sql = "from Flight f where f.flightNumber = :flightNumber and f.flightScheduledDate=:flightScheduledDate and f.flightDirection =:flightDirection";
		List<Flight> flights =flightDao.find(sql, params);
		
		if(flights.isEmpty()){
			logger.info("Can not find the flight to update, insert new flight......");
			flightDao.save(flight);
			logger.info("Insert new flight success......");
		}else{
			logger.info("find the flight to update, update old flight......");
			Flight flightQ = flights.get(0);
			logger.info("Old flight :{}",flightQ.toString());
			logger.info("New flight :{}",flight.toString());
			String[] a = {"id","createDateTime"};
	        BeanUtils.copyProperties(flight, flightQ,a);
	        flightDao.update(flightQ);
	        logger.info("update old flight success......");
		}
	}

	@Override
	public void delete(Flight flight) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("flightNumber", flight.getFlightNumber());
		params.put("flightScheduledDate", flight.getFlightScheduledDate());
		params.put("flightDirection", flight.getFlightDirection());
		String sql = "from Flight f where f.flightNumber = :flightNumber and f.flightScheduledDate=:flightScheduledDate and f.flightDirection =:flightDirection";
		List<Flight> flights =flightDao.find(sql, params);
		if(flights.isEmpty()){
			logger.error("Can not find the flight to delete!!!! Ingnore delete message!");
		}else{
			logger.info("Flight is deleting........");
          for(Flight flightQ: flights){
        	  flightDao.delete(flightQ);
          }
          logger.info("Flight delete success........");
		}
	}

	@Override
	public List<Flight> getFlightByCallsign(Map<String, Object> params) {
		// TODO Auto-generated method stub
		String sql = "from Flight f where f.callSign = :callsign and f.flightDirection = :direction and f.flightScheduledDateTime > :beforeDateTime and f.flightScheduledDateTime < :afterDateTime order by f.flightScheduledDateTime asc";
		List<Flight> flights= flightDao.find(sql, params);
		return flights;
	}

	@Override
	public List<Flight> getFlightByFlightNumberWithDateTimePeriod(Map<String, Object> params) {
		// TODO Auto-generated method stub
		String sql = "from Flight f where f.flightNumber = :flightNumber and f.flightDirection = :direction and f.flightScheduledDateTime > :beforeDateTime and f.flightScheduledDateTime < :afterDateTime order by f.flightScheduledDateTime asc";
		List<Flight> flights= flightDao.find(sql, params);
		return flights;
	}
	@Override
	public List<Flight> getFlightByFlightNumberWithDate(Map<String, Object> params) {
		// TODO Auto-generated method stub
		String sql = "from Flight f where f.flightNumber = :flightNumber and f.flightDirection = :direction and f.flightScheduledDate = :flightScheduledDate order by f.flightScheduledDateTime asc";
		List<Flight> flights= flightDao.find(sql, params);
		return flights;
	}
	@Override
	public List<Flight> getFlightByThreePrimaryKeys(Map<String, Object> params) {
		// TODO Auto-generated method stub
		String sql = "from Flight f where f.flightNumber = :flightNumber and f.flightDirection = :direction and f.flightScheduledDate=:flightScheduledDate order by f.flightScheduledDateTime asc";
		List<Flight> flights= flightDao.find(sql, params);
		return flights;
	}

}
