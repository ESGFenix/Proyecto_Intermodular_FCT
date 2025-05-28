package com.controller;

import com.model.Database;
import java.sql.*;

public class HouseType 
{
    public static void InsertHouseType(String name, String description, Database db)
    {
        String query = "INSERT INTO House_Type (name, description) VALUES (?, ?)";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void DeleteHouseType(int id, Database db)
    {
        String query = "DELETE FROM House_Type WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void ModifyHouseType(int id, String name, String description, Database db)
    {
        String query = "UPDATE House_Type SET name = ?, description = ? WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void SelectHouseType(int id, Database db)
    {
        String query = "SELECT * FROM House_Type WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                System.out.println("House type ID: " + rs.getString("id") + 
                                    "\n\tHouse type name: " + rs.getString("name") + 
                                    "\n\tHouse type description: " + rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
