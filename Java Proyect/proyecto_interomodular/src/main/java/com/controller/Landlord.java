/**
 * This class handles the CRUD operations for Landlords in the system.
 */
package com.controller;

import com.model.Database;
import java.sql.*;
import java.util.ArrayList;

/**
 * Provides static methods to insert, delete, retrieve, and modify landlord data in the database.
 */
public class Landlord 
{
    /**
     * Inserts a new landlord into the database along with their phone numbers and emails.
     * If a landlord with the given DNI already exists, insertion is aborted.
     *
     * @param DNI Landlord's national identity number
     * @param name Landlord's full name
     * @param phone_numbers List of phone numbers
     * @param emails List of emails
     * @param db Database connection handler
     */
    public static void InsertLandlord(String DNI, String name, ArrayList<String> phone_numbers, ArrayList<String> emails, Database db) {
        int landlord_id = 0;

        // Check for duplicate landlord by DNI
        String query = "SELECT id FROM Landlord WHERE DNI = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) 
        {
            pstmt.setString(1, DNI);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) 
            {
                System.out.println("Error: A landlord with this DNI already exists.");
                return;
            }
        } catch (SQLException e) 
        {
            e.printStackTrace();
            return;
        }

        // Insert landlord
        query = "INSERT INTO Landlord (DNI, name) VALUES (?, ?)";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) 
        {
            pstmt.setString(1, DNI);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }

        // Retrieve the ID of the newly inserted landlord
        try (PreparedStatement pstmt = db.getConnection().prepareStatement("SELECT id FROM Landlord WHERE DNI = ?")) 
        {
            pstmt.setString(1, DNI);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) 
                landlord_id = rs.getInt("id");

            else 
            {
                System.out.println("Error: Could not retrieve the newly inserted landlord ID.");
                return;
            }
        } catch (SQLException e) 
        {
            e.printStackTrace();
            return;
        }

        // Insert phone numbers
        for (String phone : phone_numbers) 
        {
            query = "INSERT INTO Landlord_Phone_Number (id_landlord, phone_number) VALUES (?, ?)";
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) 
            {
                pstmt.setInt(1, landlord_id);
                pstmt.setString(2, phone);
                pstmt.executeUpdate();
            } catch (SQLException e) 
            {
                e.printStackTrace();
            }
        }

        // Insert emails
        for (String email : emails) 
        {
            query = "INSERT INTO Landlord_Email (id_landlord, email) VALUES (?, ?)";
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) 
            {
                pstmt.setInt(1, landlord_id);
                pstmt.setString(2, email);
                pstmt.executeUpdate();
            } catch (SQLException e) 
            {
                e.printStackTrace();
            }    
        }

        System.out.println("Contract created successfully!");
    }

    /**
     * Deletes a landlord and all associated phone numbers and emails from the database.
     *
     * @param id ID of the landlord to delete
     * @param db Database connection handler
     */
    public static void DeleteLandlord(int id, Database db) 
    {
        String sql = "DELETE FROM Landlord_Email WHERE id_landlord = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }

        sql = "DELETE FROM Landlord_Phone_Number WHERE id_landlord = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }

        sql = "DELETE FROM Landlord WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves and prints all information about a landlord, including emails and phone numbers.
     *
     * @param id ID of the landlord to retrieve
     * @param db Database connection handler
     */
    public static void SelectLandlord(int id, Database db) 
    {
        String sql = "SELECT * FROM Landlord WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                System.out.print("-----------------------------------------------------" + 
                                "\n\tLandlord's ID: " + rs.getInt("id") + 
                                "\n\tLandlord's DNI: " + rs.getString("DNI") + 
                                "\n\tLandlord's name: " + rs.getString("name"));
            }
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }

        sql = "SELECT * FROM Landlord_Email WHERE id_landlord = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            System.out.print("\n\tLandlord's emails: ");
            while (rs.next()) 
            {
                System.out.print(rs.getString("email"));
                if (!rs.isLast())
                    System.out.print(", ");
            }
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }

        sql = "SELECT * FROM Landlord_Phone_Number WHERE id_landlord = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            System.out.print("\n\tLandlord's phone numbers: ");
            while (rs.next()) 
            {
                System.out.print(rs.getString("phone_number"));
                if (!rs.isLast())
                    System.out.print(", ");
            }
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }
        System.out.println();
    }

    /**
     * Modifies the landlord's information in the database.
     * Updates name, DNI, emails, and phone numbers.
     *
     * @param id Landlord ID
     * @param DNI New DNI
     * @param name New name
     * @param phone_numbers New phone numbers
     * @param emails New emails
     * @param db Database connection handler
     */
    public static void ModifyLandlord(int id, String DNI, String name, ArrayList<String> phone_numbers, ArrayList<String> emails, Database db) {
        // Update name and DNI
        String sql = "UPDATE Landlord SET DNI = ?, name = ? WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setString(1, DNI);
            pstmt.setString(2, name);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }

        // Update phone numbers
        sql = "DELETE FROM Landlord_Phone WHERE landlord_id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }

        for (String phone : phone_numbers) 
        {
            sql = "INSERT INTO Landlord_Phone (landlord_id, phone_number) VALUES (?, ?)";
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
        sql = "DELETE FROM Landlord_Email WHERE landlord_id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }

        for (String email : emails) {
            sql = "INSERT INTO Landlord_Email (landlord_id, email) VALUES (?, ?)";
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
            {
                pstmt.setInt(1, id);
                pstmt.setString(2, email);
                pstmt.executeUpdate();
            } catch (SQLException e) 
            {
                e.printStackTrace();
            }
        }
    }
}
