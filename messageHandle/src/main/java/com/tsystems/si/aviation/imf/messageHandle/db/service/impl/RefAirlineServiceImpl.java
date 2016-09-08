package com.tsystems.si.aviation.imf.messageHandle.db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsystems.si.aviation.imf.messageHandle.db.bean.RefAirline;
import com.tsystems.si.aviation.imf.messageHandle.db.dao.RefAirlineDaoI;
import com.tsystems.si.aviation.imf.messageHandle.db.service.RefAirlineServiceI;


@Service
public class RefAirlineServiceImpl implements RefAirlineServiceI {
	private static final Logger     logger               = LoggerFactory.getLogger(RefAirlineServiceImpl.class);
	@Autowired
    private RefAirlineDaoI refAirlineDao;
	@Override
	public void saveOrUpdate(RefAirline refAirline) {
		// TODO Auto-generated method stub
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("airlineIatacode", refAirline.getAirlineIatacode());
		params.put("airlineIcaocode", refAirline.getAirlineIcaocode());
		String sql = "from RefAirline f where f.airlineIatacode = :airlineIatacode and f.airlineIcaocode=:airlineIcaocode";
		List<RefAirline> refAirlines =refAirlineDao.find(sql, params);
		
		if(refAirlines.isEmpty()){
			logger.info("Can not find the RefAirline to update, insert new RefAirline......");
			refAirlineDao.save(refAirline);
			logger.info("Insert new RefAirline success......");
		}else{
			logger.info("find the RefAirline to update, update old RefAirline......");
			RefAirline refAirlineQ = refAirlines.get(0);
			logger.info("Old RefAirline :{}",refAirlineQ.toString());
			logger.info("New RefAirline :{}",refAirline.toString());
			String[] a = {"id","createDatetime"};
	        BeanUtils.copyProperties(refAirline, refAirlineQ,a);
	        refAirlineDao.update(refAirlineQ);
	        logger.info("update old RefAirline success......");
		}
	}

	@Override
	public void delete(RefAirline refAirline) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("airlineIatacode", refAirline.getAirlineIatacode());
		params.put("airlineIcaocode", refAirline.getAirlineIcaocode());
		String sql = "from RefAirline f where f.airlineIataCode = :airlineIatacode and f.airlineIcaocode=:airlineIcaocode";
		List<RefAirline> refAirlines =refAirlineDao.find(sql, params);
		if(refAirlines.isEmpty()){
			logger.error("Can not find the RefAirline to delete!!!! Ingnore delete message!");
		}else{
			logger.info("RefAirline is deleting........");
          for(RefAirline refAirlineQ: refAirlines){
        	  refAirlineDao.delete(refAirlineQ);
          }
          logger.info("RefAirline delete success........");
		}
	}
	public String getIATAByICAO(String airlineIcaocode) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("airlineIcaocode", airlineIcaocode);
		String sql = "from RefAirline a where a.airlineIcaocode = :airlineIcaocode and airlineIatacode !=null and airlineIatacode !=''order by a.airlineIcaocode asc";
		List<RefAirline> airlines = refAirlineDao.find(sql, params);
		if(!airlines.isEmpty()){
			RefAirline airline = airlines.get(0);
			
			return airline.getAirlineIatacode();
		}
		
		return null;
	}
}
