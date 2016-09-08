package com.tsystems.si.aviation.imf.messageHandle.db.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsystems.si.aviation.imf.messageHandle.db.bean.OrgMessage;
import com.tsystems.si.aviation.imf.messageHandle.db.dao.OrgMessageDaoI;
import com.tsystems.si.aviation.imf.messageHandle.db.service.OrgMessageServiceI;


@Service
public class OrgMessageServiceImpl implements OrgMessageServiceI {
	private static final Logger     logger               = LoggerFactory.getLogger(OrgMessageServiceImpl.class);
	@Autowired
    private OrgMessageDaoI orgMessageDao;
	@Override
	public void saveOrUpdate(OrgMessage orgMessage) {
		// TODO Auto-generated method stub
		orgMessageDao.saveOrUpdate(orgMessage);
		}
	}



