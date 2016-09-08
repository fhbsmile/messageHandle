package com.tsystems.si.aviation.imf.messageHandle.imfclient.fus;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tsystems.aviation.imf.client.commons.ImfServiceType;
import com.tsystems.aviation.imf.client.exception.ImfClientConnectionException;
import com.tsystems.aviation.imf.client.exception.ImfClientInvalidServiceTypeException;
import com.tsystems.aviation.imf.client.exception.ImfClientMessageValidationException;
import com.tsystems.aviation.imf.client.exception.ImfClientUsFailedException;
import com.tsystems.aviation.imf.client.exception.ImfServerUnknowException;
import com.tsystems.aviation.imf.client.subsystem.ImfUsClient;
import com.tsystems.si.aviation.imf.messageHandle.db.bean.ImfMessage;
import com.tsystems.si.aviation.imf.messageHandle.db.service.ImfMessageServiceI;


public class ImfFUSAction {
	private static final Logger     logger               = LoggerFactory.getLogger(ImfFUSAction.class);
	public ImfUsClient  usClient = null;
	@Autowired
	private ImfMessageServiceI imfMessageService;
	
	
	public void update(){
		if(usClient==null){
			try {
				logger.info("Init USClient.........");
				usClient = ImfUsClient.getImfUsClient(ImfServiceType.FUS);
				logger.info("Init USClient Success.........");			
			} catch (ImfClientConnectionException e) {
				// TODO Auto-generated catch block
				
				logger.error(e.getMessage(),e);
			} catch (ImfClientInvalidServiceTypeException e) {
				// TODO Auto-generated catch block
				
				logger.error(e.getMessage(),e);
			}
		}
		
		if(usClient!=null){
			List<ImfMessage> imfMessagesa = imfMessageService.findImfMessageByStatusAndOwer("A", "HU");
			logger.info("A Message Count:[{}]",new Object[]{imfMessagesa.size()});
			for(ImfMessage imfMessageA :imfMessagesa){
				String xmlMessagea  = imfMessageA.getXmlMessage();
				Integer id  = imfMessageA.getId();
				String status = imfMessageA.getStatus();
				String reason=null;
				String statusA="D";
				try {
					logger.info("UpdateXML,ID:[{}],Status:{}\n{}",new Object[]{id,status,xmlMessagea});
					usClient.update(xmlMessagea);
				} catch (ImfServerUnknowException e) {
					// TODO Auto-generated catch block
					 statusA="X";
					 reason = e.getMessage();
					logger.error(e.getMessage(),e);
				} catch (ImfClientMessageValidationException e) {
					// TODO Auto-generated catch block
					 statusA="X";
					 reason = e.getMessage();
					logger.error(e.getMessage(),e);
				} catch (ImfClientConnectionException e) {
					// TODO Auto-generated catch block
					 statusA="X";
					 reason = e.getMessage();
					logger.error(e.getMessage(),e);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					 statusA="X";
					 reason = e.getMessage();
					logger.error(e.getMessage(),e);
				} catch (ImfClientUsFailedException e) {
					// TODO Auto-generated catch block
					 statusA="X";
					 reason = e.getMessage();
					logger.error(e.getMessage(),e);
				}catch(Exception e){
					 statusA="X";
					 reason = e.getMessage();
					logger.error(e.getMessage(),e);
				}
				
				imfMessageA.setStatus(statusA);
				imfMessageA.setSendDateTime(new Date());
				imfMessageA.setReason(reason);
				imfMessageService.saveOrUpdate(imfMessageA);				
				logger.info("UpdateXML,ID:[{}] Success!,Status:{}",new Object[]{id,statusA});
		    }
			List<ImfMessage> imfMessagesp = imfMessageService.findImfMessageByStatusAndOwer("P", "HU");
			logger.info("P Message Count:[{}]",new Object[]{imfMessagesp.size()});
			for(ImfMessage imfMessageP :imfMessagesp){
				String xmlMessagep  = imfMessageP.getXmlMessage();
				String status = imfMessageP.getStatus();
				Integer id  = imfMessageP.getId();
				
				String reason=null;
				String statusP="PD";
				try {
					logger.info("UpdateXML,ID:[{}],Status:{}\n{}",new Object[]{id,status,xmlMessagep});
					usClient.update(xmlMessagep);
				} catch (ImfServerUnknowException e) {
					// TODO Auto-generated catch block
					 statusP="X";
					 reason = e.getMessage();
					logger.error(e.getMessage(),e);
				} catch (ImfClientMessageValidationException e) {
					// TODO Auto-generated catch block
					 statusP="X";
					 reason = e.getMessage();
					logger.error(e.getMessage(),e);
				} catch (ImfClientConnectionException e) {
					// TODO Auto-generated catch block
					 statusP="X";
					 reason = e.getMessage();
					logger.error(e.getMessage(),e);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					 statusP="X";
					 reason = e.getMessage();
					logger.error(e.getMessage(),e);
				} catch (ImfClientUsFailedException e) {
					// TODO Auto-generated catch block
					 statusP="X";
					 reason = e.getMessage();
					logger.error(e.getMessage(),e);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					 statusP="X";
					 reason = e.getMessage();
					logger.error(e.getMessage(),e);
				}
				
				imfMessageP.setStatus(statusP);
				imfMessageP.setSendDateTime(new Date());
				imfMessageP.setReason(reason);
				imfMessageService.saveOrUpdate(imfMessageP);
				logger.info("UpdateXML,ID:[{}] Success,Status:{}",new Object[]{id,status});
		   }
		

	}
}
}