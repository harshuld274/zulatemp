package com.phantombeast.fooddelivery.bean;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderBean {
	public enum Mode {
		ONLINE_PAYMENT,
		CASH_ON_DELIVERY
	}
	
	public enum Status {
		PENDING,
		DELIVERED,
		CANCELLED
	}
	
	private int id;
	private String email;
	private CartBean cart;
	private float amount;
	private String address;
	private String mobile;
	private Timestamp time;
	private Mode mode;
	private Status status;
	
	public OrderBean() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public CartBean getCart() {
		return cart;
	}
	public void setCart(CartBean cart) {
		this.cart = cart;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public Mode getMode() {
		return mode;
	}
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public static CartBean fromJSON(Object obj) throws JsonMappingException, JsonProcessingException {
		CartBean cb = null;
		if (obj != null) {
			Map<String, Integer> result = new ObjectMapper().readValue(obj.toString(), HashMap.class);
			cb = new CartBean(result);
		}
		return cb;
	}
	public static Timestamp fromTimestamp(Object obj) {
		return Timestamp.valueOf(obj.toString());
	}
	public static Mode toEnum(String string) {
		return Mode.valueOf(string);
	}
	public static Status toStatus(String string) {
		return Status.valueOf(string);
	}
}
