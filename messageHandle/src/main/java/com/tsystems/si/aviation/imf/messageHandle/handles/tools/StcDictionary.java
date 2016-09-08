package com.tsystems.si.aviation.imf.messageHandle.handles.tools;

import java.util.HashMap;

public class StcDictionary
{
  private HashMap<String, String> stcMap = new HashMap();


  public HashMap<String, String> getStcMap() {
	return stcMap;
}


public void setStcMap(HashMap<String, String> stcMap) {
	this.stcMap = stcMap;
}


public String getCAACStcByIATA(String iata) {
    return (String)this.stcMap.get(iata);
  }
}