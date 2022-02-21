package com.phantombeast.fooddelivery.bean;

import com.phantombeast.fooddelivery.dao.ConnectionProvider;
import com.phantombeast.fooddelivery.dao.FoodItemDAO;

public class TempCartBean {
	private String email;
	private String food;
	private int quantity;
	private float price;
	private float total;

	public TempCartBean() {
		super();
	}

	public TempCartBean(String email, String food, int quantity, float price, float total) {
		super();
		this.email = email;
		this.food = food;
		this.quantity = quantity;
		this.price = price;
		this.total = total;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public boolean isQuantityAvailable() {
		FoodItemDAO foodDAO = new FoodItemDAO(ConnectionProvider.getConnection());
		return foodDAO.selectFoodByName(this.getFood()).getQuantity() >= this.getQuantity();
	}

	public int getIdFromName(String name) {
		FoodItemDAO foodDAO = new FoodItemDAO(ConnectionProvider.getConnection());
		FoodItemBean food = foodDAO.selectFoodByName(name);
		return food.getId();
	}

	@Override
	public String toString() {
		return "TempCartBean [email=" + email + ", food=" + food + ", quantity=" + quantity + ", price=" + price
				+ ", total=" + total + "]";
	}
}
