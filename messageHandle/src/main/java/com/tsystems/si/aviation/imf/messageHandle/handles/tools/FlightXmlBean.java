/** 
 * Project Name:imgTelexHandle 
 * File Name:FlightXmlBean.java 
 * Package Name:com.tsystems.si.aviation.img.imgTelexHandle.bean 
 * Date:2015年9月2日下午5:44:22
 * version:v1.0
 * Copyright (c) 2015, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */ 


package com.tsystems.si.aviation.imf.messageHandle.handles.tools;

import java.util.Date;

public class FlightXmlBean {
	private int id;
	private String messageSequenceID;
	private String operationMode;
	private Date sendDateTime;
	private Date createDateTime;
	private Date originalDateTime;

	
	private Date flightScheduledDate;
	private String flightIdentity;
	private String flightDirection;
	private String airlineIatacode;
	private String airlineIcaocode;

	private String flightServiceType;
	private String flightCountryType;
	private Date flightScheduledDateTime;
	private String registration;
	private String callSign;
	private String aircraftIatacode;
	private String aircraftIcaocode;
	private String codeShareFlight;
	private String iataoriginAirport;
	private String iatapreviousAirport;
	private String iataviaAirport;
	private String iatanextAirport;
	private String iatadestinationAirport;
	private String icaoriginAirport;
	private String icaopreviousAirport;
	private String icaoviaAirport;
	private String icaonextAirport;
	private String icaodestinationAirport;
	
	private String AirportIATACodeLeg1;
	private String AirportIATACodeLeg2;
	private String AirportIATACodeLeg3;
	private String AirportIATACodeLeg4;
	
	private Date scheduledPreviousAirportDepartureDateTime;
	private Date estimatedPreviousAirportDepartureDateTime;
	private Date actualPreviousAirportDepartureDateTime;
	
	private Date scheduledLandingDateTime;
	private Date estimatedLandingDateTime;
	private Date actualLandingDateTime;
	
	private Date scheduledOnBlockDateTime;
	private Date estimatedOnBlockDateTime;
	private Date actualOnBlockDateTime;
	
	private Date scheduledOffBlockDateTime;
	private Date estimatedOffBlockDateTime;
	private Date actualOffBlockDateTime;
	
	private Date scheduledTakeOffDateTime;
	private Date estimatedTakeOffDateTime;
	private Date actualTakeOffDateTime;
	
	private Date scheduledNextAirportArrivalDateTime;
	private Date actualNextAirportArrivalDateTime;
	private Date estimatedNextAirportArrivalDateTime;
	
	private int scheduledFlyTime;
	private int estimatedFlyTime;
	private Date actualDoorOpenDateTime;
	private Date actualDoorCloseDateTime;
	private Date actualOnBridgeDateTime;
	private Date actualOffBridgeDateTime;
	private String flightStatus;
	private String operationStatus;
	private String xmlStatus;
	private String createReason;
	private String delayCode;
	private String delayFreeText;
	private String diversionAirportIATACode;
	private String diversionAirportICAOCode;
	private String stand;
	private String gate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessageSequenceID() {
		return messageSequenceID;
	}
	public void setMessageSequenceID(String messageSequenceID) {
		this.messageSequenceID = messageSequenceID;
	}
	public String getOperationMode() {
		return operationMode;
	}
	public void setOperationMode(String operationMode) {
		this.operationMode = operationMode;
	}
	public Date getSendDateTime() {
		return sendDateTime;
	}
	public void setSendDateTime(Date sendDateTime) {
		this.sendDateTime = sendDateTime;
	}
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	public Date getOriginalDateTime() {
		return originalDateTime;
	}
	public void setOriginalDateTime(Date originalDateTime) {
		this.originalDateTime = originalDateTime;
	}
	public Date getFlightScheduledDate() {
		return flightScheduledDate;
	}
	public void setFlightScheduledDate(Date flightScheduledDate) {
		this.flightScheduledDate = flightScheduledDate;
	}
	public String getFlightIdentity() {
		return flightIdentity;
	}
	public void setFlightIdentity(String flightIdentity) {
		this.flightIdentity = flightIdentity;
	}
	public String getFlightDirection() {
		return flightDirection;
	}
	public void setFlightDirection(String flightDirection) {
		this.flightDirection = flightDirection;
	}
	public String getAirlineIatacode() {
		return airlineIatacode;
	}
	public void setAirlineIatacode(String airlineIatacode) {
		this.airlineIatacode = airlineIatacode;
	}
	public String getAirlineIcaocode() {
		return airlineIcaocode;
	}
	public void setAirlineIcaocode(String airlineIcaocode) {
		this.airlineIcaocode = airlineIcaocode;
	}
	public String getFlightServiceType() {
		return flightServiceType;
	}
	public void setFlightServiceType(String flightServiceType) {
		this.flightServiceType = flightServiceType;
	}
	public String getFlightCountryType() {
		return flightCountryType;
	}
	public void setFlightCountryType(String flightCountryType) {
		this.flightCountryType = flightCountryType;
	}
	public Date getFlightScheduledDateTime() {
		return flightScheduledDateTime;
	}
	public void setFlightScheduledDateTime(Date flightScheduledDateTime) {
		this.flightScheduledDateTime = flightScheduledDateTime;
	}
	public String getRegistration() {
		return registration;
	}
	public void setRegistration(String registration) {
		this.registration = registration;
	}
	public String getCallSign() {
		return callSign;
	}
	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}
	public String getAircraftIatacode() {
		return aircraftIatacode;
	}
	public void setAircraftIatacode(String aircraftIatacode) {
		this.aircraftIatacode = aircraftIatacode;
	}
	public String getAircraftIcaocode() {
		return aircraftIcaocode;
	}
	public void setAircraftIcaocode(String aircraftIcaocode) {
		this.aircraftIcaocode = aircraftIcaocode;
	}
	public String getCodeShareFlight() {
		return codeShareFlight;
	}
	public void setCodeShareFlight(String codeShareFlight) {
		this.codeShareFlight = codeShareFlight;
	}
	public String getIataoriginAirport() {
		return iataoriginAirport;
	}
	public void setIataoriginAirport(String iataoriginAirport) {
		this.iataoriginAirport = iataoriginAirport;
	}
	public String getIatapreviousAirport() {
		return iatapreviousAirport;
	}
	public void setIatapreviousAirport(String iatapreviousAirport) {
		this.iatapreviousAirport = iatapreviousAirport;
	}
	public String getIataviaAirport() {
		return iataviaAirport;
	}
	public void setIataviaAirport(String iataviaAirport) {
		this.iataviaAirport = iataviaAirport;
	}
	public String getIatanextAirport() {
		return iatanextAirport;
	}
	public void setIatanextAirport(String iatanextAirport) {
		this.iatanextAirport = iatanextAirport;
	}
	public String getIatadestinationAirport() {
		return iatadestinationAirport;
	}
	public void setIatadestinationAirport(String iatadestinationAirport) {
		this.iatadestinationAirport = iatadestinationAirport;
	}
	public String getIcaoriginAirport() {
		return icaoriginAirport;
	}
	public void setIcaoriginAirport(String icaoriginAirport) {
		this.icaoriginAirport = icaoriginAirport;
	}
	public String getIcaopreviousAirport() {
		return icaopreviousAirport;
	}
	public void setIcaopreviousAirport(String icaopreviousAirport) {
		this.icaopreviousAirport = icaopreviousAirport;
	}
	public String getIcaoviaAirport() {
		return icaoviaAirport;
	}
	public void setIcaoviaAirport(String icaoviaAirport) {
		this.icaoviaAirport = icaoviaAirport;
	}
	public String getIcaonextAirport() {
		return icaonextAirport;
	}
	public void setIcaonextAirport(String icaonextAirport) {
		this.icaonextAirport = icaonextAirport;
	}
	public String getIcaodestinationAirport() {
		return icaodestinationAirport;
	}
	public void setIcaodestinationAirport(String icaodestinationAirport) {
		this.icaodestinationAirport = icaodestinationAirport;
	}
	public String getAirportIATACodeLeg1() {
		return AirportIATACodeLeg1;
	}
	public void setAirportIATACodeLeg1(String airportIATACodeLeg1) {
		AirportIATACodeLeg1 = airportIATACodeLeg1;
	}
	public String getAirportIATACodeLeg2() {
		return AirportIATACodeLeg2;
	}
	public void setAirportIATACodeLeg2(String airportIATACodeLeg2) {
		AirportIATACodeLeg2 = airportIATACodeLeg2;
	}
	public String getAirportIATACodeLeg3() {
		return AirportIATACodeLeg3;
	}
	public void setAirportIATACodeLeg3(String airportIATACodeLeg3) {
		AirportIATACodeLeg3 = airportIATACodeLeg3;
	}
	public String getAirportIATACodeLeg4() {
		return AirportIATACodeLeg4;
	}
	public void setAirportIATACodeLeg4(String airportIATACodeLeg4) {
		AirportIATACodeLeg4 = airportIATACodeLeg4;
	}
	public Date getScheduledPreviousAirportDepartureDateTime() {
		return scheduledPreviousAirportDepartureDateTime;
	}
	public void setScheduledPreviousAirportDepartureDateTime(
			Date scheduledPreviousAirportDepartureDateTime) {
		this.scheduledPreviousAirportDepartureDateTime = scheduledPreviousAirportDepartureDateTime;
	}
	public Date getEstimatedPreviousAirportDepartureDateTime() {
		return estimatedPreviousAirportDepartureDateTime;
	}
	public void setEstimatedPreviousAirportDepartureDateTime(
			Date estimatedPreviousAirportDepartureDateTime) {
		this.estimatedPreviousAirportDepartureDateTime = estimatedPreviousAirportDepartureDateTime;
	}
	public Date getActualPreviousAirportDepartureDateTime() {
		return actualPreviousAirportDepartureDateTime;
	}
	public void setActualPreviousAirportDepartureDateTime(
			Date actualPreviousAirportDepartureDateTime) {
		this.actualPreviousAirportDepartureDateTime = actualPreviousAirportDepartureDateTime;
	}
	public Date getScheduledLandingDateTime() {
		return scheduledLandingDateTime;
	}
	public void setScheduledLandingDateTime(Date scheduledLandingDateTime) {
		this.scheduledLandingDateTime = scheduledLandingDateTime;
	}
	public Date getEstimatedLandingDateTime() {
		return estimatedLandingDateTime;
	}
	public void setEstimatedLandingDateTime(Date estimatedLandingDateTime) {
		this.estimatedLandingDateTime = estimatedLandingDateTime;
	}
	public Date getActualLandingDateTime() {
		return actualLandingDateTime;
	}
	public void setActualLandingDateTime(Date actualLandingDateTime) {
		this.actualLandingDateTime = actualLandingDateTime;
	}
	public Date getScheduledOnBlockDateTime() {
		return scheduledOnBlockDateTime;
	}
	public void setScheduledOnBlockDateTime(Date scheduledOnBlockDateTime) {
		this.scheduledOnBlockDateTime = scheduledOnBlockDateTime;
	}
	public Date getEstimatedOnBlockDateTime() {
		return estimatedOnBlockDateTime;
	}
	public void setEstimatedOnBlockDateTime(Date estimatedOnBlockDateTime) {
		this.estimatedOnBlockDateTime = estimatedOnBlockDateTime;
	}
	public Date getActualOnBlockDateTime() {
		return actualOnBlockDateTime;
	}
	public void setActualOnBlockDateTime(Date actualOnBlockDateTime) {
		this.actualOnBlockDateTime = actualOnBlockDateTime;
	}
	public Date getScheduledOffBlockDateTime() {
		return scheduledOffBlockDateTime;
	}
	public void setScheduledOffBlockDateTime(Date scheduledOffBlockDateTime) {
		this.scheduledOffBlockDateTime = scheduledOffBlockDateTime;
	}
	public Date getEstimatedOffBlockDateTime() {
		return estimatedOffBlockDateTime;
	}
	public void setEstimatedOffBlockDateTime(Date estimatedOffBlockDateTime) {
		this.estimatedOffBlockDateTime = estimatedOffBlockDateTime;
	}
	public Date getActualOffBlockDateTime() {
		return actualOffBlockDateTime;
	}
	public void setActualOffBlockDateTime(Date actualOffBlockDateTime) {
		this.actualOffBlockDateTime = actualOffBlockDateTime;
	}
	public Date getScheduledTakeOffDateTime() {
		return scheduledTakeOffDateTime;
	}
	public void setScheduledTakeOffDateTime(Date scheduledTakeOffDateTime) {
		this.scheduledTakeOffDateTime = scheduledTakeOffDateTime;
	}
	public Date getEstimatedTakeOffDateTime() {
		return estimatedTakeOffDateTime;
	}
	public void setEstimatedTakeOffDateTime(Date estimatedTakeOffDateTime) {
		this.estimatedTakeOffDateTime = estimatedTakeOffDateTime;
	}
	public Date getActualTakeOffDateTime() {
		return actualTakeOffDateTime;
	}
	public void setActualTakeOffDateTime(Date actualTakeOffDateTime) {
		this.actualTakeOffDateTime = actualTakeOffDateTime;
	}
	public Date getScheduledNextAirportArrivalDateTime() {
		return scheduledNextAirportArrivalDateTime;
	}
	public void setScheduledNextAirportArrivalDateTime(
			Date scheduledNextAirportArrivalDateTime) {
		this.scheduledNextAirportArrivalDateTime = scheduledNextAirportArrivalDateTime;
	}
	public Date getActualNextAirportArrivalDateTime() {
		return actualNextAirportArrivalDateTime;
	}
	public void setActualNextAirportArrivalDateTime(
			Date actualNextAirportArrivalDateTime) {
		this.actualNextAirportArrivalDateTime = actualNextAirportArrivalDateTime;
	}
	public Date getEstimatedNextAirportArrivalDateTime() {
		return estimatedNextAirportArrivalDateTime;
	}
	public void setEstimatedNextAirportArrivalDateTime(
			Date estimatedNextAirportArrivalDateTime) {
		this.estimatedNextAirportArrivalDateTime = estimatedNextAirportArrivalDateTime;
	}
	public int getScheduledFlyTime() {
		return scheduledFlyTime;
	}
	public void setScheduledFlyTime(int scheduledFlyTime) {
		this.scheduledFlyTime = scheduledFlyTime;
	}
	public int getEstimatedFlyTime() {
		return estimatedFlyTime;
	}
	public void setEstimatedFlyTime(int estimatedFlyTime) {
		this.estimatedFlyTime = estimatedFlyTime;
	}
	public Date getActualDoorOpenDateTime() {
		return actualDoorOpenDateTime;
	}
	public void setActualDoorOpenDateTime(Date actualDoorOpenDateTime) {
		this.actualDoorOpenDateTime = actualDoorOpenDateTime;
	}
	public Date getActualDoorCloseDateTime() {
		return actualDoorCloseDateTime;
	}
	public void setActualDoorCloseDateTime(Date actualDoorCloseDateTime) {
		this.actualDoorCloseDateTime = actualDoorCloseDateTime;
	}
	public Date getActualOnBridgeDateTime() {
		return actualOnBridgeDateTime;
	}
	public void setActualOnBridgeDateTime(Date actualOnBridgeDateTime) {
		this.actualOnBridgeDateTime = actualOnBridgeDateTime;
	}
	public Date getActualOffBridgeDateTime() {
		return actualOffBridgeDateTime;
	}
	public void setActualOffBridgeDateTime(Date actualOffBridgeDateTime) {
		this.actualOffBridgeDateTime = actualOffBridgeDateTime;
	}
	public String getFlightStatus() {
		return flightStatus;
	}
	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}
	public String getOperationStatus() {
		return operationStatus;
	}
	public void setOperationStatus(String operationStatus) {
		this.operationStatus = operationStatus;
	}
	public String getXmlStatus() {
		return xmlStatus;
	}
	public void setXmlStatus(String xmlStatus) {
		this.xmlStatus = xmlStatus;
	}
	public String getCreateReason() {
		return createReason;
	}
	public void setCreateReason(String createReason) {
		this.createReason = createReason;
	}
	public String getDelayCode() {
		return delayCode;
	}
	public void setDelayCode(String delayCode) {
		this.delayCode = delayCode;
	}
	public String getDelayFreeText() {
		return delayFreeText;
	}
	public void setDelayFreeText(String delayFreeText) {
		this.delayFreeText = delayFreeText;
	}
	public String getDiversionAirportIATACode() {
		return diversionAirportIATACode;
	}
	public void setDiversionAirportIATACode(String diversionAirportIATACode) {
		this.diversionAirportIATACode = diversionAirportIATACode;
	}
	public String getDiversionAirportICAOCode() {
		return diversionAirportICAOCode;
	}
	public void setDiversionAirportICAOCode(String diversionAirportICAOCode) {
		this.diversionAirportICAOCode = diversionAirportICAOCode;
	}
	public String getStand() {
		return stand;
	}
	public void setStand(String stand) {
		this.stand = stand;
	}
	public String getGate() {
		return gate;
	}
	public void setGate(String gate) {
		this.gate = gate;
	}
	
	
	
}
