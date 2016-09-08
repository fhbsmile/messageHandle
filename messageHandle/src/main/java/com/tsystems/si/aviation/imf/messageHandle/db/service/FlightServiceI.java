package com.tsystems.si.aviation.imf.messageHandle.db.service;

import java.util.List;
import java.util.Map;

import com.tsystems.si.aviation.imf.messageHandle.db.bean.Flight;



public interface  FlightServiceI {
   
	public void saveOrUpdate(Flight flight);
	
	public void delete(Flight flight);
	
	 public List<Flight> getFlightByCallsign(Map<String, Object> params);
     public List<Flight> getFlightByFlightNumberWithDateTimePeriod(Map<String, Object> params);
     public List<Flight> getFlightByFlightNumberWithDate(Map<String, Object> params);
     public List<Flight> getFlightByThreePrimaryKeys(Map<String, Object> params);
}
