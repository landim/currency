package com.crossover.exchange.model;

import org.springframework.data.annotation.Id;

public class Currency {
	@Id
	private String code;
	private String name;
	private double value;
	
	public Currency(String code, String name, double value) {
		this.code = code;
		this.name = name;
		this.value = value;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}

}
