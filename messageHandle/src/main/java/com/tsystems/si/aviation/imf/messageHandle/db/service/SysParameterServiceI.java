package com.tsystems.si.aviation.imf.messageHandle.db.service;

import com.tsystems.si.aviation.imf.messageHandle.db.bean.SysParameter;



public interface  SysParameterServiceI {
   
	public void saveOrUpdate(SysParameter sysParameter);
	
	public void deleteSysParameterByName(String spName);
	
	public SysParameter querySysParameterByName(String spName);

}
