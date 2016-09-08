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
import com.tsystems.si.aviation.imf.messageHandle.db.bean.Flight;
import com.tsystems.si.aviation.imf.messageHandle.db.bean.SysParameter;
import com.tsystems.si.aviation.imf.messageHandle.db.dao.SysParameterDaoI;
import com.tsystems.si.aviation.imf.messageHandle.db.service.SysParameterServiceI;



@Service
public class SysParameterServiceImpl implements SysParameterServiceI {
	private static final Logger     logger               = LoggerFactory.getLogger(SysParameterServiceImpl.class);
	@Autowired
    private SysParameterDaoI sysParameterDao;
	@Override
	public void saveOrUpdate(SysParameter sysParameter) {
		// TODO Auto-generated method stub
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("spName", sysParameter.getSpName());

		String sql = "from SysParameter sp where sp.spName = :spName";
		List<SysParameter> sysParameters =sysParameterDao.find(sql, params);
		
		if(sysParameters.isEmpty()){
			logger.info("Can not find the SysParameter to update, insert new SysParameter......");
			sysParameterDao.save(sysParameter);
			logger.info("Insert new SysParameter success......");
		}else{
			logger.info("find the SysParameter to update, update old SysParameter......");
			SysParameter sysParameterQ = sysParameters.get(0);
			logger.info("Old sysParameter :{}",sysParameterQ.toString());
			logger.info("New sysParameter :{}",sysParameter.toString());
			String[] a = {"id","createDateTime"};
	        BeanUtils.copyProperties(sysParameter, sysParameterQ,a);
	        sysParameterDao.update(sysParameterQ);
	        logger.info("update old SysParameter success......");
		}
	}

	@Override
	public void deleteSysParameterByName(String spName) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("spName", spName);

		String sql = "from SysParameter sp where sp.spName = :spName";
		List<SysParameter> sysParameters =sysParameterDao.find(sql, params);
		if(sysParameters.isEmpty()){
			logger.error("Don't find SysParameter to delete!!!!");
		}else{
			logger.info("SysParameter is deleting........");
          for(SysParameter sysParameterQ: sysParameters){
        	  sysParameterDao.delete(sysParameterQ);
          }
          logger.info("SysParameter delete success........");
		}
	}

	@Override
	public SysParameter querySysParameterByName(String spName) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("spName", spName);
		String sql = "from SysParameter sp where sp.spName = :spName";
		SysParameter sysParameterQ =null;
		List<SysParameter> sysParameters =sysParameterDao.find(sql, params);
		if(sysParameters.isEmpty()){
			logger.error("Don't find sysParameter:{}",spName);
		}else{
			logger.info("Find sysParameter:{}",spName);
			 sysParameterQ = sysParameters.get(0);
            logger.info("sysParameter value:{}",JSON.toJSONString(sysParameterQ));
		}
		
		return sysParameterQ;
		
	}


}
