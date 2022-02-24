package com.phantombeast.fooddelivery.bean;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		Timestamp ts = new Timestamp(System.currentTimeMillis());

		// Passing the long value in the Date class
		// constructor
		Date date = new Date(ts.getTime());

		// Printing the date
		System.out.println(date);
	}

	public static CartBean fromJSON(Object obj) throws JsonMappingException, JsonProcessingException {
		CartBean cb = null;
		if (obj != null) {
			Map<String, Integer> result = new ObjectMapper().readValue(obj.toString(), HashMap.class);
			cb = new CartBean(result);
		}
		return cb;
	}

	public static Object toJSON(CartBean cb) throws JsonMappingException, JsonProcessingException {
		Object obj = null;
		if (cb != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(cb.toString());
			obj = objectMapper.readValue(json, Object.class);
		}
		return obj;
	}
}
