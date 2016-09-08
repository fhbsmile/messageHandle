package com.tsystems.si.aviation.imf.messageHandle.db.service;

import com.tsystems.si.aviation.imf.messageHandle.db.bean.ComFlight;



public interface  ComFlightServiceI {
   
	public void saveOrUpdate(ComFlight comFlight);
	
	public void delete(ComFlight comFlight);
	public int deleteComFlightByOwner(String owner);
	
}
