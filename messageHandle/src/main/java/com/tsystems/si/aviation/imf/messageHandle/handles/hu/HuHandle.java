/** 
 * Project Name:messageHandle 
 * File Name:HuHandle.java 
 * Package Name:com.tsystems.si.aviation.imf.messageHandle.handles.hu 
 * Date:2016年5月13日上午10:09:21
 * version:v1.0
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */ 


/** 
 * Project Name:messageHandle 
 * File Name:HuHandle.java 
 * Package Name:com.tsystems.si.aviation.imf.messageHandle.handles.hu 
 * Date:2016年5月13日上午10:09:21 
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */  
package com.tsystems.si.aviation.imf.messageHandle.handles.hu;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.configuration.ConfigurationUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tsystems.si.aviation.imf.messageHandle.db.bean.ImfMessage;
import com.tsystems.si.aviation.imf.messageHandle.handles.Handle;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.FlightXmlBean;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.SequenceGenerator;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.TelexConstant;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.TimeHandle;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.XMLHandle;

/**   
 * @ClassName:  HuHandle   
 * @Description:TODO
 * @author: Bolo.Fang@t-systems.com  
 * @date:   2016年5月13日 上午10:09:21   
 *      
 */
public class HuHandle implements Handle {
	private static final Logger     logger               = LoggerFactory.getLogger(HuHandle.class);
	public  static SequenceGenerator sequenceGenerator = new SequenceGenerator();
	protected String handleName;
	protected int beforeMin = TelexConstant.default_beforeMin;
	protected int afterMin = TelexConstant.default_afterMin;
	protected int defaultFlyMins = TelexConstant.default_flyMins;
	protected String statusNew = TelexConstant.default_statusNew_hu;
	protected String statusMod = TelexConstant.default_statusMod_hu;
	protected String statusCnl = TelexConstant.default_statusCnl_hu;
	protected String statusDel = TelexConstant.default_statusDel_hu;
	protected String statusDly = TelexConstant.default_statusDly_hu;
	protected String statusA = "A";
	protected String statusN = "N";
	protected String createReason = TelexConstant.default_createReason_hu;
    protected String baseAirportICAO = TelexConstant.default_baseAirportICAO;
    protected String baseAirportIATA = TelexConstant.default_baseAirportIATA;
	protected String  templateXMLName = TelexConstant.default_templateXMLName;
	protected String  filterXSLName = TelexConstant.default_filterXSLName;
	protected String  handleMode = 	TelexConstant.default_handleMode_hu;
	protected String  templateXML;
	protected String  filterXSL;
	protected Transformer transformer;
	
	
	/* (non-Javadoc)
	 * @see com.tsystems.si.aviation.imf.messageHandle.handles.Handle#initHandle()
	 */
	@Override
	public void initHandle() {
		// TODO Auto-generated method stub
		logger.info("{} >>>>>>>>>> init begin.......",this.handleName);
		URL urlTemplateXML = ConfigurationUtils.locate(this.templateXMLName);
		try {
			
			this.templateXML = IOUtils.toString(urlTemplateXML, "utf-8");
			logger.info("{} ..... Loading XML Template [{}] Success!",new Object[]{this.handleName,this.templateXMLName});
			logger.info("\n{}",this.templateXML);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("{} ..... Loading XML Template [{}]  Failed!!!,Please check the XML exist or not!",new Object[]{this.handleName,this.templateXMLName});
			logger.error("System exit!!");
			logger.error(e.getMessage(), e);
			System.exit(0);
		}
			if(this.filterXSLName!=null){
				
				URL urlFilterXSL = ConfigurationUtils.locate(this.filterXSLName);
				try {
					
					this.filterXSL = IOUtils.toString(urlFilterXSL, "utf-8");
					logger.info("{} ..... Loading Filter XSL [{}] Success!",new Object[]{this.handleName,this.filterXSLName});
					logger.info("\n{}",this.filterXSL);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("{} ..... Loading Filter XSL [{}] Failed!!!,Please check the XSL exist or not!",new Object[]{this.handleName,this.filterXSLName});
					logger.error("System exit!!");
					logger.error(e.getMessage(), e);
					System.exit(0);
				}
				
				Source xsl = new StreamSource(new StringReader(this.filterXSL));
				try {
					this.transformer = TransformerFactory.newInstance().newTransformer(xsl);
					this.transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				} catch (TransformerConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.exit(0);
				} catch (TransformerFactoryConfigurationError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.exit(0);
				}
		}else{
			logger.warn("{} ..... NO Filter XSL defind!!!, Generated XML wouldn't be transformed!",this.handleName);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.tsystems.si.aviation.imf.messageHandle.handles.Handle#createImfMessage(com.tsystems.si.aviation.imf.messageHandle.handles.tools.FlightXmlBean)
	 */
	@Override
	public ImfMessage createImfMessage(FlightXmlBean fxbean) {
		// TODO Auto-generated method stub
		 ImfMessage imfMessage= new ImfMessage();
			HashMap<String, String> attrabuteForXMLMap = new HashMap<String,String>();
			attrabuteForXMLMap.put("MessageSequenceID", fxbean.getMessageSequenceID());
			attrabuteForXMLMap.put("OperationMode", fxbean.getOperationMode());
			attrabuteForXMLMap.put("SendDateTime", TimeHandle.getDateTimeWithTString(fxbean.getSendDateTime()));
			attrabuteForXMLMap.put("CreateDateTime", TimeHandle.getDateTimeWithTString(fxbean.getCreateDateTime()));
			attrabuteForXMLMap.put("OriginalDateTime", TimeHandle.getDateTimeWithTString(fxbean.getOriginalDateTime()));
			attrabuteForXMLMap.put("FlightIdentity", fxbean.getFlightIdentity());
			attrabuteForXMLMap.put("CreateReason", fxbean.getCreateReason());
			attrabuteForXMLMap.put("FlightDirection", fxbean.getFlightDirection());
			attrabuteForXMLMap.put("FlightServiceType", fxbean.getFlightServiceType());
			attrabuteForXMLMap.put("Registration", fxbean.getRegistration());
			attrabuteForXMLMap.put("FlightScheduledDate", TimeHandle.getDateString(fxbean.getFlightScheduledDate()));
			attrabuteForXMLMap.put("FlightScheduledDateTime", TimeHandle.getDateTimeWithTString(fxbean.getFlightScheduledDateTime()));
			attrabuteForXMLMap.put("ICAOOriginAirport", fxbean.getIcaoriginAirport());
			attrabuteForXMLMap.put("ICAOPreviousAirport", fxbean.getIcaopreviousAirport());
			attrabuteForXMLMap.put("ICAOViaAirport", fxbean.getIcaoviaAirport());
			attrabuteForXMLMap.put("ICAONextAirport", fxbean.getIcaonextAirport());
			attrabuteForXMLMap.put("ICAODestinationAirport", fxbean.getIcaodestinationAirport());
			attrabuteForXMLMap.put("IATAOriginAirport", fxbean.getIataoriginAirport());
			attrabuteForXMLMap.put("IATAPreviousAirport", fxbean.getIatapreviousAirport());
			attrabuteForXMLMap.put("IATAViaAirport", fxbean.getIataviaAirport());
			attrabuteForXMLMap.put("IATANextAirport", fxbean.getIatanextAirport());
			attrabuteForXMLMap.put("IATADestinationAirport", fxbean.getIatadestinationAirport());
			
			attrabuteForXMLMap.put("AirportIATACodeLeg1", fxbean.getAirportIATACodeLeg1());
			attrabuteForXMLMap.put("AirportIATACodeLeg2", fxbean.getAirportIATACodeLeg2());
			attrabuteForXMLMap.put("AirportIATACodeLeg3", fxbean.getAirportIATACodeLeg3());
			attrabuteForXMLMap.put("AirportIATACodeLeg4", fxbean.getAirportIATACodeLeg4());
			attrabuteForXMLMap.put("ScheduledPreviousAirportDepartureDateTime", TimeHandle.getDateTimeWithTString(fxbean.getScheduledPreviousAirportDepartureDateTime()));
			attrabuteForXMLMap.put("ActualPreviousAirportDepartureDateTime", TimeHandle.getDateTimeWithTString(fxbean.getActualPreviousAirportDepartureDateTime()));
			attrabuteForXMLMap.put("EstimatedPreviousAirportDepartureDateTime", TimeHandle.getDateTimeWithTString(fxbean.getEstimatedPreviousAirportDepartureDateTime()));
			
			attrabuteForXMLMap.put("ActualLandingDateTime", TimeHandle.getDateTimeWithTString(fxbean.getActualLandingDateTime()));
			attrabuteForXMLMap.put("EstimatedLandingDateTime", TimeHandle.getDateTimeWithTString(fxbean.getEstimatedLandingDateTime()));
			attrabuteForXMLMap.put("ActualOnBlockDateTime", TimeHandle.getDateTimeWithTString(fxbean.getActualOnBlockDateTime()));
			attrabuteForXMLMap.put("ActualOnBridgeDateTime", TimeHandle.getDateTimeWithTString(fxbean.getActualOnBridgeDateTime()));
			attrabuteForXMLMap.put("ActualDoorOpenDateTime", TimeHandle.getDateTimeWithTString(fxbean.getActualDoorOpenDateTime()));
			int ftimeInt = fxbean.getEstimatedFlyTime();
			String ftimeString =null;
			if(ftimeInt==0){
				
			}else{
				ftimeString = ftimeInt+"";
			}
/*			if(fxbean.getEstimatedNextAirportArrivalDateTime()!=null){
				//对于离港来说没有飞行时间
			}else{
				attrabuteForXMLMap.put("EstimatedFlyTime", ftimeString);
			}*/
			attrabuteForXMLMap.put("ActualOffBridgeDateTime", TimeHandle.getDateTimeWithTString(fxbean.getActualOffBridgeDateTime()));
			attrabuteForXMLMap.put("ActualDoorCloseDateTime", TimeHandle.getDateTimeWithTString(fxbean.getActualDoorCloseDateTime()));
			attrabuteForXMLMap.put("EstimatedFlyTime", ftimeString);
			attrabuteForXMLMap.put("ActualOffBlockDateTime", TimeHandle.getDateTimeWithTString(fxbean.getActualOffBlockDateTime()));
			attrabuteForXMLMap.put("ActualTakeOffDateTime", TimeHandle.getDateTimeWithTString(fxbean.getActualTakeOffDateTime()));
			attrabuteForXMLMap.put("EstimatedTakeOffDateTime", TimeHandle.getDateTimeWithTString(fxbean.getEstimatedTakeOffDateTime()));
			
			attrabuteForXMLMap.put("ActualNextAirportArrivalDateTime", TimeHandle.getDateTimeWithTString(fxbean.getActualNextAirportArrivalDateTime()));		
			attrabuteForXMLMap.put("EstimatedNextAirportArrivalDateTime", TimeHandle.getDateTimeWithTString(fxbean.getEstimatedNextAirportArrivalDateTime()));
			
			attrabuteForXMLMap.put("OperationStatus", fxbean.getOperationStatus());
			
			
			
			String xmlMessage = XMLHandle.makeXMLMessage(attrabuteForXMLMap, templateXML,this.transformer);
			if(xmlMessage!=null){
				imfMessage.setXmlMessage(xmlMessage.trim());
			}
			imfMessage.setFlightNumber(fxbean.getFlightIdentity());
			imfMessage.setFlightDirection(fxbean.getFlightDirection());
			imfMessage.setFlightScheduledDate(fxbean.getFlightScheduledDate());
			if(fxbean.getRegistration()!=null){
				imfMessage.setRegistration(fxbean.getRegistration());
			}
			if(fxbean.getFlightScheduledDateTime()!=null){
				imfMessage.setFlightScheduledDateTime(fxbean.getFlightScheduledDateTime());
			}
			if(fxbean.getActualPreviousAirportDepartureDateTime()!=null){
				imfMessage.setActualPreviousAirportDepartureDateTime(fxbean.getActualPreviousAirportDepartureDateTime());
			}
			if(fxbean.getEstimatedLandingDateTime()!=null){
				imfMessage.setEstimatedLandingDateTime(fxbean.getEstimatedLandingDateTime());
			}
			if(fxbean.getActualLandingDateTime()!=null){
				imfMessage.setActualLandingDateTime(fxbean.getActualLandingDateTime());
			}
			if(fxbean.getActualOnBlockDateTime()!=null){
				imfMessage.setActualOnBlockDateTime(fxbean.getActualOnBlockDateTime());
			}
			if(fxbean.getActualOffBlockDateTime()!=null){
				imfMessage.setActualOffBlockDateTime(fxbean.getActualOffBlockDateTime());
			}
			if(fxbean.getOperationStatus()!=null){
				imfMessage.setOperationStatus(fxbean.getOperationStatus());
			}
			if(fxbean.getEstimatedTakeOffDateTime()!=null){
				imfMessage.setEstimatedTakeOffDateTime(fxbean.getEstimatedTakeOffDateTime());
			}
			if(fxbean.getActualTakeOffDateTime()!=null){
				imfMessage.setActualTakeOffDateTime(fxbean.getActualTakeOffDateTime());
			}
			imfMessage.setCreateDateTime(new Date());
			imfMessage.setStatus(fxbean.getXmlStatus());
	    	return imfMessage;
	}

	public String getHandleName() {
		return handleName;
	}

	public void setHandleName(String handleName) {
		this.handleName = handleName;
	}

	public int getBeforeMin() {
		return beforeMin;
	}

	public void setBeforeMin(int beforeMin) {
		this.beforeMin = beforeMin;
	}

	public int getAfterMin() {
		return afterMin;
	}

	public void setAfterMin(int afterMin) {
		this.afterMin = afterMin;
	}

	public int getDefaultFlyMins() {
		return defaultFlyMins;
	}

	public void setDefaultFlyMins(int defaultFlyMins) {
		this.defaultFlyMins = defaultFlyMins;
	}

	public String getStatusNew() {
		return statusNew;
	}

	public void setStatusNew(String statusNew) {
		this.statusNew = statusNew;
	}

	public String getStatusMod() {
		return statusMod;
	}

	public void setStatusMod(String statusMod) {
		this.statusMod = statusMod;
	}

	public String getStatusCnl() {
		return statusCnl;
	}

	public void setStatusCnl(String statusCnl) {
		this.statusCnl = statusCnl;
	}

	public String getStatusDel() {
		return statusDel;
	}

	public void setStatusDel(String statusDel) {
		this.statusDel = statusDel;
	}

	public String getStatusDly() {
		return statusDly;
	}

	public void setStatusDly(String statusDly) {
		this.statusDly = statusDly;
	}

	public String getCreateReason() {
		return createReason;
	}

	public void setCreateReason(String createReason) {
		this.createReason = createReason;
	}

	public String getBaseAirportICAO() {
		return baseAirportICAO;
	}

	public void setBaseAirportICAO(String baseAirportICAO) {
		this.baseAirportICAO = baseAirportICAO;
	}

	public String getBaseAirportIATA() {
		return baseAirportIATA;
	}

	public void setBaseAirportIATA(String baseAirportIATA) {
		this.baseAirportIATA = baseAirportIATA;
	}

	public String getTemplateXMLName() {
		return templateXMLName;
	}

	public void setTemplateXMLName(String templateXMLName) {
		this.templateXMLName = templateXMLName;
	}

	public String getFilterXSLName() {
		return filterXSLName;
	}

	public void setFilterXSLName(String filterXSLName) {
		this.filterXSLName = filterXSLName;
	}

	public String getHandleMode() {
		return handleMode;
	}

	public void setHandleMode(String handleMode) {
		this.handleMode = handleMode;
	}

    public String flightNumberFormat(String flightNumber){
    	String formatedFlightNumber = flightNumber;
    	if(flightNumber!=null){
    		String iata = flightNumber.substring(0, 2);
    		String number = flightNumber.substring(2);
    		if(number.startsWith("0")&&number.length()>3){
    			formatedFlightNumber = iata+number.substring(1);
    		}
    		
    	}
    	formatedFlightNumber = StringUtils.removeEndIgnoreCase(formatedFlightNumber, "N");
    	return formatedFlightNumber;
    }

}
