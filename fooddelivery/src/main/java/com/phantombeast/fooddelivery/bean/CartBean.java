package com.phantombeast.fooddelivery.bean;

import java.util.Map;

public class CartBean {
//	private List<String> items;
//	private List<Integer> quanities;
//	
//	public CartBean(List<String> items, List<Integer> quanities) {
//		super();
//		this.items = items;
//		this.quanities = quanities;
//	}

	private Map<String, Integer> quantities;

	public CartBean() {
	}

	public CartBean(Map<String, Integer> quantities) {
		super();
		this.quantities = quantities;
	}

}
