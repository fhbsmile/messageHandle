package com.tsystems.si.aviation.imf.messageHandle.db.service.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsystems.si.aviation.imf.messageHandle.db.bean.ImfMessage;
import com.tsystems.si.aviation.imf.messageHandle.db.dao.ImfMessageDaoI;
import com.tsystems.si.aviation.imf.messageHandle.db.service.ImfMessageServiceI;


@Service
public class ImfMessageServiceImpl implements ImfMessageServiceI {
	private static final Logger     logger               = LoggerFactory.getLogger(ImfMessageServiceImpl.class);
	@Autowired
    private ImfMessageDaoI ImfMessageDao;
	@Override
	public void saveOrUpdate(ImfMessage imfMessage) {
		// TODO Auto-generated method stub
		ImfMessageDao.saveOrUpdate(imfMessage);
		}
	@Override
	public List<ImfMessage> findImfMessageByStatus(String status) {
		// TODO Auto-generated method stub
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("status", status);
		String sql = "from ImfMessage f where f.status = :status order by f.id asc";
		List<ImfMessage> imfMessages= ImfMessageDao.find(sql, params);
		return imfMessages;
	}
	@Override
	public List<ImfMessage> findImfMessageByStatusAndOwer(String status, String owner) {
		// TODO Auto-generated method stub
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("status", status);
		params.put("owner", owner);
		String sql = "from ImfMessage f where f.status = :status and f.owner = :owner order by f.id asc";
		List<ImfMessage> imfMessages= ImfMessageDao.find(sql, params);
		return imfMessages;
	}
	
	
	}



