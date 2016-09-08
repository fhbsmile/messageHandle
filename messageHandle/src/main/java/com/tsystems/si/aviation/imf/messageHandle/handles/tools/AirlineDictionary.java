package com.tsystems.si.aviation.imf.messageHandle.handles.tools;

import java.util.HashMap;

public class AirlineDictionary
{
  private HashMap<String, String> airlineMap = new HashMap();

  public HashMap<String, String> getAirlineMap() {
    return this.airlineMap;
  }

  public void setAirlineMap(HashMap<String, String> airlineMap) {
    this.airlineMap = airlineMap;
  }

  public String getIATAByICAO(String icao) {
	  String res = this.airlineMap.get(icao);
	  if(res==null){
		  res ="W/Z";
	  }
    return res;
  }
}