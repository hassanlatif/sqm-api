package com.stc.sqm.hajj;

import java.util.List;

public class Utilization {

	
	private String kpiDate;
	private String customer;
	private String circuit;
	private String availability;
	private String configuredBandwidth;
	private String avgUtilIn;
	private String avgUtilOut;
	private String maxUtilIn;
	private String maxUtilInDate;
	private String maxUtilOut;
	private String maxUtilOutDate;
	private String avgErrorIn;
	private String avgErrorOut;
	private String hajj;
	private String vip;
	private String isHajjVip;
	private List<UtilReadings> utilReadings;
	
	
	public String getKpiDate() {
		return kpiDate;
	}
	public void setKpiDate(String kpiDate) {
		this.kpiDate = kpiDate;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getCircuit() {
		return circuit;
	}
	public void setCircuit(String circuit) {
		this.circuit = circuit;
	}
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	public String getConfiguredBandwidth() {
		return configuredBandwidth;
	}
	public void setConfiguredBandwidth(String configuredBandwidth) {
		this.configuredBandwidth = configuredBandwidth;
	}
	public String getAvgUtilIn() {
		return avgUtilIn;
	}
	public void setAvgUtilIn(String avgUtilIn) {
		this.avgUtilIn = avgUtilIn;
	}
	public String getAvgUtilOut() {
		return avgUtilOut;
	}
	public void setAvgUtilOut(String avgUtilOut) {
		this.avgUtilOut = avgUtilOut;
	}
	public String getMaxUtilIn() {
		return maxUtilIn;
	}
	public void setMaxUtilIn(String maxUtilIn) {
		this.maxUtilIn = maxUtilIn;
	}
	public String getMaxUtilInDate() {
		return maxUtilInDate;
	}
	public void setMaxUtilInDate(String maxUtilInDate) {
		this.maxUtilInDate = maxUtilInDate;
	}
	public String getMaxUtilOut() {
		return maxUtilOut;
	}
	public void setMaxUtilOut(String maxUtilOut) {
		this.maxUtilOut = maxUtilOut;
	}
	public String getMaxUtilOutDate() {
		return maxUtilOutDate;
	}
	public void setMaxUtilOutDate(String maxUtilOutDate) {
		this.maxUtilOutDate = maxUtilOutDate;
	}
	public String getAvgErrorIn() {
		return avgErrorIn;
	}
	public void setAvgErrorIn(String avgErrorIn) {
		this.avgErrorIn = avgErrorIn;
	}
	public String getAvgErrorOut() {
		return avgErrorOut;
	}
	public void setAvgErrorOut(String avgErrorOut) {
		this.avgErrorOut = avgErrorOut;
	}
	public String getHajj() {
		return hajj;
	}
	public void setHajj(String hajj) {
		this.hajj = hajj;
	}
	public String getVip() {
		return vip;
	}
	public void setVip(String vip) {
		this.vip = vip;
	}
	public String getIsHajjVip() {
		return isHajjVip;
	}
	public void setIsHajjVip(String isHajjVip) {
		this.isHajjVip = isHajjVip;
	}
	
	public List<UtilReadings> getUtilReadings() {
		return utilReadings;
	}
	public void setUtilReadings(List<UtilReadings> utilReadings) {
		this.utilReadings = utilReadings;
	}
	
}
