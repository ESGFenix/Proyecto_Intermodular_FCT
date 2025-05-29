package com.controller;

import java.sql.*;

import com.model.Database;

/**
 * AdvancedQueries class provides methods to execute complex SQL queries
 * related to tenements, landlords, and contracts in a rental management system.
 */
public class AdvancedQueries 
{
    /**
     * Displays all tenements that are not currently rented because their contracts are expired.
     * 
     * @param db Database object used to establish SQL connection
     */
    public static void showNotRentedHouses(Database db)
    {
        String sql = "SELECT T.*, H.name FROM House_Type H " +
                     "JOIN Tenement T ON H.id = T.type " +
                     "JOIN Contract C ON C.id_tenement = T.id " +
                     "WHERE C.contract_status = 'EXPIRED'";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                // Determine if pets are allowed
                String acepta_mascota = "No";
                if (rs.getInt("accepts_pets") == 1) 
                    acepta_mascota = "Si";

                // Display the tenement's details
                System.out.println("\tTenement's ID: " + rs.getString("id") + 
                                    "\n\tLandlord's ID: " + rs.getInt("id_landlord") + 
                                    "\n\tRent's price: " + rs.getFloat("rent_price") + 
                                    "\n\tTenement's surface: " + rs.getFloat("surface") + 
                                    "\n\tTenement's description: " + rs.getString("description") + 
                                    "\n\tHouse's type: " + rs.getString("name") + 
                                    "\n\tAccepts pets: " + acepta_mascota + 
                                    "\n\tAddress: " + rs.getString("address") + 
                                    "\n-----------------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the total and average rent gains for each landlord with active contracts.
     * 
     * @param db Database object used to establish SQL connection
     */
    public static void showLandlordsMonthlyGains(Database db)
    {
        String sql = "SELECT L.id, L.name, SUM(C.price) AS total_gains, AVG(C.price) AS average_rent " +
                     "FROM Landlord L " +
                     "LEFT JOIN Tenement T ON L.id = T.id_landlord " +
                     "LEFT JOIN Contract C ON T.id = C.id_tenement " +
                     "WHERE C.contract_status = 'ACTIVE' " +
                     "GROUP BY L.id";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                // Display landlord's ID, name, total and average rent
                System.out.println("------------------------------------------------------------" +
                                    "\nLandlord's ID: " + rs.getInt("id") + 
                                    "\nLandlord's name: " + rs.getString("name") + 
                                    "\nTotal gains this month: " + rs.getFloat("total_gains") + 
                                    "\nAverage rent of their houses: " + rs.getFloat("average_rent") + 
                                    "\n------------------------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays tenements currently rented by tenants who own pets.
     * 
     * @param db Database object used to establish SQL connection
     */
    public static void showHousesWithPets(Database db)
    {
        String sql = "SELECT T.*, H.name FROM House_Type H " +
                     "JOIN Tenement T ON H.id = T.type " +
                     "JOIN Contract C ON C.id_tenement = T.id " +
                     "JOIN Tenant TE ON TE.id = C.id_tenant " +
                     "WHERE TE.has_pet = 1 AND C.contract_status = 'ACTIVE'";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                // Determine if pets are allowed
                String acepta_mascota = "No";
                if (rs.getInt("accepts_pets") == 1) 
                    acepta_mascota = "Si";

                // Display the tenement's details
                System.out.println("------------------------------------------------------" +
                                    "\nTenement's ID: " + rs.getString("id") + 
                                    "\nLandlord's ID: " + rs.getInt("id_landlord") + 
                                    "\nRent's price: " + rs.getFloat("rent_price") + 
                                    "\nTenement's surface: " + rs.getFloat("surface") + 
                                    "\nTenement's description: " + rs.getString("description") + 
                                    "\nHouse's type: " + rs.getString("name") + 
                                    "\nAccepts pets: " + acepta_mascota + 
                                    "\nAddress: " + rs.getString("address") + 
                                    "\n-----------------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the house type that has been rented the most based on active contracts.
     * 
     * @param db Database object used to establish SQL connection
     */
    public static void showMostRentedHouseType(Database db)
    {
        String sql = "SELECT H.name, COUNT(T.id) AS total_rents " +
                     "FROM House_Type H " +
                     "JOIN Tenement T ON H.id = T.type " +
                     "JOIN Contract C ON C.id_tenement = T.id " +
                     "WHERE C.contract_status = 'ACTIVE' " +
                     "GROUP BY H.name " +
                     "ORDER BY total_rents DESC " +
                     "LIMIT 1";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) 
            {
                // Output the most rented house type and its count
                System.out.println("Most rented house type: " + rs.getString("name") + 
                                   "\nTotal rents: " + rs.getInt("total_rents"));
            } else {
                System.out.println("No rented houses found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
