package com.phantombeast.fooddelivery.bean;

import java.util.Map;

public class CartBean {
	private Map<String, Integer> quantities;

	public CartBean() {
	}

	public CartBean(Map<String, Integer> quantities) {
		super();
		this.quantities = quantities;
	}

	public Map<String, Integer> getQuantities() {
		return quantities;
	}

	public void setQuantities(Map<String, Integer> quantities) {
		this.quantities = quantities;
	}
}
