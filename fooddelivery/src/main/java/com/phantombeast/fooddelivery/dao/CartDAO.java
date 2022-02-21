package com.phantombeast.fooddelivery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.phantombeast.fooddelivery.bean.TempCartBean;

public class CartDAO {
	private Connection cn;

	private static final String SELECT_CART_SQL = "Select * from carts where food = ? and email = ?";
	private static final String SELECT_CART_BY_USER_SQL = "Select * from carts where email = ?";
	private static final String INSERT_INTO_CART_SQL = "Insert into carts (email, food, quantity, price, total) values (?, ?, ?, ?, ?)";
	private static final String UPDATE_CART_SQL = "Update carts Set quantity = ?, total = ? where food = ? and email = ?";

	public CartDAO(Connection cn) {
		super();
		this.cn = cn;
	}

	public boolean insertIntoCart(TempCartBean tb) {
		boolean success = false;

		PreparedStatement ps;
		try {
			ps = cn.prepareStatement(INSERT_INTO_CART_SQL);
			ps.setString(1, tb.getEmail());
			ps.setString(2, tb.getFood());
			ps.setInt(3, tb.getQuantity());
			ps.setFloat(4, tb.getPrice());
			ps.setFloat(5, tb.getTotal());

			success = ps.executeUpdate() > 0;

			ps.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return success;
	}

	public TempCartBean selectCart(String name, String email) {
		TempCartBean tb = null;

		PreparedStatement ps;
		try {
			ps = cn.prepareStatement(SELECT_CART_SQL);
			ps.setString(1, name);
			ps.setString(2, email);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				tb = new TempCartBean();
				tb.setEmail(rs.getString(2));
				tb.setFood(rs.getString(3));
				tb.setQuantity(rs.getInt(4));
				tb.setPrice(rs.getFloat(5));
				tb.setTotal(rs.getFloat(6));
			}

//			ps.close();
//			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tb;
	}

	public List<TempCartBean> selectCartByUser(String email) {
		List<TempCartBean> items = new ArrayList<>();

		PreparedStatement ps;
		try {
			ps = cn.prepareStatement(SELECT_CART_BY_USER_SQL);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TempCartBean tb = new TempCartBean();
				tb.setEmail(rs.getString(2));
				tb.setFood(rs.getString(3));
				tb.setQuantity(rs.getInt(4));
				tb.setPrice(rs.getFloat(5));
				tb.setTotal(rs.getFloat(6));
				items.add(tb);
			}

			ps.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return items;
	}

	public boolean updateCart(TempCartBean tb) {
		boolean success = false;

		PreparedStatement ps;
		try {
			ps = cn.prepareStatement(UPDATE_CART_SQL);
			ps.setInt(1, tb.getQuantity());
			ps.setFloat(2, tb.getTotal());
			ps.setString(3, tb.getFood());
			ps.setString(4, tb.getEmail());
			success = ps.executeUpdate() > 0;

			ps.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return success;
	}

}
