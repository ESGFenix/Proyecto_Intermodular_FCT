package com.controller;

import com.model.Database;
import java.sql.*;

public class Tenement 
{
    public void InsertTenement(String id, int id_landlord, float rent_price, float surface, String description, int type, int accepts_pets, String address, Database db)
    {
        String sql = "INSERT INTO tenement VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setInt(2, id_landlord);
            pstmt.setFloat(3, rent_price);
            pstmt.setFloat(4, surface);
            pstmt.setString(5, description);
            pstmt.setInt(6, type);
            pstmt.setInt(7, accepts_pets);
            pstmt.setString(8, address);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DeleteTenement(String id, int id_landlord,  Database db)
    {
        String sql = "DELETE FROM tenement WHERE id = ? AND id_landlord = ?";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setString(1, id);
            pstmt.setInt(2, id_landlord);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void SelectTenement(String id, int id_landlord, Database db)
    {
        String sql = "SELECT * FROM tenement WHERE id = ? AND id_landlord = ?";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setString(1, id);
            pstmt.setInt(2, id_landlord);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String acceptsPets = rs.getInt("accepts_pets") == 1 ? "Yes" : "No";
                System.out.println("Tenement's ID: " + rs.getString("id") + "\nTenement landlord's ID" + rs.getString("id_landlord") + "\nRent's price: " + rs.getFloat("rent_price") + "\nTenement's surface: " + rs.getFloat("surface") + "\nTenement's description: " + acceptsPets + "\nTenement's address: " + rs.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ModifyTenement(String id, int id_landlord, float rent_price, float surface, String description, int type, int accepts_pets, String address, Database db)
    {
        String sql = "UPDATE tenement SET rent_price = ?, surface = ?, description = ?, type = ?, accepts_pets = ?, address = ? WHERE id = ? AND id_landlord = ?";
        
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setFloat(1, rent_price);
            pstmt.setFloat(2, surface);
            pstmt.setString(3, description);
            pstmt.setInt(4, type);
            pstmt.setInt(5, accepts_pets);
            pstmt.setString(6, address);
            pstmt.setString(7, id);
            pstmt.setInt(8, id_landlord);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
