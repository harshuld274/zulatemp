package com.phantombeast.fooddelivery.bean;

import com.phantombeast.fooddelivery.dao.ConnectionProvider;
import com.phantombeast.fooddelivery.dao.FoodItemDAO;
import com.phantombeast.fooddelivery.dao.UserDAO;

public class FoodItemBean {
	private int id;
	private String name;
	private float price;
	private String about;
	private int quantity;
	
	public FoodItemBean() {
		super();
	}
	
	public FoodItemBean(String name, float price, String about, int quantity) {
		super();
		this.name = name;
		this.price = price;
		this.about = about;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String isValid() {
		String error = null;
		FoodItemDAO foodDAO = new FoodItemDAO(ConnectionProvider.getConnection());

		if (this.getName().length() == 0) {
			error = "Name cannot be empty";
		} else if (!foodDAO.isNewFood(this.getName())) {
			error = "Food with name already exists";			
		} else if (this.getPrice() < 0) {
			error = "Price cannot be negative";
		} else if (this.getAbout().split(" ").length < 3) {
			error = "Description is too short. Must be atleast 3 words long";
		} else if (this.getQuantity() < 1) {
			error = "Quantity cannot be less than 1";
		}
		return error;
	}
	
	public String updateCheck() {
		String error = null;
		FoodItemDAO foodDAO = new FoodItemDAO(ConnectionProvider.getConnection());
		
		if (this.getName().length() == 0) {
			error = "Name cannot be empty";
		} else if (this.getPrice() < 0) {
			error = "Price cannot be negative";
		} else if (this.getAbout().split(" ").length < 3) {
			error = "Description is too short. Must be atleast 3 words long";
		} else if (this.getQuantity() < 1) {
			error = "Quantity cannot be less than 1";
		}
		return error;
	}

	@Override
	public String toString() {
		return "FoodItemBean [id=" + id + ", name=" + name + ", price=" + price + ", about=" + about + ", quantity="
				+ quantity + "]";
	}
	
	
}
