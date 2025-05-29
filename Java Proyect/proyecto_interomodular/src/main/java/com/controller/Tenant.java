package com.controller;

import com.model.Database;
import java.sql.*;
import java.util.ArrayList;

/**
 * Controller class for managing Tenant records in the database.
 * <p>
 * Provides CRUD operations for tenants, including creation, retrieval,
 * update, and deletion, along with management of associated emails and phone numbers.
 * </p>
 */
public class Tenant 
{
    /**
     * Inserts a new tenant into the Tenant table along with contact details.
     * Verifies uniqueness of DNI before insertion.
     * Retrieves the generated tenant ID and inserts associated emails and phone numbers.
     *
     * @param DNI            Unique DNI identifier of the tenant
     * @param name           Full name of the tenant
     * @param has_pet        Flag indicating if tenant has a pet (1: yes, 0: no)
     * @param emails         List of email addresses to associate
     * @param phone_numbers  List of phone numbers to associate
     * @param db             Database instance for connection access
     */
    public static void InsertTenant(String DNI, String name, int has_pet, ArrayList<String> emails, ArrayList<String> phone_numbers, Database db)
    {
        int tenant_id = 0;

        // Check if a tenant with the same DNI already exists
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
        } catch (SQLException e) 
        {
            // Handle SQL exception during DNI lookup
            e.printStackTrace();
            return;
        }

        // Insert the tenant record into the Tenant table
        query = "INSERT INTO Tenant (DNI, name, has_pet) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) 
        {
            pstmt.setString(1, DNI);
            pstmt.setString(2, name);
            pstmt.setInt(3, has_pet);
            pstmt.executeUpdate();
        } catch (SQLException e) 
        {
            // Handle SQL exception during insertion
            e.printStackTrace();
        }
        
        // Retrieve the newly inserted tenant ID
        try (PreparedStatement pstmt = db.getConnection().prepareStatement("SELECT id FROM Tenant WHERE DNI = ?")) 
        {
            pstmt.setString(1, DNI);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) 
                tenant_id = rs.getInt("id");

            else 
            {
                System.out.println("Error: Could not retrieve the newly inserted tenant ID.");
                return;
            }
        } catch (SQLException e) 
        {
            // Handle SQL exception during ID retrieval
            e.printStackTrace();
            return;
        }

        // Insert each phone number into Tenant_Phone
        for (String phone : phone_numbers) 
        {
            query = "INSERT INTO Tenant_Phone (id_tenant, phone_number) VALUES (?, ?)";
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) 
            {
                pstmt.setInt(1, tenant_id);
                pstmt.setString(2, phone);
                pstmt.executeUpdate();
            } catch (SQLException e) 
            {
                // Handle SQL exception during phone insertion
                e.printStackTrace();
            }
        }

        // Insert each email into Tenant_Email
        for (String email : emails) 
        {
            query = "INSERT INTO Tenant_Email (tenant_id, email) VALUES (?, ?)";
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) 
            {
                pstmt.setInt(1, tenant_id);
                pstmt.setString(2, email);
                pstmt.executeUpdate();
            } catch (SQLException e) 
            {
                // Handle SQL exception during email insertion
                e.printStackTrace();
            }    
        }
    }

    /**
     * Deletes a tenant record from the database by ID.
     *
     * @param id  Numeric ID of the tenant to delete
     * @param db  Database instance for connection access
     */
    public static void DeleteTenant(int id, Database db)
    {
        String sql = "DELETE FROM Tenant WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) 
        {
            // Handle SQL exception during deletion
            e.printStackTrace();
        }
    }

    /**
     * Retrieves and prints tenant details, including emails and phone numbers.
     *
     * @param id  Numeric ID of the tenant to retrieve
     * @param db  Database instance for connection access
     */
    public static void SelectTenant(int id, Database db)
    {
        String sql = "SELECT * FROM Tenant WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String hasPet = rs.getInt("has_pet") == 1 ? "Yes" : "No";
                System.out.print("------------------------------------------------------------");
                System.out.print("\n\tTenant's ID: " + rs.getString("id") + 
                                 "\n\tTenant's DNI: " + rs.getString("DNI") + 
                                 "\n\tTenant's name: " + rs.getString("name") + 
                                 "\n\tHas pet: " + hasPet);
            }
        } catch (SQLException e) 
        {
            // Handle SQL exception during selection
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
                System.out.print(rs.getString("email") + (rs.isLast() ? "" : ", "));
            }
        } catch (SQLException e) 
        {
            // Handle SQL exception during email retrieval
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
                System.out.print(rs.getString("phone_number") + (rs.isLast() ? "" : ", "));
            }
            System.out.println("\n----------------------------------------------------");
        } catch (SQLException e) 
        {
            // Handle SQL exception during phone retrieval
            e.printStackTrace();
        }
    }

    /**
     * Updates tenant information and contact details.
     * Clears existing phone numbers and emails before inserting new values.
     *
     * @param id             Numeric ID of the tenant to update
     * @param DNI            New DNI for the tenant
     * @param name           New name of the tenant
     * @param has_pet        Updated pet flag (1: yes, 0: no)
     * @param emails         List of new email addresses
     * @param phone_numbers  List of new phone numbers
     * @param db             Database instance for connection access
     */
    public static void ModifyTenant(int id, String DNI, String name, int has_pet, ArrayList<String> emails, ArrayList<String> phone_numbers, Database db)
    {
        String sql = "UPDATE Tenant SET DNI = ?, name = ?, has_pet = ? WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setString(1, DNI);
            pstmt.setString(2, name);
            pstmt.setInt(3, has_pet);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) 
        {
            // Handle SQL exception during update
            e.printStackTrace();
        }

        // Delete existing phone numbers
        sql = "DELETE FROM Tenant_Phone WHERE id_tenant = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) 
        {
            // Handle SQL exception during phone deletion
            e.printStackTrace();
        }

        // Insert new phone numbers
        for (String phone : phone_numbers) 
        {
            sql = "INSERT INTO Tenant_Phone (id_tenant, phone_number) VALUES (?, ?)";
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
            {
                pstmt.setInt(1, id);
                pstmt.setString(2, phone);
                pstmt.executeUpdate();
            } catch (SQLException e) 
            {
                // Handle SQL exception during phone insertion
                e.printStackTrace();
            }
        }

        // Delete existing emails
        sql = "DELETE FROM Tenant_Email WHERE tenant_id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) 
        {
            // Handle SQL exception during email deletion
            e.printStackTrace();
        }

        // Insert new email addresses
        for (String email : emails) 
        {
            sql = "INSERT INTO Tenant_Email (tenant_id, email) VALUES (?, ?)";
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
            {
                pstmt.setInt(1, id);
                pstmt.setString(2, email);
                pstmt.executeUpdate();
            } catch (SQLException e) 
            {
                // Handle SQL exception during email insertion
                e.printStackTrace();
            }    
        }
    }
}
