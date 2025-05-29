package com.controller;

import com.model.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

/**
 * Controller class for managing Contract records in the database.
 * <p>
 * Provides methods to insert, select, update, change status, and delete contracts.
 * Each operation is executed via JDBC PreparedStatement for SQL safety.
 * </p>
 */
public class Contract 
{
    /**
     * Inserts a new contract into the Contract table.
     *
     * @param id_tenement     Identifier of the tenement (format: TXXX)
     * @param id_tenant       Numeric ID of the tenant
     * @param start_date      Contract start date
     * @param finish_date     Contract finish date
     * @param price           Contract price value
     * @param contract_status Integer code (1: PENDING, 2: ACTIVE, else EXPIRED)
     * @param db              Database instance for obtaining JDBC connection
     */
    public static void InsertContract(String id_tenement, int id_tenant, Date start_date, Date finish_date, float price, int contract_status, Database db)
    {
        // Determine status string based on input code
        String contractStatusString;
        if (contract_status == 1)
            contractStatusString = "PENDING";
        else if (contract_status == 2)
            contractStatusString = "ACTIVE";
        else
            contractStatusString = "EXPIRED";

        // SQL statement for inserting a new contract record
        String sql = "INSERT INTO Contract (id_tenement, id_tenant, start_date, finish_date, price, contract_status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setString(1, id_tenement);
            pstmt.setInt(2, id_tenant);
            pstmt.setDate(3, start_date);
            pstmt.setDate(4, finish_date);
            pstmt.setFloat(5, price);
            pstmt.setString(6, contractStatusString);
            pstmt.executeUpdate(); // Execute insert operation
        } catch (SQLException e) 
        {
            // Handle SQL exception by printing stack trace
            e.printStackTrace();
        }
    } 

    /**
     * Deletes an existing contract from the database.
     *
     * @param id_tenement Identifier of the tenement
     * @param id_tenant   Numeric ID of the tenant
     * @param start_date  Contract start date to match
     * @param db          Database instance for obtaining JDBC connection
     */
    public static void DeleteContract(String id_tenement, int id_tenant, Date start_date, Database db)
    {
        // SQL statement for deleting a contract record
        String sql = "DELETE FROM Contract WHERE id_tenement = ? AND id_tenant = ? AND start_date = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setString(1, id_tenement);
            pstmt.setInt(2, id_tenant);
            pstmt.setDate(3, start_date);
            pstmt.executeUpdate(); // Execute delete operation
        } catch (SQLException e) 
        {
            // Handle SQL exception by printing stack trace
            e.printStackTrace();
        }
    }

    /**
     * Updates details of an existing contract in the database.
     *
     * @param id_tenement   Identifier of the tenement
     * @param id_tenant     Numeric ID of the tenant
     * @param start_date    Original contract start date to match
     * @param finish_date   New finish date for the contract
     * @param price         New price value
     * @param contract_status Integer code (1: PENDING, 2: ACTIVE, else EXPIRED)
     * @param db            Database instance for obtaining JDBC connection
     */
    public static void ModifyContract(String id_tenement, int id_tenant, Date start_date, Date finish_date, float price, int contract_status, Database db)
    {
        // Determine status string based on input code
        String contractStatusString;
        if (contract_status == 1)
            contractStatusString = "PENDING";
        else if (contract_status == 2)
            contractStatusString = "ACTIVE";
        else
            contractStatusString = "EXPIRED";

        // SQL statement for updating contract fields
        String sql = "UPDATE Contract SET finish_date = ?, price = ?, contract_status = ? WHERE id_tenement = ? AND id_tenant = ? AND start_date = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setDate(1, finish_date);
            pstmt.setFloat(2, price);
            pstmt.setString(3, contractStatusString);
            pstmt.setString(4, id_tenement);
            pstmt.setInt(5, id_tenant);
            pstmt.setDate(6, start_date);
            pstmt.executeUpdate(); // Execute update operation
        } catch (SQLException e) 
        {
            // Handle SQL exception by printing stack trace
            e.printStackTrace();
        }
    }

    /**
     * Changes the status of an existing contract record.
     *
     * @param id_tenement   Identifier of the tenement
     * @param id_tenant     Numeric ID of the tenant
     * @param start_date    Contract start date to match
     * @param contract_status Integer code (1: PENDING, 2: ACTIVE, else EXPIRED)
     * @param db            Database instance for obtaining JDBC connection
     */
    public static void ContractStateChange(String id_tenement, int id_tenant, Date start_date, int contract_status, Database db)
    {
        // Determine status string based on input code
        String contractStatusString;
        if (contract_status == 1)
            contractStatusString = "PENDING";
        else if (contract_status == 2)
            contractStatusString = "ACTIVE";
        else
            contractStatusString = "EXPIRED";

        // SQL statement for updating only the contract status
        String sql = "UPDATE Contract SET contract_status = ? WHERE id_tenement = ? AND id_tenant = ? AND start_date = ?";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setString(1, contractStatusString);
            pstmt.setString(2, id_tenement);
            pstmt.setInt(3, id_tenant);
            pstmt.setDate(4, start_date);
            pstmt.executeUpdate(); // Execute status change
        } catch (SQLException e) 
        {
            // Handle SQL exception by printing stack trace
            e.printStackTrace();
        }

    }

    /**
     * Retrieves and prints details of a contract record.
     *
     * @param id_tenement Identifier of the tenement
     * @param id_tenant   Numeric ID of the tenant
     * @param start_date  Contract start date to match
     * @param db          Database instance for obtaining JDBC connection
     */
    public static void SelectContract(String id_tenement, int id_tenant, Date start_date, Database db)
    {
        // SQL statement for selecting a contract record
        String sql = "SELECT * FROM Contract WHERE id_tenement = ? AND id_tenant = ? AND start_date = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            pstmt.setString(1, id_tenement);
            pstmt.setInt(2, id_tenant);
            pstmt.setDate(3, start_date);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                // Format and print contract information
                System.out.println("---------------------------------------------------------------"
                                    + "\n\tTenement's ID: " + rs.getString("id_tenement")
                                    + "\n\tTenant's ID: " + rs.getInt("id_tenant")
                                    + "\n\tContract's start day: " + rs.getDate("start_date")
                                    + "\n\tContract's finish day: " + rs.getDate("finish_date")
                                    + "\n\tContract's status: "  + rs.getString("contract_status")
                                    + "\n------------------------------------------------------------------");
            }
        } catch (SQLException e) 
        {
            // Handle SQL exception by printing stack trace
            e.printStackTrace();
        }
    }
}
