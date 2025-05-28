package com.controller;

import com.model.Database;
import java.sql.*;
import java.util.ArrayList;

public class Tenant 
{
    public static void InsertTenant(String DNI, String name, int has_pet, ArrayList<String> emails, ArrayList<String> phone_numbers, Database db)
    {
        int tenant_id = 0;

        String query = "SELECT id FROM Tenant WHERE DNI = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) 
        {
            pstmt.setString(1, DNI);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) 
            {
                System.out.println("Error: A tenant with this DNI already exists.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        query = "INSERT INTO Tenant (DNI, name, has_pet) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
            pstmt.setString(1, DNI);
            pstmt.setString(2, name);
            pstmt.setInt(3, has_pet);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        try (PreparedStatement pstmt = db.getConnection().prepareStatement("SELECT id FROM Landlord WHERE DNI = ?")) 
        {
            pstmt.setString(1, DNI);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) 
            {
                tenant_id = rs.getInt("id");
            } 
            else 
            {
                System.out.println("Error: Could not retrieve the newly inserted landlord ID.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        for (String phone : phone_numbers) 
        {
            query = "INSERT INTO Tenant_Phone (id_tenant, phone_number) VALUES (?, ?)";
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) 
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
            query = "INSERT INTO Tenant_Email (tenant_id, email) VALUES (?, ?)";
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) 
            {
                pstmt.setInt(1, tenant_id);
                pstmt.setString(2, email);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }    
        }
    }

    public static void DeleteTenant(int id, Database db)
    {
        String sql = "DELETE FROM Tenant WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void SelectTenant(int id, Database db)
    {
        String sql = "SELECT * FROM Tenant WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String hasPet = rs.getInt("has_pet") == 1 ? "Yes" : "No";
                System.out.print("------------------------------------------------------------" +
                                    "\n\tTenant's ID: " + rs.getString("id") + 
                                    "\n\tTenant's DNI: " + rs.getString("DNI") + 
                                    "\n\tenant's name: " + rs.getString("name") + 
                                    "\n\tHas pet: " + hasPet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "SELECT * FROM Tenant_Email WHERE id_tenant = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            System.out.print("\n\tTenant's emails: ");
            while (rs.next()) 
            {
                System.out.print(rs.getString("email"));
                if (!rs.isLast())
                    System.out.print(", ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "SELECT * FROM Tenant_Phone_Number WHERE id_tenant = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            System.out.print("\n\tTenant's phone numbers: ");
            while (rs.next()) 
            {
                System.out.print(rs.getString("phone_number"));
                if (!rs.isLast())
                    System.out.print(", ");
                else
                    System.out.print("\n----------------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    public static void ModifyTenant(int id, String DNI, String name, int has_pet, ArrayList<String> emails, ArrayList<String> phone_numbers, Database db)
    {
        String sql = "UPDATE Tenant SET DNI = ?, name = ?, has_pet = ? WHERE id = ?";
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
        sql = "DELETE FROM Tenant_Phone WHERE id_tenant = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (String phone : phone_numbers) 
        {
            sql = "INSERT INTO Tenant_Phone (id_tenant, phone_number) VALUES (?, ?)";
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
        sql = "DELETE FROM Tenant_Email WHERE tenant_id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (String email : emails) 
        {
            sql = "INSERT INTO Tenant_Email (tenant_id, email) VALUES (?, ?)";
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
