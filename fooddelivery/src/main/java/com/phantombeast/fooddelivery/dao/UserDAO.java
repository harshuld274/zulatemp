package com.phantombeast.fooddelivery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.phantombeast.fooddelivery.bean.LoginBean;
import com.phantombeast.fooddelivery.bean.SignupBean;

public class UserDAO {

	private Connection cn;

	private static final String LOGIN_SQL = "Select * from users where email = ? and password = ?";
	private static final String INSERT_USER_SQL = "Insert into users (name, email, password, address, mobile )values (?, ?, ?, ?, ?)";
	private static final String SELECT_USER_SQL = "Select * from users where email = ?";
	
	public UserDAO(Connection cn) {
		super();
		this.cn = cn;
	}
	
	public boolean validateLogin(LoginBean lb) {
		boolean success = false;
		
		PreparedStatement ps;
		try {
			ps = cn.prepareStatement(LOGIN_SQL);
			ps.setString(1, lb.getEmail());
			ps.setString(2, lb.getPassword());
			
			ResultSet rs = ps.executeQuery();
			success = rs.next();
			
			ps.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return success;
	}
	
	public SignupBean selectUser(String email) {
		SignupBean user = null;
		
		PreparedStatement ps;
		try {
			ps = cn.prepareStatement(SELECT_USER_SQL);
			ps.setString(1, email);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				String address = rs.getString("address");
				String mobile = rs.getString("mobile");
				
				user = new SignupBean();
				user.setName(name);
				user.setEmail(email);
				user.setAddress(address);
				user.setMobile(mobile);
			}
			
			ps.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}

	public boolean isNewUser(String email) {
		return (selectUser(email) == null);
	}

	
	public boolean insertUser(SignupBean sb) {
		boolean success = false;

		PreparedStatement ps;
		try {
			ps = cn.prepareStatement(INSERT_USER_SQL );
			ps.setString(1, sb.getName());
			ps.setString(2, sb.getEmail());
			ps.setString(3, sb.getPassword());
			ps.setString(4, sb.getAddress());
			ps.setString(5, sb.getMobile());

			success = ps.executeUpdate() > 0;
			System.out.println(success);
			ps.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return success;	
	}

	
}
