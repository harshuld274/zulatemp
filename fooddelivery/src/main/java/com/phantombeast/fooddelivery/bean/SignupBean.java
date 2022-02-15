package com.phantombeast.fooddelivery.bean;

import com.phantombeast.fooddelivery.dao.ConnectionProvider;
import com.phantombeast.fooddelivery.dao.UserDAO;

public class SignupBean {

	private String name;
	private String email;
	private String password;
	private String password2;
	private String address;
	private String mobile;

	public SignupBean() {
		super();
	}

	public SignupBean(String name, String email, String password, String password2, String address, String mobile) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.password2 = password2;
		this.address = address;
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
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

	public String isValid() {
		String error = null;
		UserDAO userDAO = new UserDAO(ConnectionProvider.getConnection());

		if (this.getName().length() == 0) {
			error = "Name cannot be empty";
		} else if (this.getEmail().length() == 0 || !this.getEmail().matches(".+@.+\\..+")) {
			error = "Invalid Email";
		} else if (!userDAO.isNewUser(this.getEmail())) {
			error = "User with Email already exists";			
		} else if (this.getPassword().length() == 0) {
			error = "Password cannot be empty";
		} else if (this.getPassword().length() < 1) {
			error = "Password is too short. Must be atleast 1 characters long";
		} else if (!this.getPassword().equals(this.getPassword2())) {
			error = "Passwords don't match";
		} else if (this.getAddress().length() == 0) {
			error = "Address cannot be empty";
		} else if (this.getMobile().length() == 0) {
			error = "Mobile Number cannot be empty";
		} else if (this.getMobile().length() != 10) {
			error = "Mobile Number must be 10 characters long";
		} else {
			try {
				Long.parseLong(this.getMobile());
			} catch (NumberFormatException e) {
				error = "Mobile Number is invalid";
			}
		}

		return error;
	}

	@Override
	public String toString() {
		return "SignupBean [name=" + name + ", email=" + email + ", password=" + password + ", password2=" + password2
				+ ", address=" + address + ", mobile=" + mobile + "]";
	}

	
}
