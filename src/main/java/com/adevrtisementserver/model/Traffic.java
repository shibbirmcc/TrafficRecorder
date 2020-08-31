package com.adevrtisementserver.model;

import java.sql.Date;
import java.sql.Time;

public class Traffic {

	private int publisherID;
	private int campaignID;
	private String ipAddress;
	private String trafficReceivingParamValue;
	private String trafficSendingParamValue;
	private Date trafficReceivingDate;
	private Time trafficReceivingTime;

	public Traffic() {
	}

	public Traffic(int publisherID, int campaignID, String ipAddress, String trafficReceivingParamValue,
			String trafficSendingParamValue, Date trafficReceivingDate, Time trafficReceivingTime) {
		this.publisherID = publisherID;
		this.campaignID = campaignID;
		this.ipAddress = ipAddress;
		this.trafficReceivingParamValue = trafficReceivingParamValue;
		this.trafficSendingParamValue = trafficSendingParamValue;
		this.trafficReceivingDate = trafficReceivingDate;
		this.trafficReceivingTime = trafficReceivingTime;
	}

	public int getPublisherID() {
		return publisherID;
	}

	public void setPublisherID(int publisherID) {
		this.publisherID = publisherID;
	}

	public int getCampaignID() {
		return campaignID;
	}

	public void setCampaignID(int campaignID) {
		this.campaignID = campaignID;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getTrafficReceivingParamValue() {
		return trafficReceivingParamValue;
	}

	public void setTrafficReceivingParamValue(String trafficReceivingParamValue) {
		this.trafficReceivingParamValue = trafficReceivingParamValue;
	}

	public String getTrafficSendingParamValue() {
		return trafficSendingParamValue;
	}

	public void setTrafficSendingParamValue(String trafficSendingParamValue) {
		this.trafficSendingParamValue = trafficSendingParamValue;
	}

	public Date getTrafficReceivingDate() {
		return trafficReceivingDate;
	}

	public void setTrafficReceivingDate(Date trafficReceivingDate) {
		this.trafficReceivingDate = trafficReceivingDate;
	}

	public Time getTrafficReceivingTime() {
		return trafficReceivingTime;
	}

	public void setTrafficReceivingTime(Time trafficReceivingTime) {
		this.trafficReceivingTime = trafficReceivingTime;
	}

	@Override
	public String toString() {
		return "Traffic [publisherID=" + publisherID + ", campaignID=" + campaignID + ", ipAddress=" + ipAddress
				+ ", trafficReceivingParamValue=" + trafficReceivingParamValue + ", trafficSendingParamValue="
				+ trafficSendingParamValue + ", trafficReceivingDate=" + trafficReceivingDate
				+ ", trafficReceivingTime=" + trafficReceivingTime + "]";
	}
}
