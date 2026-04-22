package com.restaurant.dao;

import com.restaurant.db.DBConnection;
import com.restaurant.model.Restaurant;
import com.restaurant.model.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO {

    public void insertRestaurant(Restaurant r) throws SQLException {
        String sql = "INSERT INTO Restaurant VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getId());
            ps.setString(2, r.getName());
            ps.setString(3, r.getAddress());
            ps.executeUpdate();
        }
    }

    public List<Restaurant> getAllRestaurants() throws SQLException {
        List<Restaurant> list = new ArrayList<>();
        String sql = "SELECT * FROM Restaurant";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Restaurant(
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getString("Address")
                ));
            }
        }
        return list;
    }

    public void deleteRestaurant(int id) throws SQLException {
        String sql = "DELETE FROM Restaurant WHERE Id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
