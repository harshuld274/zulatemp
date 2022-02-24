package com.phantombeast.fooddelivery.bean;

import java.util.Map;
import java.util.Map.Entry;

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

	public void addItem(String name, int quantity) {
		quantities.put(name, quantities.getOrDefault(name, 0) + quantity);
	}

	@Override
	public String toString() {
		StringBuilder json = new StringBuilder();
		json.append("{");
		for (Entry<String, Integer> entry : quantities.entrySet()) {
			json.append("\"" + entry.getKey() + "\":" + entry.getValue() + ",");
		}
		json.deleteCharAt(json.length() - 1);
		json.append("}");

		return json.toString();
	}

}
