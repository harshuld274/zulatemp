package com.phantombeast.fooddelivery.bean;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class Test {

	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		CartBean cb = new CartBean();
		Map<String, Integer> quantities = new HashMap<String, Integer>();
		quantities.put("vada", 10);
		quantities.put("idli", 15);
		quantities.put("dosa", 5);
		cb.setQuantities(quantities);
//		System.out.println(cb.getQuantities().toString());
		System.out.println(OrderBean.toJSON(cb).toString());
	}

}
