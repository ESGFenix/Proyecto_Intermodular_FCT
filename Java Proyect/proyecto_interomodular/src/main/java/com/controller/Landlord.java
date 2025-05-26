package com.controller;

import com.model.Database;
import java.sql.*;
import java.util.ArrayList;

public class Landlord 
{
    public void InsertLandlord(String DNI, String name, ArrayList<String> phone_numbers, ArrayList<String> emails, Database db)
    {
        int landlord_id = 0;
        
        String sql = "INSERT INTO landlord (DNI, name) VALUES (?, ?)";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, DNI);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                landlord_id = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String query = "SELECT id FROM landlord WHERE DNI = " + DNI;

        try (Statement statement = db.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) 
        {
            if (resultSet.next()) {
                landlord_id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (String phone : phone_numbers) 
        {
            sql = "INSERT INTO landlord_phone (landlord_id, phone_number) VALUES (?, ?)";
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
            {
                pstmt.setInt(1, landlord_id);
                pstmt.setString(2, phone);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for (String email : emails) 
        {
            sql = "INSERT INTO landlord_email (landlord_id, email) VALUES (?, ?)";
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
            {
                pstmt.setInt(1, landlord_id);
                pstmt.setString(2, email);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }    
        }
    }

    public void DeleteLandlord(int id, Database db)
    {
        String sql = "DELETE FROM landlord WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void SelectLandlord(int id, Database db)
    {
        String sql = "SELECT * FROM landlord WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                System.out.println("Landlord's ID: " + rs.getInt("id") + ", Landlord's DNI: " + rs.getString("DNI") + ", Landlord's name: " + rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ModifyLandlord(int id, String DNI, String name, ArrayList<String> phone_numbers, ArrayList<String> emails, Database db)
    {
        String sql = "UPDATE landlord SET DNI = ?, name = ? WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, DNI);
            pstmt.setString(2, name);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Update phone numbers
        sql = "DELETE FROM landlord_phone WHERE landlord_id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (String phone : phone_numbers) 
        {
            sql = "INSERT INTO landlord_phone (landlord_id, phone_number) VALUES (?, ?)";
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
            {
                pstmt.setInt(1, id);
                pstmt.setString(2, phone);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Update emails
        sql = "DELETE FROM landlord_email WHERE landlord_id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (String email : emails) {
            sql = "INSERT INTO landlord_email (landlord_id, email) VALUES (?, ?)";
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
            {
                pstmt.setInt(1, id);
                pstmt.setString(2, email);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
