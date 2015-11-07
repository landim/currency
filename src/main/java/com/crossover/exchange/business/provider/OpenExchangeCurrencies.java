package com.crossover.exchange.business.provider;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenExchangeCurrencies {

	private String disclaimer;
	private String license;
	private long timestamp;
	private String base;
	/**
	 * rates of currencies
	 */
	private Map<String, Double> rates;
	
	/**
	 * @return the disclaimer
	 */
	public String getDisclaimer() {
		return disclaimer;
	}
	/**
	 * @param disclaimer the disclaimer to set
	 */
	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}
	/**
	 * @return the license
	 */
	public String getLicense() {
		return license;
	}
	/**
	 * @param license the license to set
	 */
	public void setLicense(String license) {
		this.license = license;
	}
	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the base
	 */
	public String getBase() {
		return base;
	}
	/**
	 * @param base the base to set
	 */
	public void setBase(String base) {
		this.base = base;
	}
	/**
	 * @return the rates
	 */
	public Map<String, Double> getRates() {
		return rates;
	}
	/**
	 * @param rates the rates to set
	 */
	public void setRates(Map<String, Double> rates) {
		this.rates = rates;
	}
}
