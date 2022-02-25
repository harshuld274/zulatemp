package com.phantombeast.fooddelivery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.phantombeast.fooddelivery.bean.FoodItemBean;
import com.phantombeast.fooddelivery.bean.OrderBean;

public class FoodItemDAO {

	private Connection cn;

	private static final String INSERT_FOOD_SQL = "Insert into foods (name, price, about, quantity) values (?, ?, ?, ?)";
	private static final String SELECT_ALL_FOODS_SQL = "Select * from foods";
	private static final String SELECT_ALL_AVAILABLE_FOODS_SQL = "Select * from foods where quantity > 0";
	private static final String SELECT_FOOD_BY_ID_SQL = "Select * from foods where id = ?";
	private static final String SELECT_FOOD_BY_NAME_SQL = "Select * from foods where name = ?";
	private static final String SELECT_FOODS_LIKE_SQL = "Select * from foods where name like ? and quantity > 0";
	private static final String DELETE_FOOD_BY_ID_SQL = "Delete from foods where id = ?";
	private static final String UPDATE_FOOD_BY_ID_SQL = "Update foods Set name = ?, price = ?, about = ?, quantity = ? where id = ?";
	private static final String UPDATE_QUANTITY_BY_NAME_SQL = "Update foods Set quantity = ? where name = ?";

	public FoodItemDAO(Connection cn) {
		super();
		this.cn = cn;
	}

	public FoodItemBean selectFoodById(int id) {
		FoodItemBean fb = null;

		PreparedStatement ps;
		try {
			ps = cn.prepareStatement(SELECT_FOOD_BY_ID_SQL);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				fb = new FoodItemBean();
				fb.setId(id);
				fb.setName(rs.getString(2));
				fb.setPrice(rs.getFloat(3));
				fb.setAbout(rs.getString(4));
				fb.setQuantity(rs.getInt(5));
			}

//			ps.close();
//			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return fb;
	}

	public FoodItemBean selectFoodByName(String name) {
		FoodItemBean fb = null;

		PreparedStatement ps;
		try {
			ps = cn.prepareStatement(SELECT_FOOD_BY_NAME_SQL);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				fb = new FoodItemBean();
				fb.setId(rs.getInt(1));
				fb.setName(rs.getString(2));
				fb.setPrice(rs.getFloat(3));
				fb.setAbout(rs.getString(4));
				fb.setQuantity(rs.getInt(5));
			}

			ps.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return fb;
	}

	public List<FoodItemBean> selectAllFoods() {
		List<FoodItemBean> foods = new ArrayList<>();

		try {
			Statement s = cn.createStatement();
			ResultSet rs = s.executeQuery(SELECT_ALL_FOODS_SQL);

			while (rs.next()) {
				FoodItemBean fb = new FoodItemBean();
				fb.setId(rs.getInt(1));
				fb.setName(rs.getString(2));
				fb.setPrice(rs.getFloat(3));
				fb.setAbout(rs.getString(4));
				fb.setQuantity(rs.getInt(5));
				foods.add(fb);
			}

			s.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foods;
	}

	public List<FoodItemBean> selectAvailableFoods() {
		List<FoodItemBean> foods = new ArrayList<>();

		try {
			Statement s = cn.createStatement();
			ResultSet rs = s.executeQuery(SELECT_ALL_AVAILABLE_FOODS_SQL);

			while (rs.next()) {
				FoodItemBean fb = new FoodItemBean();
				fb.setId(rs.getInt(1));
				fb.setName(rs.getString(2));
				fb.setPrice(rs.getFloat(3));
				fb.setAbout(rs.getString(4));
				fb.setQuantity(rs.getInt(5));
				foods.add(fb);
			}

			s.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foods;
	}

	public List<FoodItemBean> selectAvailableFoodsLike(String name) {
		List<FoodItemBean> foods = new ArrayList<>();
		PreparedStatement ps;

		try {
			ps = cn.prepareStatement(SELECT_FOODS_LIKE_SQL);
			ps.setString(1, "%" + name + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				FoodItemBean fb = new FoodItemBean();
				fb.setId(rs.getInt(1));
				fb.setName(rs.getString(2));
				fb.setPrice(rs.getFloat(3));
				fb.setAbout(rs.getString(4));
				fb.setQuantity(rs.getInt(5));
				foods.add(fb);
			}

			ps.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foods;
	}

	public boolean insertFood(FoodItemBean fb) {
		boolean success = false;

		PreparedStatement ps;
		try {
			ps = cn.prepareStatement(INSERT_FOOD_SQL);
			ps.setString(1, fb.getName());
			ps.setFloat(2, fb.getPrice());
			ps.setString(3, fb.getAbout());
			ps.setInt(4, fb.getQuantity());

			success = ps.executeUpdate() > 0;

			ps.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return success;
	}

	public boolean deleteFood(int id) {
		boolean success = false;

		PreparedStatement ps;
		try {
			ps = cn.prepareStatement(DELETE_FOOD_BY_ID_SQL);
			ps.setInt(1, id);
			success = ps.executeUpdate() > 0;

//			ps.close();
//			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return success;
	}

	public boolean updateFood(FoodItemBean fb) {
		boolean success = false;

		PreparedStatement ps;
		try {
			ps = cn.prepareStatement(UPDATE_FOOD_BY_ID_SQL);
			ps.setString(1, fb.getName());
			ps.setFloat(2, fb.getPrice());
			ps.setString(3, fb.getAbout());
			ps.setInt(4, fb.getQuantity());
			ps.setInt(5, fb.getId());
			success = ps.executeUpdate() > 0;

			ps.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return success;
	}

	public boolean isNewFood(String name) {
		return selectFoodByName(name) == null;
	}

	public boolean updateQuantities(OrderBean ob) {
		boolean success = true;

		PreparedStatement ps = null;
		FoodItemDAO foodDao = new FoodItemDAO(ConnectionProvider.getConnection());
		try {
			Map<String, Integer> map = ob.getCart().getQuantities();
			int updatedQuantity;
			for (Entry<String, Integer> entry : map.entrySet()) {
				if (ob.getStatus().toString().equals("PENDING")) {
					updatedQuantity = foodDao.selectFoodByName(entry.getKey()).getQuantity() - entry.getValue();
				} else {
					updatedQuantity = foodDao.selectFoodByName(entry.getKey()).getQuantity() + entry.getValue();
				}

				ps = cn.prepareStatement(UPDATE_QUANTITY_BY_NAME_SQL);
				ps.setInt(1, updatedQuantity);
				ps.setString(2, entry.getKey());
				success &= (ps.executeUpdate() > 0);
				System.out.println(success);
			}

			ps.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return success;
	}

}
