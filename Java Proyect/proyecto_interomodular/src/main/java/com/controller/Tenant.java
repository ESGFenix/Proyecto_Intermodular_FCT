package com.controller;

import com.model.Database;
import java.sql.*;
import java.util.ArrayList;

public class Tenant 
{
    public void InsertTenant(String DNI, String name, int has_pet, ArrayList<String> emails, ArrayList<String> phone_numbers, Database db)
    {
        int tenant_id = 0;

        String sql = "INSERT INTO tenant (DNI, name, has_pet) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, DNI);
            pstmt.setString(2, name);
            pstmt.setInt(3, has_pet);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        String query = "SELECT id FROM tenant WHERE DNI = " + DNI;

        try (Statement statement = db.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query)) 
        {
            if (resultSet.next()) 
                tenant_id = resultSet.getInt("id");  
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (String phone : phone_numbers) 
        {
            sql = "INSERT INTO tenant_phone (id_tenant, phone_number) VALUES (?, ?)";
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
            {
                pstmt.setInt(1, tenant_id);
                pstmt.setString(2, phone);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for (String email : emails) 
        {
            sql = "INSERT INTO landlord_email (tenant_id, email) VALUES (?, ?)";
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
            {
                pstmt.setInt(1, tenant_id);
                pstmt.setString(2, email);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }    
        }
    }

    public void DeleteTenant(int id, Database db)
    {
        String sql = "DELETE FROM tenant WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void SelectTenant(int id, Database db)
    {
        String sql = "SELECT * FROM tenant WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String hasPet = rs.getInt("has_pet") == 1 ? "Yes" : "No";
                System.out.println("Tenant's ID: " + rs.getString("id") + "\n Tenant's DNI: " + rs.getString("DNI") + "\n Tenant's name: " + rs.getString("name") + "\n Has pet: " + hasPet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ModifyTenant(int id, String DNI, String name, int has_pet, ArrayList<String> emails, ArrayList<String> phone_numbers, Database db)
    {
        String sql = "UPDATE tenant SET DNI = ?, name = ?, has_pet = ? WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, DNI);
            pstmt.setString(2, name);
            pstmt.setInt(3, has_pet);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Update phone numbers
        sql = "DELETE FROM tenant_phone WHERE id_tenant = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (String phone : phone_numbers) 
        {
            sql = "INSERT INTO tenant_phone (id_tenant, phone_number) VALUES (?, ?)";
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
        sql = "DELETE FROM tenant_email WHERE tenant_id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (String email : emails) 
        {
            sql = "INSERT INTO tenant_email (tenant_id, email) VALUES (?, ?)";
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
