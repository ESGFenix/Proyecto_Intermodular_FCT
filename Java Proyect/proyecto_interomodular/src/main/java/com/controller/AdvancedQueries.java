package com.controller;

import java.sql.*;

import com.model.Database;
/**
 * AdvancedQueries class provides methods to execute complex SQL queries
 * related to tenements, landlords, and contracts in a rental management system.
 */
public class AdvancedQueries 
{
    public void showNotRentedHouses(Database db)
    {
        String sql = "SELECT T.*, H.name FROM House_Type H LEFT JOIN Tenement T ON H.id = T.type LEFT JOIN Contract C ON C.id_tenement = T.id WHERE T.id NOT IN(SELECT id_tenement FROM Contract WHERE contract_status IN ('ACTIVE', 'PENDING'))";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String acepta_mascota = "No";
                if (rs.getInt("accepts_pets") == 1) 
                    acepta_mascota = "Si";
                
                System.out.println("Tenement's ID: " + rs.getString("id") + 
                                    "\nLandlord's ID: " + rs.getInt("id_landlord") + 
                                    "\nRent's price: " + rs.getFloat("rent_price") + 
                                    "\nTenement's surface: " + rs.getFloat("surface") + 
                                    "\nTenement's description: " + rs.getString("description") + 
                                    "\nHouse's type: " + rs.getString("name") + 
                                    "\nAccepts pets: " + acepta_mascota + 
                                    "\nAddress: " + rs.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showLandlordsMonthlyGains(Database db)
    {
        String sql = "SELECT L.id, L.name, SUM(C.rent_price) AS total_gains, AVG(C.rent_price) AS average_rent FROM Landlord L LEFT JOIN Tenement T ON L.id = T.id_landlord LEFT JOIN Contract C ON T.id = C.id_tenement WHERE C.contract_status = 'ACTIVE' GROUP BY L.id";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                System.out.println("Landlord's ID: " + rs.getInt("id") + 
                                   "\nLandlord's name: " + rs.getString("name") + 
                                   "\nTotal gains this month: " + rs.getFloat("total_gains") + 
                                   "\nAverage rent of their houses: " + rs.getFloat("average_rent"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showHousesWithPets(Database db)
    {
        String sql = "SELECT T.*, H.name FROM House_Type H JOIN Tenement T ON H.id = T.type JOIN Contract C ON C.id_tenement = T.id JOIN Tenant TE ON TE.id = C.id_tenant  WHERE TE.has_pet = 1 AND C.contract_status = 'ACTIVE'";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String acepta_mascota = "No";
                if (rs.getInt("accepts_pets") == 1) 
                    acepta_mascota = "Si";
                
                System.out.println("Tenement's ID: " + rs.getString("id") + 
                                    "\nLandlord's ID: " + rs.getInt("id_landlord") + 
                                    "\nRent's price: " + rs.getFloat("rent_price") + 
                                    "\nTenement's surface: " + rs.getFloat("surface") + 
                                    "\nTenement's description: " + rs.getString("description") + 
                                    "\nHouse's type: " + rs.getString("name") + 
                                    "\nAccepts pets: " + acepta_mascota + 
                                    "\nAddress: " + rs.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showMostRentedHouseType(Database db)
    {
        String sql = "SELECT h.name, COUNT(t.id) AS total_rents FROM house_type h JOIN tenement t ON h.id = t.type JOIN contract c ON c.id_tenement = t.id WHERE c.contract_status = 'ACTIVE' GROUP BY h.name ORDER BY total_rents DESC LIMIT 1";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) 
        {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) 
            {
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
