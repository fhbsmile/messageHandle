package com.tsystems.si.aviation.imf.messageHandle.handles.tools;

import java.util.HashMap;

public class AirportDictionary
{
  private HashMap<String, String> airportMap = new HashMap();



  public HashMap<String, String> getAirportMap() {
	return airportMap;
}



public void setAirportMap(HashMap<String, String> airportMap) {
	this.airportMap = airportMap;
}



public String getNewIATAByIATA(String iata) {
	  String res = this.airportMap.get(iata);
	  if(res==null){
		  res =iata;
	  }
    return res;
  }

public String getNewIATARouting(String orgRouting){
	  String newRouting = "";
	  String[] r=orgRouting.split("-");
	  for(int i=0;i<r.length;i++){
		  String rn =getNewIATAByIATA(r[i]);
		  newRouting=newRouting+"-"+rn;
	  }
	  newRouting= newRouting.replaceFirst("-", "");
	  
      return newRouting;
}
}