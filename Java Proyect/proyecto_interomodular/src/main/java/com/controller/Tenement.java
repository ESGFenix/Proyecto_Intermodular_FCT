package com.controller;

import com.model.Database;
import java.sql.*;

/**
 * The Tenement class provides methods to insert, delete, select, and modify tenement records
 * in the associated database. All SQL operations are executed using prepared statements to
 * ensure safe query execution.
 */
public class Tenement 
{
    /**
     * Inserts a new tenement into the database.
     * 
     * @param id the ID of the tenement
     * @param id_landlord the ID of the landlord owning the tenement
     * @param rent_price the rental price of the tenement
     * @param surface the surface area of the tenement
     * @param description textual description of the tenement
     * @param type integer representing the house type
     * @param accepts_pets 1 if pets are accepted, 0 otherwise
     * @param address the address of the tenement
     * @param db the database connection object
     */
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

    /**
     * Deletes a tenement record from the database based on ID and landlord ID.
     * 
     * @param id the ID of the tenement
     * @param id_landlord the ID of the landlord
     * @param db the database connection object
     */
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

    /**
     * Selects and displays details of a tenement including house type from a join with House_Type.
     * 
     * @param id the ID of the tenement
     * @param id_landlord the ID of the landlord
     * @param db the database connection object
     */
    public static void SelectTenement(String id, int id_landlord, Database db)
    {
        // Query to retrieve the name of the house type
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

        // Query to retrieve all tenement details
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

    /**
     * Modifies an existing tenement's information in the database.
     * 
     * @param id the ID of the tenement
     * @param id_landlord the ID of the landlord
     * @param rent_price updated rent price
     * @param surface updated surface area
     * @param description updated description
     * @param type updated type ID
     * @param accepts_pets updated pet policy (1 = yes, 0 = no)
     * @param address updated address
     * @param db the database connection object
     */
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
