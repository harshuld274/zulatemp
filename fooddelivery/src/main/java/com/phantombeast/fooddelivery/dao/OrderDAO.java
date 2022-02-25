package com.phantombeast.fooddelivery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.phantombeast.fooddelivery.bean.OrderBean;

public class OrderDAO {
	private Connection cn;

	private static final String PLACE_ORDER_SQL = "Insert into orders (email, cart, amount, address, mobile, time, mode, status) values (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SELECT_ALL_ORDERS_SQL = "Select * from orders";
	private static final String SELECT_ORDER_BY_ID_SQL = "Select * from orders where id = ?";
	private static final String SELECT_ORDERS_BY_USER_SQL = "Select * from orders where email = ?";
	private static final String SETTLE_ORDER_BY_ID_SQL = "Update orders Set status = ? where id = ?";

	public OrderDAO(Connection cn) {
		super();
		this.cn = cn;
	}

	public boolean placeOrder(OrderBean ob) throws JsonMappingException, JsonProcessingException {
		boolean success = false;

		PreparedStatement ps;
		try {
			ps = cn.prepareStatement(PLACE_ORDER_SQL);
			ps.setString(1, ob.getEmail());
			ps.setString(2, OrderBean.toJSON(ob.getCart()));
			ps.setFloat(3, ob.getAmount());
			ps.setString(4, ob.getAddress());
			ps.setString(5, ob.getMobile());
			ps.setTimestamp(6, ob.getTime());
			ps.setString(7, ob.getMode().name());
			ps.setString(8, ob.getStatus().name());
			System.out.println(ps.toString());
			success = ps.executeUpdate() > 0;

			ps.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return success;
	}

	public List<OrderBean> selectAllOrders() throws JsonMappingException, JsonProcessingException {
		List<OrderBean> foods = new ArrayList<>();

		try {
			Statement s = cn.createStatement();
			ResultSet rs = s.executeQuery(SELECT_ALL_ORDERS_SQL);

			while (rs.next()) {
				OrderBean ob = new OrderBean();

				ob.setId(rs.getInt(1));
				ob.setEmail(rs.getString(2));

				ob.setCart(OrderBean.fromJSON(rs.getString(3)));
				ob.setAmount(rs.getFloat(4));
				ob.setAddress(rs.getString(5));
				ob.setMobile(rs.getString(6));
				ob.setTime(rs.getTimestamp(7));
				ob.setMode(OrderBean.toMode(rs.getString(8)));
				ob.setStatus(OrderBean.toStatus(rs.getString(9)));
				foods.add(ob);
			}

			s.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foods;
	}

	public List<OrderBean> selectOrdersByUser(String email) throws JsonMappingException, JsonProcessingException {
		List<OrderBean> foods = new ArrayList<>();
		PreparedStatement ps;

		try {
			ps = cn.prepareStatement(SELECT_ORDERS_BY_USER_SQL);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				OrderBean ob = new OrderBean();

				ob.setId(rs.getInt(1));
				ob.setEmail(email);

				ob.setCart(OrderBean.fromJSON(rs.getString(3)));
				ob.setAmount(rs.getFloat(4));
				ob.setAddress(rs.getString(5));
				ob.setMobile(rs.getString(6));
				ob.setTime(rs.getTimestamp(7));
				ob.setMode(OrderBean.toMode(rs.getString(8)));
				ob.setStatus(OrderBean.toStatus(rs.getString(9)));
				foods.add(ob);
			}

			ps.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foods;
	}

	public OrderBean selectOrderById(int id) throws JsonMappingException, JsonProcessingException {
		OrderBean ob = new OrderBean();
		PreparedStatement ps;

		try {
			ps = cn.prepareStatement(SELECT_ORDER_BY_ID_SQL);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ob.setId(rs.getInt(1));
				ob.setEmail(rs.getString(2));

				ob.setCart(OrderBean.fromJSON(rs.getString(3)));
				ob.setAmount(rs.getFloat(4));
				ob.setAddress(rs.getString(5));
				ob.setMobile(rs.getString(6));
				ob.setTime(rs.getTimestamp(7));
				ob.setMode(OrderBean.toMode(rs.getString(8)));
				ob.setStatus(OrderBean.toStatus(rs.getString(9)));
			}

//			ps.close();
//			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ob;
	}

	public boolean settleOrderById(int id, String status) {
		boolean success = false;

		PreparedStatement ps;
		try {
			ps = cn.prepareStatement(SETTLE_ORDER_BY_ID_SQL);
			ps.setString(1, status);
			ps.setInt(2, id);

			success = ps.executeUpdate() > 0;
			ps.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return success;
	}
}