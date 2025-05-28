package com.controller;

import com.model.Database;
import java.sql.*;

public class Tenement 
{
    public static void InsertTenement(String id, int id_landlord, float rent_price, float surface, String description, int type, int accepts_pets, String address, Database db)
    {
        String sql = "INSERT INTO Tenement VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

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

    public static void DeleteTenement(String id, int id_landlord,  Database db)
    {
        String sql = "DELETE FROM Tenement WHERE id = ? AND id_landlord = ?";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setString(1, id);
            pstmt.setInt(2, id_landlord);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void SelectTenement(String id, int id_landlord, Database db)
    {
        String sql = "SELECT H.name FROM House_Type H JOIN Tenement T ON H.id = T.type WHERE T.id = '" + id + "'";
        String houseType;

        try (Statement stmt = db.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) 
        {
            if (rs.next()) 
                houseType = rs.getString("name");
            else
                houseType = "Unknown";
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        sql = "SELECT * FROM Tenement WHERE id = ? AND id_landlord = ?";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setString(1, id);
            pstmt.setInt(2, id_landlord);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String acceptsPets = rs.getInt("accepts_pets") == 1 ? "Yes" : "No";
                System.out.println("--------------------------------------------------------" +
                                    "\n\tTenement's ID: " + rs.getString("id") + 
                                    "\n\tTenement landlord's ID: " + rs.getString("id_landlord") + 
                                    "\n\tRent's price: " + rs.getFloat("rent_price") + 
                                    "\n\tTenement's surface: " + rs.getFloat("surface") + 
                                    "\n\tTenement's description: " + rs.getString("description") + 
                                    "\n\tTenement's type: " + houseType +
                                    "\n\tAccepts pets: " + acceptsPets  +
                                    "\n\tTenement's address: " + rs.getString("address") + 
                                    "\n--------------------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void ModifyTenement(String id, int id_landlord, float rent_price, float surface, String description, int type, int accepts_pets, String address, Database db)
    {
        String sql = "UPDATE Tenement SET rent_price = ?, surface = ?, description = ?, type = ?, accepts_pets = ?, address = ? WHERE id = ? AND id_landlord = ?";
        
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
